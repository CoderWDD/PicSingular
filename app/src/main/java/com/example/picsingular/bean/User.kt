package com.example.picsingular.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var userId: Long,
    var username: String,
    val password: String,
    val avatar: String? = null,
    val signature: String? = null,
) : Parcelable