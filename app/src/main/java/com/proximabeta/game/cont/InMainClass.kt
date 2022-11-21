package com.proximabeta.game.cont

import android.app.Application
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk


class InMainClass : Application() {

    companion object {
        const val osNal = "0ef8d92d-6ca5-4aba-9131-11021f269f01"
        var appsCheck = "appsChecker"
        var C1: String? = "c11"
        var link = "link"
        var MAIN_ID: String? = ""
        var DEEPL: String? = ""
    }

    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(osNal)

        Hawk.init(this).build()
    }
}
