package com.example.picsingular.common.utils.navhost

import android.net.Uri
import android.os.Parcelable
import androidx.navigation.NavHostController

object NavHostUtil {
    fun navigateTo(
        navHostController: NavHostController,
        destinationRouteName: String,
        args: Any? = null,
        singleTop: Boolean = false,
        restoreStates: Boolean = true
    ){
        val parameter = formatArgs(args)
        navHostController.navigate("$destinationRouteName$parameter"){
            launchSingleTop = singleTop
            restoreState = restoreStates
        }
    }

    fun navigateBack(
        navHostController: NavHostController,
        backRouteName: String? = null,
        saveStates: Boolean = true,
        args: Any? = null,
    ){
        val parameter = formatArgs(args)
        if (backRouteName == null) {
            navHostController.popBackStack()
        } else {
            navHostController.popBackStack(route = "$backRouteName$parameter", inclusive = true, saveState = saveStates)
        }
    }

    private fun formatArgs(args: Any?): String {
        return if (args != null) {
            if (args is Parcelable) String.format("/%s",Uri.encode(args.toJson())) else String.format("/%s",args.toString())
        } else {
            ""
        }
    }
}