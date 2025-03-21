package com.nexa.cinemate.data.database.services.firebase

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.nexa.cinemate.R
import com.nexa.cinemate.data.models.response.user.UserDataProvider
import com.nexa.cinemate.screens.ui.views.login.LoginUIState
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException


class GoogleAuthClient(
    private val context : Context,
    private val onTapClient : SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            onTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("")
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    suspend fun signOut() {
        try {
            onTapClient.signOut().await()
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserDataProvider? = auth.currentUser?.run {
        UserDataProvider(
            userid = uid,
            username = displayName.toString(),
            photoUrl = photoUrl.toString()
        )
    }

    suspend fun signInWithIntent(intent: Intent): LoginUIState {
        val credential = onTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            val data = user?.run {
                UserDataProvider(
                    username = user.displayName.toString(),
                    userid = uid,
                    photoUrl = user.photoUrl.toString()
                )
            }
            LoginUIState.Sucess(
                data = data,
                message = "Sucess"
            )
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            LoginUIState.Error(
                message = e.message.toString()
            )
        }
    }

}



