package com.nexa.cinemate.data.utils

import android.content.Context
import android.util.Log
import androidx.core.content.edit

class UserManager(
    context : Context
) {

    val userPreferences = context.getSharedPreferences("UserInfos", Context.MODE_PRIVATE)

    companion object {
        private const val USER_NAME = ""
        private const val PROFILE_IMAGE = ""
    }


    fun setInfos(username : String, imageUrl : String) {
        userPreferences.edit() {
            putString(USER_NAME, username)
            putString(PROFILE_IMAGE, imageUrl)
        }
    }

    fun getUserImage() : String {
        return userPreferences.getString(PROFILE_IMAGE, null).toString()
    }

    fun signOut() {
        Log.d("UserManager", "Signing out...")
        val edit = userPreferences.edit()
        edit.clear()
        edit.apply()
        Log.d("UserManager", "Preferences cleared.")
    }

    fun getUserName() : String {
        return userPreferences.getString(USER_NAME, null).toString()
    }

}