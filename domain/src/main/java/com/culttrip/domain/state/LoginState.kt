package com.culttrip.domain.state

import com.culttrip.data.models.body.LoginBody
import com.culttrip.data.models.response.AuthResponse
import io.vortex.android.VortexAction
import io.vortex.android.state.VortexState

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 2:21 PM
 */

open class LoginState : VortexState {

    class EmptyState: LoginState()
    class LoadingState(private val state: Boolean): LoginState() {
        fun getLoading() = state
    }

    class LoginSuccessState(private val response: AuthResponse): LoginState() {
        fun getResponse() = response
    }

    class LoginError(private val message: String): LoginState() {
        fun getErrorMessage() = message
    }

}

open class LoginAction: VortexAction {
    class RequestLoginState(private val requestBody: LoginBody): LoginAction() {
        fun getLoginDetails() = requestBody
    }
}
