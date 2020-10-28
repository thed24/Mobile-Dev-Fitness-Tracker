package com.mobiledev.fitnesstracker.dependencies

import android.content.Context
import androidx.room.Room
import com.mobiledev.fitnesstracker.activities.MainActivity
import com.mobiledev.fitnesstracker.persistence.SQLExerciseItemRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainActivityModule(context: Context) {
    private val context: Context = context

    @Provides
    @Singleton
    fun provideSQLExerciseItemRepository(): SQLExerciseItemRepository {
        return Room.databaseBuilder(
            context,
            SQLExerciseItemRepository::class.java, "exercise-item-database"
        ).build()
    }

    @Provides
    fun context(): Context {
        return context
    }
}

@Component(modules = [MainActivityModule::class])
@Singleton
interface MainActivityComponent {
    fun context(): Context?
    fun inject(mainActivity: MainActivity?)
}