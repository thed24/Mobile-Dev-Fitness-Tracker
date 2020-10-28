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
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "distance")val distance: Float,
    @ColumnInfo(name = "time_spent")val timeSpent: Float,
    @ColumnInfo(name = "exercise_type")val ExerciseType: ExerciseType,
    @ColumnInfo(name = "pace")val pace: Float
) : Parcelable

