package com.example.myapplication.video.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun AvatarView(modifier: Modifier = Modifier, onClick: () -> Unit) {
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (imgAvatar, addIcon) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.ic_dog),
            contentDescription = "",
            modifier = Modifier
                .size(48.dp)
                .background(shape = CircleShape, color = Color.White)
                .border(width = 2.dp, shape = CircleShape, color = Color.White)
                .clip(shape = CircleShape)
                .constrainAs(imgAvatar) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = MaterialTheme.colorScheme.error, shape = CircleShape)
                .constrainAs(addIcon) {
                    top.linkTo(imgAvatar.bottom)
                    bottom.linkTo(imgAvatar.bottom)
                    start.linkTo(imgAvatar.start)
                    end.linkTo(imgAvatar.end)
                }, contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun AvatarViewPriview() {
    MyApplicationTheme {
        AvatarView {

        }
    }
}


@Composable
fun AudioTrackView(modifier: Modifier = Modifier) {

    val infiniteTransition = rememberInfiniteTransition()
    val rotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            repeatMode = RepeatMode.Restart,
            animation = tween(durationMillis = 5000, easing = LinearEasing)
        ), label = ""
    )
    Image(
        painterResource(id = R.drawable.ic_audio_track),
        contentDescription = "",
        modifier = Modifier
            .size(30.dp)
            .rotate(rotate)
    )
}

@Preview
@Composable
private fun AudioTrackViewPriview() {
    MyApplicationTheme {
        AudioTrackView()
    }
}


@Composable
fun VideoAttractiveInfoitem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier.size(30.dp),
            tint = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = text, style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
    }
}

@Preview
@Composable
private fun LikeItemPriview() {
    MyApplicationTheme {
        VideoAttractiveInfoitem(icon = R.drawable.ic_heart, text = "1.5M") {

        }
    }
}

@Composable
fun SideBarView(
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit,
    onLikeClick: () -> Unit,
    onChatClick: () -> Unit,
    onSaveClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarView {
            onAvatarClick()
        }
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoitem(icon = R.drawable.ic_heart, text = "1.5M") {
            onLikeClick()
        }
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoitem(icon = R.drawable.ic_chat, text = "8136") {
            onChatClick()
        }
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoitem(icon = R.drawable.ic_bookmark, text = "90.0K") {
            onSaveClick()
        }
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoitem(icon = R.drawable.ic_share, text = "75.7K") {
            onShareClick()
        }
        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoitem(icon = R.drawable.ic_bookmark, text = "90.0K") {

        }
        AudioTrackView()
    }
}
@Preview
@Composable
private fun SideBarViewPriview() {
    MyApplicationTheme {
        SideBarView(
            onAvatarClick = { /*TODO*/ },
            onLikeClick = { /*TODO*/ },
            onChatClick = { /*TODO*/ },
            onSaveClick = { /*TODO*/ }) {
            
        }
    }
}
