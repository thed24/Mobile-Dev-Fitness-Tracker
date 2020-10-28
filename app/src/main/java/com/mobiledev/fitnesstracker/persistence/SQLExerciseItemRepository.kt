package com.mobiledev.fitnesstracker.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mobiledev.fitnesstracker.domain.ExerciseItem
import com.mobiledev.fitnesstracker.domain.ExerciseItemDao

@Database(entities = [ExerciseItem::class], version = 1)
@TypeConverters(ExerciseTypeConverter::class)
abstract class SQLExerciseItemRepository : RoomDatabase() {
    abstract fun exerciseItemDao(): ExerciseItemDao
}