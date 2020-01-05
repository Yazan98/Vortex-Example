package com.culttrip.client.fragments.reg

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import com.culttrip.client.R
import com.culttrip.client.screens.MainScreen
import com.culttrip.client.utils.ApplicationKeys
import com.culttrip.data.models.body.LoginBody
import com.culttrip.data.models.response.AuthResponse
import com.culttrip.domain.state.LoginAction
import com.culttrip.domain.state.LoginState
import com.culttrip.domain.viewmodels.reg.LoginViewModel
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.ui.hideView
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 3:06 AM
 */

class LoginFragment @Inject constructor() : VortexFragment<LoginState, LoginAction, LoginViewModel>() {
    private val loginViewModel: LoginViewModel by viewModel(LoginViewModel::class)

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override suspend fun getController(): LoginViewModel {
        return loginViewModel
    }

    override fun initScreen(view: View) {

        GlobalScope.launch {
            subscribeStateHandler()
        }

        LogoLogin?.apply {
            this.setImageURI("res:/" + R.drawable.logo_2)
        }

        LoginButton?.apply {
            this.setOnClickListener {
                GlobalScope.launch {
                    getController().reduce(
                        LoginAction.RequestLoginState(
                            LoginBody(
                                EmailField?.text.toString().trim(),
                                PasswordField?.text.toString().trim()
                            )
                        )
                    )
                }
            }
        }

        SignUpButton?.apply {
            this.setOnClickListener {

            }
        }
    }

    private suspend fun subscribeStateHandler() {
        withContext(Dispatchers.Main) {
            loginViewModel.getVortexStore()?.let {
                it.getStateObserver().observe(this@LoginFragment, Observer {
                    GlobalScope.launch {
                        onStateChanged(it)
                    }
                })
            }
        }
    }

    override suspend fun onStateChanged(newState: LoginState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is LoginState.LoginError -> showMessage(newState.getErrorMessage())
                is LoginState.LoginSuccessState -> showAccountInfo(newState.getResponse())
                is LoginState.LoadingState -> startLoading(newState.getLoading())
            }
        }
    }

    private suspend fun startLoading(state: Boolean) {
        withContext(Dispatchers.Main) {
            when (state) {
                true -> {
                    LoginDetailsContainer?.hideView()
                    LoginButton?.hideView()
                    LoginProgress?.showView()
                }

                false -> {
                    LoginDetailsContainer?.showView()
                    LoginButton?.showView()
                    LoginProgress?.hideView()
                }
            }
        }
    }

    private suspend fun showAccountInfo(authResponse: AuthResponse) {
        withContext(Dispatchers.Main) {
            showMessage(getString(R.string.login_success))
            activity?.let {
                it.getSharedPreferences(ApplicationKeys.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                    ?.let {
                        it.edit()
                            .putBoolean(ApplicationKeys.USER_STATUS_KEY, true)
                            .putString(ApplicationKeys.ACCESS_TOKEN, authResponse.token)
                            .putLong(ApplicationKeys.ACCOUNT_ID, authResponse.user.id).apply()
                    }
            }

            if (authResponse.user.accountStatus.equals("ACTIVATED")) {
                startScreen<MainScreen>(true)
            } else {
                showMessage(getString(R.string.account_disabled))
            }
        }
    }

    private suspend fun showMessage(message: String) {
        activity?.let {
            messageController.showSnackbar(it, message)
        }
    }

}
