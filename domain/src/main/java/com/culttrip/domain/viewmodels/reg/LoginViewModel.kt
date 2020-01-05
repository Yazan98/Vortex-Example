package com.culttrip.domain.viewmodels.reg

import com.culttrip.data.repository.AuthRepository
import com.culttrip.domain.state.LoginAction
import com.culttrip.domain.state.LoginState
import io.vortex.android.reducer.VortexViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 2:20 PM
 */

open class LoginViewModel @Inject constructor() : VortexViewModel<LoginState, LoginAction>() {

    private val loginRepository: AuthRepository by lazy {
        AuthRepository()
    }

    override suspend fun getInitialState(): LoginState {
        return LoginState.EmptyState()
    }

    override suspend fun reduce(newAction: LoginAction) {
        when (newAction) {
            is LoginAction.RequestLoginState -> loginAccount(newAction)
        }
    }

    private suspend fun loginAccount(login: LoginAction.RequestLoginState) {
        withContext(Dispatchers.IO) {
            when {
                login.getLoginDetails().email.isEmpty() -> handleError("InvalidEmail")
                login.getLoginDetails().password.isEmpty() -> handleError("InvalidPassword")
                else -> {
                    acceptNewState(LoginState.LoadingState(true))
                    addRxRequest(loginRepository.login(login.getLoginDetails()).subscribe({
                        GlobalScope.launch {
                            println("Request : OnSuccess")
                            acceptNewState(LoginState.LoadingState(false))
                            acceptNewState(LoginState.LoginSuccessState(it.data))
                        }
                    }, {
                        it.message?.let {
                            GlobalScope.launch {
                                println("Request : OnError")
                                acceptNewState(LoginState.LoadingState(false))
                                handleError(it)
                            }
                        }
                    }))
                }
            }
        }
    }

    private suspend fun handleError(message: String) {
        withContext(Dispatchers.IO) {
            acceptNewState(LoginState.LoginError(message))
        }
    }
}