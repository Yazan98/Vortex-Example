package com.culttrip.domain.viewmodels.reg

import com.culttrip.data.models.body.RegisterBody
import com.culttrip.data.models.response.AuthResponse
import com.culttrip.data.models.response.ProfileLocation
import com.culttrip.data.models.response.UserResponse
import com.culttrip.data.repository.AuthRepository
import com.culttrip.domain.state.AuthAction
import com.culttrip.domain.state.AuthState
import io.vortex.android.errors.VortexException
import io.vortex.android.models.VortexExceptionDetails
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

open class RegisterViewModel @Inject constructor() : VortexViewModel<AuthState, AuthAction>() {

    private val loginRepository: AuthRepository by lazy {
        AuthRepository()
    }

    override suspend fun getInitialState(): AuthState {
        return AuthState.EmptyState()
    }

    override suspend fun reduce(newAction: AuthAction) {
        when (newAction) {
            is AuthAction.RequestLoginState -> loginAccount(newAction)
            is AuthAction.RequestRegisterState -> registerAccount(newAction.getDetails())
        }
    }

    private suspend fun loginAccount(login: AuthAction.RequestLoginState) {
        withContext(Dispatchers.IO) {
            when {
                login.getLoginDetails().email.isEmpty() -> handleError("Invalid Email")
                login.getLoginDetails().password.isEmpty() -> handleError("Invalid Password")
                else -> {
                    acceptLoadingState(true)
                    addRxRequest(loginRepository.login(login.getLoginDetails()).subscribe({
                        GlobalScope.launch {
                            handleSuccessState(AuthState.LoginSuccessState(it.data))
                        }
                    }, {
                        GlobalScope.launch {
                            it.message?.let {
                                handleErrorState(ConnectionException(it))
                            }
                        }
                    }))
                }
            }
        }
    }

    class ConnectionException(override val message: String?) : VortexException(object : VortexExceptionDetails<Any> {
        override fun getExceptionBody(): Any {
            return AuthResponse(user = UserResponse(location = ProfileLocation()))
        }

        override fun getExceptionMessage(): String {
            return message.toString()
        }

    })

    private suspend fun registerAccount(body: RegisterBody) {
        withContext(Dispatchers.IO) {
            when {
                body.email.isEmpty() -> handleError("Email Required")
                body.password.isEmpty() -> handleError("Password Required")
                body.phoneNumber.isEmpty() -> handleError("PhoneNumber Required")
                body.pinCode.isEmpty() -> handleError("Pin Code Required")
                else -> {
                    acceptNewState(AuthState.LoadingState(true))
                    addRxRequest(loginRepository.register(body).subscribe({
                        GlobalScope.launch {
                            acceptNewState(AuthState.LoadingState(false))
                            acceptNewState(AuthState.LoginSuccessState(it.data))
                        }
                    }, {
                        it.message?.let {
                            GlobalScope.launch {
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
            acceptNewState(AuthState.LoginError(message))
        }
    }
}