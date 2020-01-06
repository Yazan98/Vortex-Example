package com.culttrip.client.fragments.reg

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import com.culttrip.client.R
import com.culttrip.client.utils.ApplicationKeys
import com.culttrip.data.models.body.RegisterBody
import com.culttrip.data.models.response.AuthResponse
import com.culttrip.data.models.response.ProfileLocation
import com.culttrip.domain.state.AuthAction
import com.culttrip.domain.state.AuthState
import com.culttrip.domain.viewmodels.reg.RegisterViewModel
import io.vortex.android.ui.fragment.VortexFragment
import io.vortex.android.utils.ui.hideView
import io.vortex.android.utils.ui.showView
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.ext.android.viewModel
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 5:27 PM
 */

class RegisterFragment @Inject constructor() : VortexFragment<AuthState, AuthAction, RegisterViewModel>() {
    private val registerViewModel: RegisterViewModel by viewModel(RegisterViewModel::class)

    override suspend fun getController(): RegisterViewModel {
        return registerViewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_register
    }

    override fun initScreen(view: View) {
        GlobalScope.launch {
            subscribeStateHandler()
        }

        RegisterButton?.apply {
            this.setOnClickListener {
                GlobalScope.launch {
                    getController().reduce(
                        AuthAction.RequestRegisterState(
                            RegisterBody(
                                username = UsernameField?.text.toString().trim(),
                                email = EmailField?.text.toString().trim(),
                                password = PasswordField?.text.toString().trim(),
                                phoneNumber = PhoneNumber?.text.toString().trim(),
                                pinCode = PinCodeField?.text.toString().trim(),
                                location = ProfileLocation(
                                    name = LocationNameField?.text.toString().trim()
                                )
                            )
                        )
                    )
                }
            }
        }
    }

    private suspend fun loadingState(state: Boolean) {
        withContext(Dispatchers.Main) {
            when (state) {
                true -> {
                    RegisterLoader?.showView()
                    linearLayout2?.visibility = View.INVISIBLE
                }

                false -> {
                    RegisterLoader?.hideView()
                    linearLayout2?.showView()
                }
            }
        }
    }

    private suspend fun subscribeStateHandler() {
        withContext(Dispatchers.Main) {
            registerViewModel.getStateHandler()?.let {
                it.observe(this@RegisterFragment, Observer {
                    GlobalScope.launch {
                        onStateChanged(it)
                    }
                })
            }

            registerViewModel.getLoadingStateHandler()?.let {
                it.observe(this@RegisterFragment, Observer {
                    GlobalScope.launch {
                        loadingState(it.getLoadingState())
                    }
                })
            }
        }
    }

    private suspend fun onAccountRegistered(authResponse: AuthResponse) {
        withContext(Dispatchers.Main) {
            activity?.let {
                it.getSharedPreferences(ApplicationKeys.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                    ?.let {
                        it.edit().putString(ApplicationKeys.ACCESS_TOKEN, authResponse.token)
                            .putString(ApplicationKeys.PIN_CODE_KET, authResponse.user.pinCode)
                            .putLong(ApplicationKeys.ACCOUNT_ID, authResponse.user.id).apply()
                    }

                messageController.showSnackbar(it, getString(R.string.register_welcome))
            }
        }
    }

    override suspend fun onStateChanged(newState: AuthState) {
        withContext(Dispatchers.IO) {
            when (newState) {
                is AuthState.LoadingState -> loadingState(newState.getLoading())
                is AuthState.LoginSuccessState -> onAccountRegistered(newState.getResponse())
            }
        }
    }

}
