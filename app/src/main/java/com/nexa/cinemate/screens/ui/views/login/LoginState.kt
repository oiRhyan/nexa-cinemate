package com.nexa.cinemate.screens.ui.views.login

import com.nexa.cinemate.data.models.response.user.UserDataProvider


sealed class LoginUIState {
    data object isEmpty : LoginUIState()
    data class Sucess(val data: UserDataProvider?, val message: String) : LoginUIState()
    data class Error(val message : String) : LoginUIState()
}