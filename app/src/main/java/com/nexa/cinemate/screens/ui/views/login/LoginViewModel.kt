package com.nexa.cinemate.screens.ui.views.login

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexa.cinemate.data.database.services.firebase.GoogleAuthClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUIState>(LoginUIState.isEmpty)
    val loginState = _loginState.asStateFlow()

    fun onSucessSignIn(result : LoginUIState.Sucess) {
         _loginState.value = result
    }

    fun resetState() {
        _loginState.value = LoginUIState.isEmpty
    }

    fun signInWithIntent(googleAuthClient : GoogleAuthClient, intent : Intent) {
        viewModelScope.launch {
            try {
                val result = googleAuthClient.signInWithIntent(intent)
                if (result is LoginUIState.Sucess) {
                    onSucessSignIn(result)
                } else {
                    _loginState.value = LoginUIState.Error("Falha na autenticação.")
                }
            } catch (e: Exception) {
                _loginState.value = LoginUIState.Error("Erro ao processar login: ${e.message}")
            }
        }
    }



}