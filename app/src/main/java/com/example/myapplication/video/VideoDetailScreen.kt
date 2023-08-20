package com.example.myapplication.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.example.myapplication.designsystem.TopTopVideoPlayer

@UnstableApi

@Composable
fun VideoDetailScreen(videoId: Int, videoViewModel: VideoDetailViewModel = hiltViewModel()) {
    val uiState = videoViewModel.uiState.collectAsState()
    if (uiState.value == VideoDetailUiState.Default) {
        videoViewModel.handleAction(VideoDetailAction.LoadData(videoId))
    }
    VideoDetailScreen(uiState = uiState.value, player = videoViewModel.videoPlayer) {
            action -> videoViewModel.handleAction(action = action)

    }
}

@UnstableApi
@Composable
fun VideoDetailScreen(
    uiState: VideoDetailUiState,
    player: Player,
    handlerAction: (VideoDetailAction) -> Unit
) {
    when (uiState) {
        is VideoDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loadding")
            }
        }

        is VideoDetailUiState.Success -> {
            VideoDetailScreen(player = player, handlerAction = handlerAction)

        }

        else -> {

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@UnstableApi
@Composable
fun VideoDetailScreen(player: Player, handlerAction: (VideoDetailAction) -> Unit) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = {
                handlerAction(VideoDetailAction.ToggleVideo)
            })
    ) {
        val (videoPlayer, sideBar) = createRefs()
        TopTopVideoPlayer(player = player, modifier = Modifier.constrainAs(videoPlayer) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.matchParent
        })


    }
}