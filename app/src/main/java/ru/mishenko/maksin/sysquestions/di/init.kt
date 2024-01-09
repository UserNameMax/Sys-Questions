package ru.mishenko.maksin.sysquestions.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.mishenko.maksin.sysquestions.Application

fun Application.initDi() =
    startKoin {
        androidContext(this@initDi)
        modules(module)
    }