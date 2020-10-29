package com.mobiledev.fitnesstracker.dependencies

import android.content.Context
import androidx.room.Room
import com.mobiledev.fitnesstracker.activities.MainActivity
import com.mobiledev.fitnesstracker.activities.Modal
import com.mobiledev.fitnesstracker.controllers.BaseController
import com.mobiledev.fitnesstracker.controllers.ExerciseAdapter
import com.mobiledev.fitnesstracker.controllers.ExerciseController
import com.mobiledev.fitnesstracker.domain.ExerciseItem
import com.mobiledev.fitnesstracker.domain.ExerciseItemDao
import com.mobiledev.fitnesstracker.persistence.SQLExerciseItemRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainActivityModule(context: Context) {
    private val context: Context = context

    private val database = Room.databaseBuilder(
        context,
        SQLExerciseItemRepository::class.java, "exercise-item-database"
    ).build()

    @Provides
    @Singleton
    fun provideSQLExerciseItemRepository(): SQLExerciseItemRepository {
        return database;
    }

    @Provides
    @Singleton
    fun provideExerciseItemDao(): ExerciseItemDao {
        return database.exerciseItemDao()
    }

    @Provides
    @Singleton
    fun provideExerciseController(exerciseItemDao: ExerciseItemDao): BaseController<ExerciseItem> {
        return ExerciseController(exerciseItemDao)
    }

    @Provides
    @Singleton
    fun providesExerciseAdapter(exerciseController: ExerciseController, modal: Modal): ExerciseAdapter {
        return ExerciseAdapter(exerciseController, modal)
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