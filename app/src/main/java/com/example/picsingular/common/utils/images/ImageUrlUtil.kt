package com.example.picsingular.common.utils.images

import com.example.picsingular.common.constants.HttpConstants

object ImageUrlUtil {
    fun getAvatarUrl(username: String): String {
        return HttpConstants.BASE_AVATAR_URL + username
    }

    fun getImageUrl(imageUrl: String, singularId: Long): String{
        return HttpConstants.BASE_IMAGE_URL + singularId + "/" + imageUrl
    }
}