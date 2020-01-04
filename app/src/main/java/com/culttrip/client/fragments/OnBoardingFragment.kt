package com.culttrip.client.fragments

import android.view.View
import com.culttrip.client.R
import io.vortex.android.ui.fragment.VortexBaseFragment
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

    }

}
