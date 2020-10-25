package com.mobiledev.fitnesstracker

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView

class ModalController (
    private var context: Context,
    private var exerciseController: ExerciseController
){
    private var DIALOGUE_WIDTH = 1000
    private var DIALOGUE_HEIGHT = 1250

    fun callCreateNewEntryForm(exerciseAdapter: ExerciseAdapter) {
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

            val newItem = ExerciseItem(
                id = 0,
                distance = distanceTxt.text.toString().toFloat(),
                timeSpent = timeSpentTxt.text.toString().toFloat(),
                FITNESS_TYPE = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                    FITNESS_TYPE.RUNNING else
                    FITNESS_TYPE.WALKING,
                pace = distanceTxt.text.toString().toFloat() / timeSpentTxt.text.toString().toFloat(),
            )

            exerciseController.addEntry(newItem)
            entryFormDialog.dismiss()
            exerciseAdapter.notifyDataSetChanged()
        }
    }

    fun callUpdateEntryForm(item: ExerciseItem, exerciseAdapter: ExerciseAdapter) {
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

            var newItem = ExerciseItem(
                id = item.id,
                distance = distanceTxt.text.toString().toFloat(),
                timeSpent = timeSpentTxt.text.toString().toFloat(),
                FITNESS_TYPE = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                    FITNESS_TYPE.RUNNING else
                    FITNESS_TYPE.WALKING,
                pace = timeSpentTxt.text.toString().toFloat(),
            )

            exerciseController.updateEntry(newItem, item.id)
            entryFormDialog.dismiss()
            exerciseAdapter.notifyDataSetChanged()
        }
    }
}