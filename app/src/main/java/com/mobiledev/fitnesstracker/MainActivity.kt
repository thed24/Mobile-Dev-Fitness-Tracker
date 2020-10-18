package com.mobiledev.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExerciseItem(
    val id: Int,
    val distance: Float,
    val timeSpent: Float,
    val FITNESS_TYPE: FITNESS_TYPE,
    val pace: Float
) : Parcelable

enum class FITNESS_TYPE {
    WALKING, RUNNING
}

class MainActivity : AppCompatActivity() {
    private var exerciseListItems = mutableListOf<ExerciseItem>()
    private var exerciseController = ExerciseController(exerciseListItems, this)
    private var exerciseAdapter = ExerciseAdapter(exerciseListItems, exerciseController)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var addExerciseButton = findViewById<Button>(R.id.addExerciseBtn)
        addExerciseButton.setOnClickListener {
            var positionStart = exerciseListItems.size
            exerciseController.callCreateNewEntryForm()
            exerciseAdapter.notifyItemRangeInserted(positionStart, exerciseListItems.size)
        }

        val viewManager = LinearLayoutManager(this)

        val exerciseView = findViewById<RecyclerView>(R.id.exerciseList).apply {
            adapter = exerciseAdapter
            layoutManager = viewManager
        }
    }
}