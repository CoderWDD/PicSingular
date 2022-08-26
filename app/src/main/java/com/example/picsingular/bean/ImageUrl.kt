package com.example.picsingular.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageUrl (
    val imageId: Long? = null,
    var imageUrl: String? = null
) : Parcelable