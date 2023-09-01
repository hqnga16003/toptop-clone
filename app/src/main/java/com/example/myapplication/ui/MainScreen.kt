package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.util.UnstableApi
import com.example.myapplication.ui.following.FollowingScreen
import com.example.myapplication.ui.foryou.ListForUVideoScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@UnstableApi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val pagerState = rememberPagerState(initialPage = 1)
    val coroutine = rememberCoroutineScope()
    val scrollToPage: (Boolean) -> Unit = { isForu ->
        val page = if (isForu) 1 else 0

        coroutine.launch {
            pagerState.animateScrollToPage(page = page)
        }
    }
    var isShowTabConent by remember {
        mutableStateOf(true)
    }
    val toggleTabContent = { isShow: Boolean ->
        if (isShowTabConent != isShow) {
            isShowTabConent = isShow

        }
    }
    LaunchedEffect(key1 = pagerState) {
        snapshotFlow {
            pagerState.currentPage
        }.collect { page ->
            if (page == 2) {
                toggleTabContent(false)
            } else {
                toggleTabContent(true)
            }
        }
    }


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (tabContentView, body) = createRefs()
        HorizontalPager(modifier = Modifier.constrainAs(body) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }, pageCount = 3, state = pagerState) { page ->
            when (page) {
                0 -> FollowingScreen()
                2 -> ProfileScreen()
                else -> ListForUVideoScreen()
            }

        }
        AnimatedVisibility(visible = isShowTabConent) {
            TabContentView(isTabSeletedIndex = pagerState.currentPage, onSelectedTab = { isForU ->
                scrollToPage(isForU)
            }, modifier = Modifier.constrainAs(tabContentView) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        }
    }

}


@Composable
fun TabContentItemView(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    isForU: Boolean,
    onSelectedTab: (isForU: Boolean) -> Unit
) {
    val anpha = if (isSelected) 1f else 0.6f

    Column(
        modifier = modifier
            .wrapContentSize()
            .clickable { onSelectedTab(isForU) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(color = Color.White.copy(anpha))
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (isSelected) {
            Divider(color = Color.White, thickness = 2.dp, modifier = Modifier.width(24.dp))

        }
    }
}

@Preview
@Composable
fun TabContentItemViewPriviewSelected() {
    TabContentItemView(title = "For You", isSelected = true, isForU = true, onSelectedTab = {})
}

@Preview
@Composable
fun TabContentItemViewPriviewUnSelected() {
    TabContentItemView(title = "Following", isSelected = false, isForU = false, onSelectedTab = {})
}


@Composable
fun TabContentView(
    modifier: Modifier = Modifier, isTabSeletedIndex: Int, onSelectedTab: (isForU: Boolean) -> Unit
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (tabContent, imgSearch) = createRefs()
        Row(modifier = Modifier
            .wrapContentSize()
            .constrainAs(tabContent) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            TabContentItemView(
                title = "Following",
                isSelected = isTabSeletedIndex == 0,
                isForU = false,
                onSelectedTab = onSelectedTab
            )
            Spacer(modifier = Modifier.width(12.dp))
            TabContentItemView(
                title = "For You",
                isSelected = isTabSeletedIndex == 1,
                isForU = true,
                onSelectedTab = onSelectedTab
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(imgSearch) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end, margin = 16.dp)

        }) {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun TabContentViewPriview() {
    TabContentView(isTabSeletedIndex = 1, onSelectedTab = {})
}