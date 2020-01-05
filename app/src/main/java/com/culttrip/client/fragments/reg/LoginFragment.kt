package com.culttrip.client.fragments.reg

import android.view.View
import com.culttrip.client.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 3:06 AM
 */

class LoginFragment @Inject constructor() : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun initScreen(view: View) {
        LogoLogin?.apply {
            this.setImageURI("res:/" + R.drawable.logo_2)
        }

        LoginButton?.apply {
            this.setOnClickListener {
                GlobalScope.launch {
                    when {
                        EmailField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.email_required))
                        PasswordField?.text.toString().trim().isEmpty() -> showMessage(getString(R.string.pass_required))
                        else -> {

                        }
                    }
                }
            }
        }

        SignUpButton?.apply {
            this.setOnClickListener {

            }
        }
    }

    private suspend fun showMessage(message: String) {
        activity?.let {
            messageController.showSnackbar(it , message)
        }
    }

}
