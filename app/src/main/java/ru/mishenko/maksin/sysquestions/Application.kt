package ru.mishenko.maksin.sysquestions

import android.app.Application
import ru.mishenko.maksin.sysquestions.di.initDi

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initDi()
    }
}