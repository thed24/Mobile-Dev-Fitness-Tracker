package com.mobiledev.fitnesstracker

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.*
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
    private var exerciseAdapter = ExerciseAdapter(this, exerciseListItems)
    private var DIALOGUE_WIDTH = 1000
    private var DIALOGUE_HEIGHT = 1250

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var addExerciseButton = findViewById<Button>(R.id.addExerciseBtn)
        addExerciseButton.setOnClickListener() {
            callCreateNewEntryForm()
        }

        var exerciseListView = findViewById<ListView>(R.id.exerciseList)
        exerciseListView.adapter = exerciseAdapter// Use scope functions?
    }

    private fun callCreateNewEntryForm() {
        var entryFormDialog = Dialog(this)
        entryFormDialog.setContentView(R.layout.create_exercise_form)
        entryFormDialog.setCancelable(false)
        entryFormDialog?.window?.setLayout(DIALOGUE_WIDTH, DIALOGUE_HEIGHT)

        var exerciseTypeRadioGroup = entryFormDialog.findViewById<RadioGroup>(R.id.exerciseTypeRadioGroup)
        var distanceTxt = entryFormDialog.findViewById<TextView>(R.id.distanceTxt)
        var timeSpentTxt = entryFormDialog.findViewById<TextView>(R.id.timeSpentTxt)
        var submitBtn = entryFormDialog.findViewById<Button>(R.id.submitBtn)

        entryFormDialog.show()

        submitBtn.setOnClickListener{
            Log.d("NEW_EXERCISE_ITEM", distanceTxt.text.toString());
            //TODO: Input validation
            exerciseListItems.add( ExerciseItem(
                id = exerciseListItems.size + 1,
                distance = distanceTxt.text.toString().toFloat(),
                timeSpent = timeSpentTxt.text.toString().toFloat(),
                FITNESS_TYPE = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                    FITNESS_TYPE.RUNNING else
                    FITNESS_TYPE.WALKING,
                pace = timeSpentTxt.text.toString().toFloat(),
            ))

            exerciseAdapter.notifyDataSetChanged()
            entryFormDialog.dismiss()
        }
    }
}