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
import javax.inject.Inject

class Modal @Inject constructor(
    private var context: Context,
    private val exerciseController: ExerciseController
) {
    private lateinit var exerciseTypeRadioGroup: RadioGroup
    private lateinit var timeSpentTxt: TextView
    private lateinit var distanceTxt: TextView
    private lateinit var submitBtn: Button
    private lateinit var entryFormDialog: Dialog

    fun createEntryForm(exerciseAdapter: ExerciseAdapter) {
        drawModal()
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
                pace = timeSpentTxt.text.toString().toFloat() / distanceTxt.text.toString().toFloat(),
            )

            exerciseController.addEntry(newItem)
            entryFormDialog.dismiss()
            exerciseAdapter.notifyDataSetChanged()
        }
    }

    fun updateEntryForm(item: ExerciseItem, exerciseAdapter: ExerciseAdapter) {
        drawModal()

        var buttonToCheck = if (item.ExerciseType == ExerciseType.RUNNING)
            R.id.runningRadioBtn else
            R.id.walkingRadioBtn

        exerciseTypeRadioGroup.check(buttonToCheck)
        distanceTxt.text = item.distance.toString()
        timeSpentTxt.text = item.timeSpent.toString()

        entryFormDialog.show()

        submitBtn.setOnClickListener {
            item.distance = distanceTxt.text.toString().toFloat()
            item.timeSpent = timeSpentTxt.text.toString().toFloat()
            item.ExerciseType = if (exerciseTypeRadioGroup.checkedRadioButtonId == 0)
                ExerciseType.RUNNING else
                ExerciseType.WALKING
            item.pace = timeSpentTxt.text.toString().toFloat() / distanceTxt.text.toString().toFloat()

            exerciseController.updateEntry(item)
            entryFormDialog.dismiss()
            exerciseAdapter.notifyDataSetChanged()
        }
    }

    fun setContext(context: Context){
        this.context = context
    }

    private fun drawModal(){
        entryFormDialog = Dialog(context).apply {
            setContentView(R.layout.create_exercise_form)
            setCancelable(false)
            window?.setLayout(1000, 1250)
        }

        exerciseTypeRadioGroup = entryFormDialog.findViewById(R.id.exerciseTypeRadioGroup)
        distanceTxt = entryFormDialog.findViewById(R.id.distanceTxt)
        timeSpentTxt = entryFormDialog.findViewById(R.id.timeSpentTxt)
        submitBtn = entryFormDialog.findViewById(R.id.submitBtn)
    }
}