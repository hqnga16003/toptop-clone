package com.example.myapplication.ui.foryou

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.example.myapplication.video.VideoDetailScreen
import com.example.myapplication.video.VideoDetailViewModel

@UnstableApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListForUVideoScreen(modifier: Modifier = Modifier) {
    VerticalPager(pageCount = 10) { videoId ->
        val videoViewModel: VideoDetailViewModel = hiltViewModel(key = videoId.toString())
        VideoDetailScreen(videoId = videoId,videoViewModel)
    }

}