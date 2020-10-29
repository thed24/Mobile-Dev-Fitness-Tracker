package com.mobiledev.fitnesstracker.activities

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import com.mobiledev.fitnesstracker.domain.ExerciseItem
import com.mobiledev.fitnesstracker.persistence.ExerciseType
import com.mobiledev.fitnesstracker.R
import com.mobiledev.fitnesstracker.controllers.ExerciseAdapter
import com.mobiledev.fitnesstracker.controllers.ExerciseController

class Modal(
    private val context: Context,
    private val exerciseController: ExerciseController
) {
    private val width = 1000
    private val height = 1250

    fun createEntryForm(exerciseAdapter: ExerciseAdapter) {
        val entryFormDialog = Dialog(context).apply {
            setContentView(R.layout.create_exercise_form)
            setCancelable(false)
            window?.setLayout(width, height)
        }

        val exerciseTypeRadioGroup =
            entryFormDialog.findViewById<RadioGroup>(R.id.exerciseTypeRadioGroup)
        val distanceTxt = entryFormDialog.findViewById<TextView>(R.id.distanceTxt)
        val timeSpentTxt = entryFormDialog.findViewById<TextView>(R.id.timeSpentTxt)
        val submitBtn = entryFormDialog.findViewById<Button>(R.id.submitBtn)

        entryFormDialog.show()

        submitBtn.setOnClickListener {
            Log.d("NEW_EXERCISE_ITEM", distanceTxt.text.toString())

            val newItem = ExerciseItem(
                id = 0,
                distance = distanceTxt.text.toString().toFloat(),
                timeSpent = timeSpentTxt.text.toString().toFloat(),
                ExerciseType = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                    ExerciseType.RUNNING else
                    ExerciseType.WALKING,
                pace = distanceTxt.text.toString().toFloat() / timeSpentTxt.text.toString().toFloat(),
            )

            exerciseController.addEntry(newItem)
            entryFormDialog.dismiss()
            exerciseAdapter.notifyDataSetChanged()
        }
    }

    fun updateEntryForm(item: ExerciseItem, exerciseAdapter: ExerciseAdapter) {
        val entryFormDialog = Dialog(context).apply {
            setContentView(R.layout.create_exercise_form)
            setCancelable(false)
            window?.setLayout(width, height)
        }

        val exerciseTypeRadioGroup =
            entryFormDialog.findViewById<RadioGroup>(R.id.exerciseTypeRadioGroup)
        val distanceTxt = entryFormDialog.findViewById<TextView>(R.id.distanceTxt)
        val timeSpentTxt = entryFormDialog.findViewById<TextView>(R.id.timeSpentTxt)
        val submitBtn = entryFormDialog.findViewById<Button>(R.id.submitBtn)
        val buttonToCheck = if (item.ExerciseType == ExerciseType.RUNNING)
            R.id.runningRadioBtn else
            R.id.walkingRadioBtn

        exerciseTypeRadioGroup.check(buttonToCheck)
        distanceTxt.text = item.distance.toString()
        timeSpentTxt.text = item.timeSpent.toString()

        entryFormDialog.show()

        submitBtn.setOnClickListener {
            Log.d("UPDATE_EXERCISE_ITEM", distanceTxt.text.toString())

            val newItem = ExerciseItem(
                id = item.id,
                distance = distanceTxt.text.toString().toFloat(),
                timeSpent = timeSpentTxt.text.toString().toFloat(),
                ExerciseType = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                    ExerciseType.RUNNING else
                    ExerciseType.WALKING,
                pace = distanceTxt.text.toString().toFloat() / timeSpentTxt.text.toString().toFloat(),
            )

            exerciseController.updateEntry(newItem)
            entryFormDialog.dismiss()
            exerciseAdapter.notifyDataSetChanged()
        }
    }
}