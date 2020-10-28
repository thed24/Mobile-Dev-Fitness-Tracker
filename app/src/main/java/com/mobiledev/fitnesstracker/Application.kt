package com.mobiledev.fitnesstracker

import android.app.Application
import com.mobiledev.fitnesstracker.dependencies.DaggerMainActivityComponent
import com.mobiledev.fitnesstracker.dependencies.MainActivityModule
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class Application : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: AndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerMainActivityComponent.builder()
            .mainActivityModule(MainActivityModule(this))
            .build()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}