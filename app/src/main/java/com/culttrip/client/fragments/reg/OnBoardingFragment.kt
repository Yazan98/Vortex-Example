package com.culttrip.client.fragments.reg

import android.view.View
import androidx.navigation.findNavController
import com.culttrip.client.R
import io.vortex.android.ui.fragment.VortexBaseFragment
import kotlinx.android.synthetic.main.fragment_on_boarding.*
import javax.inject.Inject

/**
 * Created By : Yazan Tarifi
 * Date : 1/4/2020
 * Time : 8:15 PM
 */

class OnBoardingFragment @Inject constructor() : VortexBaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_on_boarding
    }

    override fun initScreen(view: View) {

        BoardingLogo?.apply {
            this.setImageURI("res:/" + R.drawable.logo_2)
        }

        ContinueButton?.apply {
            this.setOnClickListener {
                findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
            }
        }

    }

}
