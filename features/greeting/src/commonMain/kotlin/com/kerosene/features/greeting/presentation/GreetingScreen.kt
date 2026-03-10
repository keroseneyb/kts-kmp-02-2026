package com.kerosene.features.greeting.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.kerosene.core.designsystem.theme.AppTheme
import com.kerosene.core.designsystem.ui.AppDimens
import com.kerosene.features.greeting.Res
import com.kerosene.features.greeting.finish_button
import com.kerosene.features.greeting.next_button
import com.kerosene.features.greeting.smartbotlogo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val GREETING_TEM = Res.drawable.smartbotlogo

private val onboardingItems = listOf(
    GREETING_TEM,
    GREETING_TEM,
    GREETING_TEM
)

@Composable
fun GreetingScreen(
    onGoToLoginButtonClick: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { onboardingItems.size })
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        GreetingContent(
            pagerState = pagerState,
            items = onboardingItems,
            onNextClick = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            },
            onFinishClick = onGoToLoginButtonClick
        )
    }
}

@Composable
private fun GreetingContent(
    pagerState: PagerState,
    items: List<DrawableResource>,
    onNextClick: () -> Unit,
    onFinishClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) { page ->
            GreetingSlide(image = items[page])
        }

        Spacer(modifier = Modifier.height(AppDimens.medium))

        PagerIndicator(
            pagerState = pagerState,
            pageCount = items.size
        )

        GoToLoginScreen(
            isLastPage = pagerState.currentPage == items.size - 1,
            onClick = {
                if (pagerState.currentPage < items.size - 1) {
                    onNextClick()
                } else {
                    onFinishClick()
                }
            }
        )
    }
}

@Composable
private fun GreetingSlide(image: DrawableResource) {
    Image(
        painter = painterResource(image),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
    )
}

@Composable
private fun PagerIndicator(pagerState: PagerState, pageCount: Int) {
    Row(
        modifier = Modifier
            .padding(vertical = AppDimens.medium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = AppDimens.small)
                    .size(AppDimens.medium)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

@Composable
private fun GoToLoginScreen(
    isLastPage: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(AppDimens.extraLarge),
        modifier = Modifier
            .padding(AppDimens.medium)
            .fillMaxWidth()
    ) {
        val buttonText = if (isLastPage) {
            stringResource(Res.string.finish_button)
        } else {
            stringResource(Res.string.next_button)
        }
        Text(text = buttonText)
    }
}

@Preview
@Composable
fun GreetingScreenPreview() {
    AppTheme {
        GreetingScreen(onGoToLoginButtonClick = {})
    }
}