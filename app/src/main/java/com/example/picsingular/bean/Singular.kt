package com.example.picsingular.bean

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

@Parcelize
data class Singular (
    val singularId: Long,
    val userId: Long,
    val likeCount: Int,
    val commentCount: Int,
    val readCount: Int,
    val title: String,
    val description: String,
    val singularStatus: String,
    val imageList: List<ImageUrl>? = null,
    val pushDate: String,
    val user: User
): Parcelable