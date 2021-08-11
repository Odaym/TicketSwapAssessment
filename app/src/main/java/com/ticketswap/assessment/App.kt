package com.ticketswap.assessment

import android.app.Application
import com.ticketswap.assessment.di.application
import com.ticketswap.assessment.di.network
import com.ticketswap.assessment.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class App : Application(){

    override fun onCreate() {
        super.onCreate()

        appInstance = this

        initDependencyGraph()
    }

    protected open fun initDependencyGraph() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    application,
                    network,
                    viewModels
                )
            )
        }
    }

    companion object {
        lateinit var appInstance: App
    }
}