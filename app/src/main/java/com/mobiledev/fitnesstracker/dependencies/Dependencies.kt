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
    private val context = context
    private val database = Room.databaseBuilder(
        context,
        SQLExerciseItemRepository::class.java, "exercise-item-database"
    ).build()

    private val exerciseItemDao = database.exerciseItemDao()

    @Provides
    @Singleton
    fun provideExerciseController(): BaseController<ExerciseItem> {
        return ExerciseController(exerciseItemDao)
    }

    @Provides
    @Singleton
    fun provideModal(): Modal {
        var exerciseController = provideExerciseController()
        return Modal(context, exerciseController)
    }

    @Provides
    @Singleton
    fun provideExerciseAdapter(): ExerciseAdapter {
        val modal = provideModal()
        var exerciseController = provideExerciseController()
        return ExerciseAdapter(exerciseController, modal)
    }
}

@Component(modules = [MainActivityModule::class])
@Singleton
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity?)
}
