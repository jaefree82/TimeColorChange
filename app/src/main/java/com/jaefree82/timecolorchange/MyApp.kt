package com.jaefree82.timecolorchange

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.uber.rxdogtag.RxDogTag

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        RxDogTag.install()
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}