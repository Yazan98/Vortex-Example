package com.culttrip.domain.state

import com.culttrip.data.models.body.LoginBody
import com.culttrip.data.models.body.RegisterBody
import com.culttrip.data.models.response.AuthResponse
import io.vortex.android.VortexAction
import io.vortex.android.state.VortexState

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 2:21 PM
 */

open class AuthState : VortexState {

    class EmptyState: AuthState()
    class LoadingState(private val state: Boolean): AuthState() {
        fun getLoading() = state
    }

    class LoginSuccessState(private val response: AuthResponse): AuthState() {
        fun getResponse() = response
    }

    class LoginError(private val message: String): AuthState() {
        fun getErrorMessage() = message
    }

}

open class AuthAction: VortexAction {

    class RequestLoginState(private val requestBody: LoginBody): AuthAction() {
        fun getLoginDetails() = requestBody
    }

    class RequestRegisterState(private val body: RegisterBody): AuthAction() {
        fun getDetails() = body
    }

}
