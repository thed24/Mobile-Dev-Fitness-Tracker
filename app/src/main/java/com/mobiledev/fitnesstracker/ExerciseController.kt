package com.mobiledev.fitnesstracker

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView

class ExerciseController(
    private var exerciseListItems: MutableList<ExerciseItem>,
    private var context: Context
) {
    private var DIALOGUE_WIDTH = 1000
    private var DIALOGUE_HEIGHT = 1250

    fun callCreateNewEntryForm() {
        var entryFormDialog = Dialog(context).apply {
            setContentView(R.layout.create_exercise_form)
            setCancelable(false)
            window?.setLayout(DIALOGUE_WIDTH, DIALOGUE_HEIGHT)
        }

        var exerciseTypeRadioGroup = entryFormDialog.findViewById<RadioGroup>(R.id.exerciseTypeRadioGroup)
        var distanceTxt = entryFormDialog.findViewById<TextView>(R.id.distanceTxt)
        var timeSpentTxt = entryFormDialog.findViewById<TextView>(R.id.timeSpentTxt)
        var submitBtn = entryFormDialog.findViewById<Button>(R.id.submitBtn)

        entryFormDialog.show()

        submitBtn.setOnClickListener{
            Log.d("NEW_EXERCISE_ITEM", distanceTxt.text.toString());

            exerciseListItems.add( ExerciseItem(
                id = exerciseListItems.size,
                distance = distanceTxt.text.toString().toFloat(),
                timeSpent = timeSpentTxt.text.toString().toFloat(),
                FITNESS_TYPE = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                    FITNESS_TYPE.RUNNING else
                    FITNESS_TYPE.WALKING,
                pace = timeSpentTxt.text.toString().toFloat(),
            ))

            entryFormDialog.dismiss()
        }
    }

    fun callUpdateEntryForm(item: ExerciseItem) {
        var entryFormDialog = Dialog(context).apply {
            setContentView(R.layout.create_exercise_form)
            setCancelable(false)
            window?.setLayout(DIALOGUE_WIDTH, DIALOGUE_HEIGHT)
        }

        var exerciseTypeRadioGroup = entryFormDialog.findViewById<RadioGroup>(R.id.exerciseTypeRadioGroup)
        var distanceTxt = entryFormDialog.findViewById<TextView>(R.id.distanceTxt)
        var timeSpentTxt = entryFormDialog.findViewById<TextView>(R.id.timeSpentTxt)
        var submitBtn = entryFormDialog.findViewById<Button>(R.id.submitBtn)

        exerciseTypeRadioGroup.check( if (item.FITNESS_TYPE == FITNESS_TYPE.RUNNING) 0 else 1 )
        distanceTxt.text = item.distance.toString()
        timeSpentTxt.text = item.timeSpent.toString()

        entryFormDialog.show()

        submitBtn.setOnClickListener{
            Log.d("UPDATE_EXERCISE_ITEM", distanceTxt.text.toString())

            var oldItem = exerciseListItems.find {
                    exercise -> exercise.id == item.id
            }

            var newItem = ExerciseItem(
                id = item.id,
                distance = distanceTxt.text.toString().toFloat(),
                timeSpent = timeSpentTxt.text.toString().toFloat(),
                FITNESS_TYPE = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                    FITNESS_TYPE.RUNNING else
                    FITNESS_TYPE.WALKING,
                pace = timeSpentTxt.text.toString().toFloat(),
            )

            if (oldItem != null) {
                exerciseListItems[oldItem.id] = newItem
            }

            entryFormDialog.dismiss()
        }
    }

    fun removeEntry(item: ExerciseItem) {
        exerciseListItems.remove(item)
    }
}