package com.culttrip.client

import android.content.Context
import android.os.Bundle
import com.culttrip.client.screens.MainScreen
import com.culttrip.client.screens.RegisterScreen
import com.culttrip.client.utils.ApplicationKeys
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
            getSharedPreferences(ApplicationKeys.SHARED_PREFS_NAME , Context.MODE_PRIVATE)?.let { it ->
                it.getBoolean(ApplicationKeys.USER_STATUS_KEY , false).let {
                    when (it) {
                        true -> startScreen<MainScreen>(true)
                        false -> startScreen<RegisterScreen>(true)
                    }
                }
            }
        }
    }

}
