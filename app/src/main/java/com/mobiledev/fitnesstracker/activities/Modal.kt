package com.mobiledev.fitnesstracker.activities

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.mobiledev.fitnesstracker.R
import com.mobiledev.fitnesstracker.controllers.BaseController
import com.mobiledev.fitnesstracker.controllers.ExerciseAdapter
import com.mobiledev.fitnesstracker.controllers.ExerciseController
import com.mobiledev.fitnesstracker.domain.ExerciseItem
import com.mobiledev.fitnesstracker.persistence.ExerciseType
import javax.inject.Inject

class Modal @Inject constructor(
    private var context: Context,
    private val exerciseController: BaseController<ExerciseItem>
) {
    private lateinit var exerciseTypeRadioGroup: RadioGroup
    private lateinit var timeSpentTxt: TextView
    private lateinit var distanceTxt: TextView
    private lateinit var submitBtn: Button
    private lateinit var entryFormDialog: Dialog
    private lateinit var titleTxt: TextView

    private val runningId = R.id.runningRadioBtn
    private val defaultId = 0

    fun createEntryForm(exerciseAdapter: ExerciseAdapter) {
        drawModal()
        entryFormDialog.show()

        submitBtn.setOnClickListener {
            if (isInvalidTextField()) {
                Toast.makeText(
                    context,
                    "Error, distance or time spent is empty!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val newItem = ExerciseItem(
                    id = defaultId,
                    distance = distanceTxt.text.toString().toFloat(),
                    timeSpent = timeSpentTxt.text.toString().toFloat(),
                    exerciseType = if (exerciseTypeRadioGroup.checkedRadioButtonId == runningId)
                        ExerciseType.RUNNING else
                        ExerciseType.WALKING,
                    pace = timeSpentTxt.text.toString().toFloat() / distanceTxt.text.toString()
                        .toFloat()
                )

                if (isInvalidEntry(newItem)) {
                    Toast.makeText(
                        context,
                        "Error, invalid distance or time spent!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    exerciseController.addEntry(newItem)
                    entryFormDialog.dismiss()
                    exerciseAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun updateEntryForm(item: ExerciseItem, exerciseAdapter: ExerciseAdapter) {
        drawModal()
        titleTxt.text = "Update Entry"

        var buttonToCheck = if (item.exerciseType == ExerciseType.RUNNING)
            R.id.runningRadioBtn else
            R.id.walkingRadioBtn

        exerciseTypeRadioGroup.check(buttonToCheck)
        distanceTxt.text = item.distance.toString()
        timeSpentTxt.text = item.timeSpent.toString()

        entryFormDialog.show()

        submitBtn.setOnClickListener {
            if (isInvalidTextField()) {
                Toast.makeText(
                    context,
                    "Error, distance or time spent is empty!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                item.distance = distanceTxt.text.toString().toFloat()
                item.timeSpent = timeSpentTxt.text.toString().toFloat()
                item.exerciseType = if (exerciseTypeRadioGroup.checkedRadioButtonId == runningId)
                    ExerciseType.RUNNING else
                    ExerciseType.WALKING
                item.pace =
                    timeSpentTxt.text.toString().toFloat() / distanceTxt.text.toString().toFloat()

                if (isInvalidEntry(item)) {
                    Toast.makeText(
                        context,
                        "Error, invalid distance or time spent!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    exerciseController.updateEntry(item)
                    entryFormDialog.dismiss()
                    exerciseAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun setContext(context: Context) {
        this.context = context
    }

    private fun drawModal() {
        entryFormDialog = Dialog(context).apply {
            setContentView(R.layout.create_exercise_form)
            setCancelable(false)
            window?.setLayout(1000, 1250)
        }

        exerciseTypeRadioGroup = entryFormDialog.findViewById(R.id.exerciseTypeRadioGroup)
        distanceTxt = entryFormDialog.findViewById(R.id.distanceTxt)
        timeSpentTxt = entryFormDialog.findViewById(R.id.timeSpentTxt)
        submitBtn = entryFormDialog.findViewById(R.id.submitBtn)
        titleTxt = entryFormDialog.findViewById(R.id.titleTxt)
    }

    private fun isInvalidTextField(): Boolean {
        return distanceTxt.text.isEmpty() || timeSpentTxt.text.isEmpty()
    }

    private fun isInvalidEntry(item: ExerciseItem): Boolean {
        return item.timeSpent.toDouble() == 0.0 || item.distance.toDouble() == 0.0
    }
}
