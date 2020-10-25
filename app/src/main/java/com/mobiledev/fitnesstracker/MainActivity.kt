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
    var id: Int,
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
    private var exerciseController = ExerciseController(exerciseListItems)
    private var modalController = ModalController(this, exerciseController)
    private var exerciseAdapter = ExerciseAdapter(exerciseListItems, exerciseController, modalController)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var addExerciseButton = findViewById<Button>(R.id.addExerciseBtn)
        addExerciseButton.setOnClickListener {
            modalController.callCreateNewEntryForm(exerciseAdapter)
            exerciseAdapter.notifyDataSetChanged()
        }

        val viewManager = LinearLayoutManager(this)

        val exerciseView = findViewById<RecyclerView>(R.id.exerciseList).apply {
            adapter = exerciseAdapter
            layoutManager = viewManager
        }
    }
}