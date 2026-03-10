package com.kerosene.features.feed.presentation

import com.kerosene.features.feed.domain.entity.Cabinet
import com.kerosene.features.feed.domain.entity.Conversation

sealed class FeedUiState {
    data object Loading : FeedUiState()
    
    data class Content(
        val selectedCabinet: Cabinet? = null,
        val isSheetVisible: Boolean = false,
        val conversations: List<Conversation> = emptyList(),
        val selectedTab: FeedTab = FeedTab.All,
        val isLoading: Boolean = false,
        val isNextPageLoading: Boolean = false,
        val isLastPageReached: Boolean = false,

        val chatSearchState: ChatSearchState = ChatSearchState(),
        val sheetState: CabinetSheetState = CabinetSheetState()
    ) : FeedUiState()
    
    data class Error(val message: String) : FeedUiState()
}

data class ChatSearchState(
    val query: String = ""
)

data class CabinetSheetState(
    val cabinets: List<Cabinet> = emptyList(),
    val filteredCabinets: List<Cabinet> = emptyList(),
    val cabinetProjects: Map<String, List<Cabinet>> = emptyMap(),
    val expandedCabinets: Set<String> = emptySet(),
    val searchQuery: String = ""
)

enum class FeedTab {
    All, Waiting
}
