package com.kerosene.features.feed.presentation

import com.kerosene.core.designsystem.ui.components.AppAnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kerosene.core.designsystem.ui.components.FullScreenLoading
import com.kerosene.core.designsystem.ui.components.FullScreenError
import com.kerosene.core.designsystem.ui.components.AppModalBottomSheet
import com.kerosene.core.designsystem.ui.components.AppOutlinedTextField
import com.kerosene.core.designsystem.ui.AppImage
import com.kerosene.core.designsystem.ui.AppDimens
import com.kerosene.features.feed.Res
import com.kerosene.features.feed.arrow_drop
import com.kerosene.features.feed.check
import com.kerosene.features.feed.Telegram_Messenger
import com.kerosene.features.feed.search
import com.kerosene.features.feed.all_requests
import com.kerosene.features.feed.empty_chat_list
import com.kerosene.features.feed.error_prefix
import com.kerosene.features.feed.no_projects
import com.kerosene.features.feed.no_waiting_chats
import com.kerosene.features.feed.nothing_found
import com.kerosene.features.feed.search_requests
import com.kerosene.features.feed.select_project
import com.kerosene.features.feed.waiting_for_reply
import com.kerosene.features.feed.your_spaces
import com.kerosene.features.feed.domain.entity.Cabinet
import com.kerosene.features.feed.domain.entity.Conversation
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    onChatClick: (String) -> Unit,
    viewModel: FeedViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.loadSpaces()
    }

    when (val currentState = state) {
        is FeedUiState.Loading -> {
            FullScreenLoading()
        }

        is FeedUiState.Error -> {
            FullScreenError(
                message = "${stringResource(Res.string.error_prefix)}${currentState.message}",
                onRetry = { viewModel.loadSpaces() }
            )
        }

        is FeedUiState.Content -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
            ) {
                FeedContentScreen(
                    currentState = currentState,
                    sheetState = sheetState,
                    viewModel = viewModel,
                    onChatClick = onChatClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedTopBar(selectedCabinet: Cabinet?, onClick: () -> Unit) {
    TopAppBar(
        title = {
            Surface(
                onClick = onClick,
                shape = RoundedCornerShape(AppDimens.medium),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = AppDimens.large,
                        vertical = AppDimens.medium
                    ), verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = selectedCabinet?.name ?: stringResource(Res.string.select_project),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        if (selectedCabinet != null) Text(
                            text = selectedCabinet.cabinetName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(AppDimens.medium))
                    Image(
                        painter = painterResource(Res.drawable.arrow_drop),
                        contentDescription = null,
                        modifier = Modifier.size(AppDimens.extraLarge),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedContentScreen(
    currentState: FeedUiState.Content,
    sheetState: SheetState,
    viewModel: FeedViewModel,
    onChatClick: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            FeedTopBar(
                selectedCabinet = currentState.selectedCabinet,
                onClick = {
                    focusManager.clearFocus()
                    viewModel.toggleSheet(true)
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            ChatSearchField(
                query = currentState.chatSearchState.query,
                onQueryChange = viewModel::onChatSearchQueryChange,
                onSearchAction = { focusManager.clearFocus() }
            )

            ChatTabs(
                selectedTab = currentState.selectedTab,
                onTabSelected = {
                    focusManager.clearFocus()
                    viewModel.onTabSelected(it)
                }
            )

            ConversationList(
                isLoading = currentState.isLoading,
                isNextPageLoading = currentState.isNextPageLoading,
                conversations = currentState.conversations,
                query = currentState.chatSearchState.query,
                selectedTab = currentState.selectedTab,
                onChatClick = {
                    focusManager.clearFocus()
                    onChatClick(it)
                },
                onLoadMore = viewModel::onLoadMore
            )
        }

        if (currentState.isSheetVisible) {
            AppModalBottomSheet(
                onDismissRequest = { viewModel.toggleSheet(false) },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                SpaceSelectorContent(
                    sheetState = currentState.sheetState,
                    selectedCabinet = currentState.selectedCabinet,
                    onSearchChange = viewModel::onSearchQueryChange,
                    onCabinetClick = viewModel::onCabinetClicked,
                    onProjectSelected = {
                        focusManager.clearFocus()
                        viewModel.onProjectSelected(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun ChatSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchAction: () -> Unit
) {
    AppOutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.large, vertical = AppDimens.medium),
        placeholder = { Text(stringResource(Res.string.search_requests)) },
        leadingIcon = {
            Image(
                painter = painterResource(Res.drawable.search),
                contentDescription = null,
                modifier = Modifier.size(AppDimens.extraLarge),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchAction() })
    )
}

@Composable
private fun ConversationList(
    isLoading: Boolean,
    isNextPageLoading: Boolean,
    conversations: List<Conversation>,
    query: String,
    selectedTab: FeedTab,
    onChatClick: (String) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false
            
            lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 2
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && !isLoading && !isNextPageLoading) {
            onLoadMore()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isLoading && conversations.isEmpty()) {
            CircularProgressIndicator()
        } else if (conversations.isEmpty()) {
            EmptyStateMessage(query, selectedTab)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) {
                items(conversations, key = { it.id }) { chat ->
                    ChatItem(
                        chat = chat,
                        onClick = { onChatClick(chat.id) }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = AppDimens.large),
                        thickness = 0.5.dp
                    )
                }

                if (isNextPageLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AppDimens.large),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyStateMessage(query: String, selectedTab: FeedTab) {
    val message = when {
        query.isNotBlank() -> stringResource(Res.string.nothing_found)
        selectedTab == FeedTab.Waiting -> stringResource(Res.string.no_waiting_chats)
        else -> stringResource(Res.string.empty_chat_list)
    }
    Text(
        text = message,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
private fun SpaceSelectorContent(
    sheetState: CabinetSheetState,
    selectedCabinet: Cabinet?,
    onSearchChange: (String) -> Unit,
    onCabinetClick: (String) -> Unit,
    onProjectSelected: (Cabinet) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = AppDimens.huge)) {
            Text(
                text = stringResource(Res.string.your_spaces),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(AppDimens.large)
            )

            SpaceSearchField(
                query = sheetState.searchQuery,
                onQueryChange = onSearchChange,
                onSearchAction = { focusManager.clearFocus() }
            )

            Spacer(modifier = Modifier.height(AppDimens.large))

            CabinetList(
                cabinets = sheetState.filteredCabinets,
                cabinetProjects = sheetState.cabinetProjects,
                expandedCabinets = sheetState.expandedCabinets,
                selectedCabinet = selectedCabinet,
                onCabinetClick = onCabinetClick,
                onProjectSelected = onProjectSelected
            )
        }
    }
}

@Composable
private fun SpaceSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchAction: () -> Unit
) {
    AppOutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth().padding(horizontal = AppDimens.large),
        placeholder = { Text(stringResource(Res.string.search)) },
        leadingIcon = {
            Image(
                painter = painterResource(Res.drawable.search),
                contentDescription = null,
                modifier = Modifier.size(AppDimens.extraLarge),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchAction() })
    )
}

@Composable
private fun CabinetList(
    cabinets: List<Cabinet>,
    cabinetProjects: Map<String, List<Cabinet>>,
    expandedCabinets: Set<String>,
    selectedCabinet: Cabinet?,
    onCabinetClick: (String) -> Unit,
    onProjectSelected: (Cabinet) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 500.dp)) {
        items(cabinets) { cabinet ->
            CabinetItem(
                cabinet = cabinet,
                isExpanded = expandedCabinets.contains(cabinet.cabinetId),
                projects = cabinetProjects[cabinet.cabinetId],
                selectedCabinet = selectedCabinet,
                onCabinetClick = { onCabinetClick(cabinet.cabinetId) },
                onProjectSelected = onProjectSelected
            )
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Composable
private fun CabinetItem(
    cabinet: Cabinet,
    isExpanded: Boolean,
    projects: List<Cabinet>?,
    selectedCabinet: Cabinet?,
    onCabinetClick: () -> Unit,
    onProjectSelected: (Cabinet) -> Unit
) {
    Column {
        ListItem(
            modifier = Modifier.clickable { onCabinetClick() },
            headlineContent = {
                Text(text = cabinet.cabinetName, fontWeight = FontWeight.Black)
            },
            trailingContent = {
                Image(
                    painter = painterResource(Res.drawable.arrow_drop),
                    contentDescription = null,
                    modifier = Modifier.size(AppDimens.extraLarge),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
        )

        AppAnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.padding(start = AppDimens.large)) {
                if (projects == null) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(AppDimens.large).size(AppDimens.extraLarge)
                    )
                } else if (projects.isEmpty()) {
                    Text(
                        text = stringResource(Res.string.no_projects),
                        modifier = Modifier.padding(AppDimens.large)
                    )
                } else {
                    projects.forEach { project ->
                        ProjectItem(
                            project = project,
                            isSelected = project.projectId == selectedCabinet?.projectId,
                            onProjectSelected = { onProjectSelected(project) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectItem(
    project: Cabinet,
    isSelected: Boolean,
    onProjectSelected: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { onProjectSelected() },
        headlineContent = { Text(text = project.name) },
        leadingContent = {
            AppImage(
                model = project.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(AppDimens.huge).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        },
        trailingContent = {
            if (isSelected) {
                Image(
                    painter = painterResource(Res.drawable.check),
                    contentDescription = null,
                    modifier = Modifier.size(AppDimens.extraLarge),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
        }
    )
}

@Composable
private fun ChatTabs(selectedTab: FeedTab, onTabSelected: (FeedTab) -> Unit) {
    TabRow(selectedTabIndex = selectedTab.ordinal, divider = {}) {
        Tab(
            selected = selectedTab == FeedTab.All,
            onClick = { onTabSelected(FeedTab.All) },
            text = { Text(stringResource(Res.string.all_requests)) })
        Tab(
            selected = selectedTab == FeedTab.Waiting,
            onClick = { onTabSelected(FeedTab.Waiting) },
            text = { Text(stringResource(Res.string.waiting_for_reply)) })
    }
}

@Composable
private fun ChatItem(
    chat: Conversation,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        leadingContent = {
            Box(modifier = Modifier.size(48.dp)) {
                AppImage(
                    model = chat.userPhotoUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                
                val socialIconPainter = when (chat.socialKind) {
                    "tg" -> painterResource(Res.drawable.Telegram_Messenger)
                    else -> null
                }

                if (socialIconPainter != null || chat.socialIconUrl != null) {
                    Surface(
                        modifier = Modifier
                            .size(18.dp)
                            .align(Alignment.BottomEnd),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 2.dp
                    ) {
                        if (socialIconPainter != null) {
                            Image(
                                painter = socialIconPainter,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(1.dp)
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        } else {
                            AppImage(
                                model = chat.socialIconUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(1.dp)
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        }
                    }
                }
            }
        },
        headlineContent = { Text(text = chat.userName, fontWeight = FontWeight.Bold) },
        supportingContent = {
            Text(
                text = chat.lastMessage,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailingContent = {
            Text(
                text = chat.displayTime,
                style = MaterialTheme.typography.labelSmall
            )
        }
    )
}
