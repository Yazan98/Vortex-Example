package com.culttrip.client

import android.content.Context
import com.culttrip.domain.viewmodels.reg.LoginViewModel
import io.vortex.android.keys.ImageLoader
import io.vortex.android.models.VortexPrefsDetails
import io.vortex.android.models.ui.VortexNotificationDetails
import io.vortex.android.ui.VortexMessageDelegation
import io.vortex.android.utils.VortexApplication
import io.vortex.android.utils.VortexConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Created By : Yazan Tarifi
 * Date : 1/2/2020
 * Time : 2:32 AM
 */

class CulttripApplication : VortexApplication(), Thread.UncaughtExceptionHandler {

    private val messageDelegation: VortexMessageDelegation by lazy {
        VortexMessageDelegation()
    }

    override fun onCreate() {
        super.onCreate()
        GlobalScope.launch {
            VortexConfiguration
                .registerApplicationClass(this@CulttripApplication)
                .registerExceptionHandler(this@CulttripApplication)
                .registerApplicationState(true)
                .registerVortexPermissionsSettings()
                .registerImageLoader(ImageLoader.FRESCO)
                .registerCompatVector()
                .registerVortexPrefsConfiguration(
                VortexPrefsDetails(
                    packageName = this@CulttripApplication.packageName,
                    mode = Context.MODE_PRIVATE
                )
            )
        }

        startKoin {
            androidContext(this@CulttripApplication)
            modules(applicationModules)
        }

        GlobalScope.launch {
            registerApplicationChannels()
        }
    }

    private suspend fun registerApplicationChannels() {
        withContext(Dispatchers.IO) {
            notificationsController.createMultiNotificationChannels(
                arrayListOf(
                    VortexNotificationDetails(
                        getString(R.string.offers_notification_channel),
                        getString(R.string.offers_notification_channel_des),
                        getString(R.string.offers_notification_channel_id)
                    )
                ),
                this@CulttripApplication.applicationContext
            )
        }
    }

    private val applicationModules = module {
        viewModel { LoginViewModel() }
    }

    override fun uncaughtException(p0: Thread, p1: Throwable) {
        GlobalScope.launch {
            p1.message?.let {
                messageDelegation.showAlertDialog(
                    this@CulttripApplication.applicationContext,
                    getString(R.string.un_expected_error),
                    it
                )
            }
        }
    }

}
