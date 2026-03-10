package com.kerosene.features.feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerosene.core.common.handleApiCall
import com.kerosene.core.common.setupSearch
import com.kerosene.core.network.ProjectApiService
import com.kerosene.core.network.CabinetProvider
import com.kerosene.features.feed.data.mapper.toCabinet
import com.kerosene.features.feed.domain.entity.Cabinet
import com.kerosene.features.feed.domain.entity.Conversation
import com.kerosene.features.feed.domain.repository.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class FeedViewModel(
    private val feedRepository: FeedRepository,
    private val cabinetProvider: CabinetProvider,
    private val projectApiService: ProjectApiService
) : ViewModel() {

    private val _state = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val state = _state.asStateFlow()

    private val _chatSearchQuery = MutableStateFlow("")
    private val _cabinetSearchQuery = MutableStateFlow("")
    
    private var allConversations: List<Conversation> = emptyList()
    private var currentOffset = INITIAL_OFFSET

    init {
        loadSpaces()
        setupChatSearch()
        setupCabinetSearch()
    }

    private fun setupChatSearch() {
        _chatSearchQuery.setupSearch(
            scope = viewModelScope,
            debounceTime = CHAT_SEARCH_DEBOUNCE_MS.milliseconds,
            minQueryLength = MIN_QUERY_LENGTH,
            onSearch = { 
                currentOffset = INITIAL_OFFSET
                applyFilters()
                loadConversations(showLoading = false) 
            }
        )
    }

    private fun setupCabinetSearch() {
        _cabinetSearchQuery.setupSearch(
            scope = viewModelScope,
            debounceTime = CABINET_SEARCH_DEBOUNCE_MS.milliseconds,
            onSearch = { query ->
                updateContent {
                    val filtered = if (query.isBlank()) {
                        sheetState.cabinets
                    } else {
                        sheetState.cabinets.filter {
                            it.name.contains(query, ignoreCase = true) ||
                                    it.cabinetName.contains(query, ignoreCase = true)
                        }
                    }
                    copy(sheetState = sheetState.copy(filteredCabinets = filtered))
                }
            }
        )
    }

    private inline fun updateContent(crossinline block: FeedUiState.Content.() -> FeedUiState.Content) {
        _state.update {
            when (it) {
                is FeedUiState.Content -> it.block()
                else -> it
            }
        }
    }
// region Load Spaces
fun loadSpaces() {
    viewModelScope.launch {
        handleApiCall(
            apiCall = { feedRepository.getCabinets().getOrThrow() },
            onLoading = ::onLoadSpacesLoading,
            onSuccess = ::onLoadSpacesSuccess,
            onError = { message -> _state.update { FeedUiState.Error(message) } }
        )
    }
}


    private fun onLoadSpacesLoading() {
        if (_state.value !is FeedUiState.Content) {
            _state.update { FeedUiState.Loading }
        }
    }

    private fun onLoadSpacesSuccess(cabinets: List<Cabinet>) {
        if (cabinets.isEmpty()) {
            _state.update { FeedUiState.Content(isLoading = false) }
            return
        }
        viewModelScope.launch { loadInitialProject(cabinets) }
    }

    private suspend fun loadInitialProject(cabinets: List<Cabinet>) {
        handleApiCall(
            apiCall = { feedRepository.getProjectInfo().getOrThrow() },
            onSuccess = { projectSpace -> onLoadInitialProjectSuccess(projectSpace, cabinets) },
            onError = { onLoadInitialProjectError(cabinets) }
        )
    }

    private fun onLoadInitialProjectSuccess(projectSpace: Cabinet?, cabinets: List<Cabinet>) {
        val initialEnriched = projectSpace?.let { ps ->
            val cabinetInList = cabinets.find { it.cabinetId == ps.cabinetId }
            ps.copy(cabinetName = cabinetInList?.cabinetName ?: ps.cabinetName)
        }

        _state.update { 
            FeedUiState.Content(
                sheetState = CabinetSheetState(
                    cabinets = cabinets,
                    filteredCabinets = cabinets
                ),
                selectedCabinet = initialEnriched,
                isLoading = initialEnriched != null,
                chatSearchState = ChatSearchState(query = _chatSearchQuery.value)
            ) 
        }

        initialEnriched?.let { 
            loadConversations(showLoading = true)
            loadProjectDetails(it)
        } ?: run {
            updateContent { copy(isLoading = false) }
        }
    }

    private fun onLoadInitialProjectError(cabinets: List<Cabinet>) {
        _state.update { 
            FeedUiState.Content(
                sheetState = CabinetSheetState(
                    cabinets = cabinets,
                    filteredCabinets = cabinets
                ),
                isLoading = false
            )
        }
    }

    private fun loadProjectDetails(cabinet: Cabinet) {
        viewModelScope.launch(Dispatchers.IO) {
            handleApiCall(
                apiCall = {
                    val response = projectApiService.getProjectsList(cabinet.cabinetId)
                    response.data?.projects?.map { p -> p.toCabinet(cabinet.cabinetId, cabinet.cabinetName) } ?: emptyList()
                },
                onSuccess = { projects -> onLoadProjectDetailsSuccess(projects, cabinet) },
                onError = {}
            )
        }
    }

    private fun onLoadProjectDetailsSuccess(projects: List<Cabinet>, cabinet: Cabinet) {
        val fullProjectInfo = projects.find { p -> p.projectId == cabinet.projectId }
        updateContent {
            copy(
                selectedCabinet = fullProjectInfo ?: cabinet,
                sheetState = sheetState.copy(
                    cabinetProjects = sheetState.cabinetProjects + (cabinet.cabinetId to projects)
                )
            )
        }
    }
    fun loadConversations(showLoading: Boolean = true, isNextPage: Boolean = false) {
        val currentState = _state.value as? FeedUiState.Content ?: return
        if (isNextPage && (currentState.isNextPageLoading || currentState.isLastPageReached)) return

        viewModelScope.launch(Dispatchers.IO) {
            handleApiCall(
                apiCall = { fetchConversations() },
                onLoading = { onLoadConversationsLoading(showLoading, isNextPage) },
                onSuccess = { fetchedChats -> onLoadConversationsSuccess(fetchedChats, isNextPage) },
                onError = { onLoadConversationsError() }
            )
        }
    }

    private suspend fun fetchConversations() = feedRepository.getConversations(
        isWaitingOnly = false,
        limit = PAGE_SIZE,
        offset = currentOffset
    ).getOrThrow()

    private fun onLoadConversationsLoading(showLoading: Boolean, isNextPage: Boolean) {
        if (isNextPage) {
            updateContent { copy(isNextPageLoading = true) }
        } else if (showLoading && allConversations.isEmpty()) {
            updateContent { copy(isLoading = true) } 
        }
    }

    private fun onLoadConversationsSuccess(fetchedChats: List<Conversation>, isNextPage: Boolean) {
        if (isNextPage) {
            allConversations = (allConversations + fetchedChats).distinctBy { it.id }
        } else {
            allConversations = fetchedChats
        }
        
        val isLastPage = fetchedChats.size < PAGE_SIZE
        if (!isLastPage) {
            currentOffset += PAGE_SIZE
        }

        updateContent { 
            copy(
                isNextPageLoading = false,
                isLastPageReached = isLastPage
            )
        }
        applyFilters()
    }

    private fun onLoadConversationsError() {
        updateContent { 
            copy(
                isLoading = false,
                isNextPageLoading = false
            ) 
        }
    }

    fun onLoadMore() {
        loadConversations(isNextPage = true)
    }

    private fun applyFilters() {
        val currentState = _state.value as? FeedUiState.Content ?: return
        val query = _chatSearchQuery.value
        val isWaitingTab = currentState.selectedTab == FeedTab.Waiting

        val filtered = allConversations.filter { chat ->
            val matchesTab = !isWaitingTab || chat.isWaitingResponse
            val matchesQuery = query.isEmpty() || 
                chat.userName.contains(query, ignoreCase = true) ||
                chat.lastMessage.contains(query, ignoreCase = true)
            
            matchesTab && matchesQuery
        }

        updateContent { 
            copy(
                conversations = filtered, 
                isLoading = false,
                chatSearchState = chatSearchState.copy(query = query)
            ) 
        }
    }

    fun onTabSelected(tab: FeedTab) {
        currentOffset = INITIAL_OFFSET
        updateContent { copy(selectedTab = tab, isLastPageReached = false) }
        allConversations = emptyList()
        loadConversations(showLoading = true)
    }

    fun onChatSearchQueryChange(query: String) {
        updateContent { copy(chatSearchState = chatSearchState.copy(query = query)) }
        _chatSearchQuery.update { query }
    }

    fun onProjectSelected(cabinet: Cabinet) {
        viewModelScope.launch(Dispatchers.IO) {
            cabinetProvider.saveSpace(cabinet.cabinetId, cabinet.projectId)
            
            allConversations = emptyList()
            currentOffset = INITIAL_OFFSET
            _chatSearchQuery.update { "" }
            
            updateContent {
                copy(
                    selectedCabinet = cabinet,
                    isSheetVisible = false,
                    conversations = emptyList(),
                    chatSearchState = chatSearchState.copy(query = ""),
                    isLoading = true,
                    isLastPageReached = false
                )
            }
            
            loadConversations(showLoading = true)
        }
    }

    fun onCabinetClicked(cabinetId: String) {
        val currentState = _state.value as? FeedUiState.Content ?: return
        val currentExpanded = currentState.sheetState.expandedCabinets
        
        if (currentExpanded.contains(cabinetId)) {
            updateContent {
                copy(sheetState = sheetState.copy(expandedCabinets = currentExpanded - cabinetId))
            }
        } else {
            updateContent {
                copy(sheetState = sheetState.copy(expandedCabinets = currentExpanded + cabinetId))
            }
            if (!currentState.sheetState.cabinetProjects.containsKey(cabinetId)) {
                val cabinetName = currentState.sheetState.cabinets.find { it.cabinetId == cabinetId }?.cabinetName ?: ""
                loadProjectsForCabinet(cabinetId, cabinetName)
            }
        }
    }
    fun loadProjectsForCabinet(cabinetId: String, cabinetName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            handleApiCall(
                apiCall = {
                    val response = projectApiService.getProjectsList(cabinetId)
                    response.data?.projects?.map { it.toCabinet(cabinetId, cabinetName) } ?: emptyList()
                },
                onSuccess = { projects -> onLoadProjectsForCabinetSuccess(projects, cabinetId) },
                onError = {}
            )
        }
    }

    private fun onLoadProjectsForCabinetSuccess(projects: List<Cabinet>, cabinetId: String) {
        updateContent {
            copy(
                sheetState = sheetState.copy(
                    cabinetProjects = sheetState.cabinetProjects + (cabinetId to projects)
                )
            )
        }
    }

    fun onSearchQueryChange(query: String) {
        updateContent { copy(sheetState = sheetState.copy(searchQuery = query)) }
        _cabinetSearchQuery.update { query }
    }

    fun toggleSheet(visible: Boolean) {
        if (!visible) {
            onSearchQueryChange("")
        }
        updateContent { copy(isSheetVisible = visible) }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val INITIAL_OFFSET = 0
        private const val CHAT_SEARCH_DEBOUNCE_MS = 500L
        private const val CABINET_SEARCH_DEBOUNCE_MS = 300L
        private const val MIN_QUERY_LENGTH = 3
    }
}
