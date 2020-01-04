package com.culttrip.client

import android.os.Bundle
import com.culttrip.client.screens.MainScreen
import com.culttrip.client.screens.RegisterScreen
import com.culttrip.client.utils.ApplicationKeys
import io.vortex.android.prefs.VortexPrefs
import io.vortex.android.ui.activity.VortexScreen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : VortexScreen() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            VortexPrefs.get(ApplicationKeys.USER_STATUS_KEY , false)?.let {
                when (it as Boolean) {
                    true -> startScreen<MainScreen>(true)
                    false -> startScreen<RegisterScreen>(true)
                }
            }
        }
    }

}
