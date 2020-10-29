package com.mobiledev.fitnesstracker.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobiledev.fitnesstracker.persistence.ExerciseType
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ExerciseItem(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "distance") var distance: Float,
    @ColumnInfo(name = "time_spent") var timeSpent: Float,
    @ColumnInfo(name = "exercise_type") var ExerciseType: ExerciseType,
    @ColumnInfo(name = "pace") var pace: Float
) : Parcelable

