package com.example.myapplication.ui.following

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.myapplication.R
import com.example.myapplication.designsystem.TopTopVideoPlayer
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@UnstableApi
@Composable
fun FollowingScreen() {

    val pagerState = rememberPagerState()
    val cardWith = (LocalConfiguration.current.screenWidthDp * 2 / 3) - 24

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
        
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Trending Creators",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Follow an account to see their latest video here",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.height(36.dp))
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f),
            pageCount = 10,
            state = pagerState,
            pageSize = PageSize.Fixed(cardWith.dp),
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(start = 24.dp)
        ) { page ->
            Card(modifier = Modifier
                .width(cardWith.dp)
                .aspectRatio(0.7f)
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    scaleY = lerp(
                        start = 0.7f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .clip(RoundedCornerShape(16.dp))) {

                CreatorCard(name = "NgaHq $page",
                    nickName = "@NgaHq $page",
                    onFollow = { },
                    onClose = {})
            }

        }
    }
}

@UnstableApi
@Composable
fun CreatorCard(
    modifier: Modifier = Modifier,
    name: String,
    nickName: String,
    onFollow: () -> Unit,
    onClose: () -> Unit
) {

    val videoPlayer = ExoPlayer.Builder(LocalContext.current).build()
    videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
    videoPlayer.playWhenReady = true
    videoPlayer.prepare()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
    ) {
        val (videoIntro, btnClose, imgAvatar, tvName, tvNickName, btnFollow) = createRefs()

        TopTopVideoPlayer(player = videoPlayer, modifier = Modifier.constrainAs(videoIntro) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })

        IconButton(onClick = onClose, modifier = Modifier
            .constrainAs(btnClose) {

                end.linkTo(parent.end, margin = 12.dp)
                top.linkTo(parent.top, margin = 12.dp)

            }
            .size(16.dp)) {
            Icon(Icons.Default.Close, contentDescription = "")
        }

        Button(onClick = onFollow,
            modifier = Modifier
                .constrainAs(btnFollow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
                .padding(horizontal = 48.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, containerColor = Color(0xFFE94359)
            )) {

            Text(
                text = "Follow",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
            )
        }
        Text(text = nickName,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
            modifier = Modifier.constrainAs(tvNickName) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(btnFollow.top, margin = 8.dp)
            })

        Text(text = name,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
            modifier = Modifier.constrainAs(tvName) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(tvNickName.top, margin = 8.dp)
            })

        AvatarFollowing(modifier = Modifier.constrainAs(imgAvatar) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(tvName.top, margin = 8.dp)
        })
    }
    val uri = RawResourceDataSource.buildRawResourceUri(R.raw.test)
    val mediaItem = MediaItem.fromUri(uri)
    videoPlayer.setMediaItem(mediaItem)
    SideEffect {
        videoPlayer.play()
    }
}


@Composable
fun AvatarFollowing(modifier: Modifier = Modifier) {
    val sizeAvatar = LocalConfiguration.current.screenWidthDp * 0.2
    Image(
        painter = painterResource(id = R.drawable.ic_dog),
        contentDescription = "",
        modifier = modifier
            .size(sizeAvatar.dp)
            .background(color = Color.White, shape = CircleShape)
            .border(color = Color.White, width = 2.dp, shape = CircleShape)
            .clip(CircleShape)
    )
}

@Preview
@Composable
fun AvatarFollowingPriview() {
    AvatarFollowing()
}