package com.example.picsingular.ui.components.banner

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.picsingular.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    list: List<BannerData>?,
    timeMillis: Long = 3000L,
    @DrawableRes placeholderImage: Int = R.drawable.banner_placeholder,
    indicatorAlignment: Alignment = Alignment.BottomCenter,
    onClickListener: (linkUrl: String, title: String) -> Unit
) {
    // get the list size and define the pagerState variable, make the initial value to be bannerSize / 2
    val bannerSize = list?.size ?: 5
    val pagerState = rememberPagerState(initialPage = bannerSize / 2)

    LaunchedEffect(key1 = pagerState.currentPage) {
        delay(timeMillis)
        if (!list.isNullOrEmpty()){
            // list 获取到后，再进行跳转展示
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % bannerSize)
        }
//        pagerState.scrollToPage((pagerState.currentPage + 1) % bannerSize)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(vertical = 8.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            count = bannerSize,
            contentPadding = PaddingValues(horizontal = 24.dp),
        ) { page ->
            Card(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 0.4f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0.1f, 0.3f)
                        )
                    }
                    .clickable {
                        onClickListener(
                            list?.get(page)?.linkUrl ?: "",
                            list?.get(page)?.title ?: ""
                        )
                    }
            ) {
                // image for banner
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    SubcomposeAsyncImage(
                        model = list?.get(page)?.imageUrl,
                        contentDescription = "Banner image",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    ) {
                        when (painter.state) {
                            is AsyncImagePainter.State.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(indicatorAlignment)
                                )
                            }
                            is AsyncImagePainter.State.Error -> {
                                Image(
                                    painter = painterResource(id = R.drawable.banner_placeholder),
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Banner image",
                                )
                            }
                            else -> {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
                }
            }

            // circle for the indicator
            Box(
                modifier = Modifier
                    .align(indicatorAlignment)
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 0 until bannerSize) {
                        var size by remember { mutableStateOf(4.dp) }
                        size = if (i == pagerState.currentPage) 8.dp else 4.dp
                        val color = if (i == pagerState.currentPage) Color.Gray else Color.White
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color)
                                .animateContentSize()
                                .size(size)
                        )
                        if (i != bannerSize - 1) Spacer(modifier = Modifier.size(4.dp))
                    }
                }
            }
        }
    }
}

