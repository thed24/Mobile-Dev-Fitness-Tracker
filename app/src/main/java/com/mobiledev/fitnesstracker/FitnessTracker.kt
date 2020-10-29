package com.mobiledev.fitnesstracker

import android.app.Application
import com.mobiledev.fitnesstracker.dependencies.DaggerMainActivityComponent
import com.mobiledev.fitnesstracker.dependencies.MainActivityComponent
import com.mobiledev.fitnesstracker.dependencies.MainActivityModule

class FitnessTracker : Application() {
    companion object {
        lateinit var mainActivityComponent: MainActivityComponent
    }

    override fun onCreate() {
        super.onCreate()
        createComponent()
    }

    private fun createComponent() {
        mainActivityComponent = DaggerMainActivityComponent.builder()
            .mainActivityModule(MainActivityModule(this))
            .build()
    }
}