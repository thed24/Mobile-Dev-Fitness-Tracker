package com.mobiledev.fitnesstracker.controllers

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import com.mobiledev.fitnesstracker.ExerciseItem
import com.mobiledev.fitnesstracker.FITNESSTYPE
import com.mobiledev.fitnesstracker.R
import com.mobiledev.fitnesstracker.domain.ExerciseAdapter

class ModalController(
    private val context: Context,
    private val exerciseController: ExerciseController
) {
    private val WIDTH = 1000
    private val HEIGHT = 1250

    fun callCreateNewEntryForm(exerciseAdapter: ExerciseAdapter) {
        val entryFormDialog = Dialog(context).apply {
            setContentView(R.layout.create_exercise_form)
            setCancelable(false)
            window?.setLayout(WIDTH, HEIGHT)
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
                FITNESSTYPE = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                    FITNESSTYPE.RUNNING else
                    FITNESSTYPE.WALKING,
                pace = distanceTxt.text.toString().toFloat() / timeSpentTxt.text.toString()
                    .toFloat(),
            )

            exerciseController.addEntry(newItem)
            entryFormDialog.dismiss()
            exerciseAdapter.notifyDataSetChanged()
        }
    }

    fun callUpdateEntryForm(item: ExerciseItem, exerciseAdapter: ExerciseAdapter) {
        val entryFormDialog = Dialog(context).apply {
            setContentView(R.layout.create_exercise_form)
            setCancelable(false)
            window?.setLayout(WIDTH, HEIGHT)
        }

        val exerciseTypeRadioGroup =
            entryFormDialog.findViewById<RadioGroup>(R.id.exerciseTypeRadioGroup)
        val distanceTxt = entryFormDialog.findViewById<TextView>(R.id.distanceTxt)
        val timeSpentTxt = entryFormDialog.findViewById<TextView>(R.id.timeSpentTxt)
        val submitBtn = entryFormDialog.findViewById<Button>(R.id.submitBtn)

        exerciseTypeRadioGroup.check(if (item.FITNESSTYPE == FITNESSTYPE.RUNNING) 0 else 1)
        distanceTxt.text = item.distance.toString()
        timeSpentTxt.text = item.timeSpent.toString()

        entryFormDialog.show()

        submitBtn.setOnClickListener {
            Log.d("UPDATE_EXERCISE_ITEM", distanceTxt.text.toString())

            val newItem = ExerciseItem(
                id = item.id,
                distance = distanceTxt.text.toString().toFloat(),
                timeSpent = timeSpentTxt.text.toString().toFloat(),
                FITNESSTYPE = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                    FITNESSTYPE.RUNNING else
                    FITNESSTYPE.WALKING,
                pace = timeSpentTxt.text.toString().toFloat(),
            )

            exerciseController.updateEntry(newItem, item.id)
            entryFormDialog.dismiss()
            exerciseAdapter.notifyDataSetChanged()
        }
    }
}