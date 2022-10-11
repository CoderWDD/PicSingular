package com.example.picsingular.common.utils.images

import com.example.picsingular.common.constants.HttpConstants

object ImageUrlUtil {
    fun getAvatarUrl(username: String, fileName: String): String {
        return HttpConstants.BASE_AVATAR_URL + username + "/avatar/" + fileName
    }

    fun getImageUrl(username:String, imageUrl: String): String{
        return HttpConstants.BASE_IMAGE_URL + username + "/" + imageUrl
    }

    fun getBannerUrl(url: String): String{
        return HttpConstants.BASE_BANNER_URL + url
    }

    fun getPicBedImage(url: String): String{
        return HttpConstants.PIC_BED_IMAGE_URL + url
    }
}