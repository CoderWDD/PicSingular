package com.example.picsingular.ui.home.community

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.picsingular.ui.components.banner.Banner
import com.example.picsingular.ui.components.banner.BannerData

@Composable
fun CommunityPage(navController: NavController) {
    // banner
    // TODO : add banner data from server
    val listBanner = mutableListOf<BannerData>()
    repeat(3) {
        listBanner.add(
            BannerData(
                title = "Title $it",
                imageUrl = "https://api.btstu.cn/sjbz/api.php",
                linkUrl = ""
            )
        )
    }
    Banner(listBanner, onClickListener = { linkUrl, title ->
        Log.e("wgw", "CommunityPage: $linkUrl $title", )
    }
    )
}
