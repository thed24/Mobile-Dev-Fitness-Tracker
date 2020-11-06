package com.mobiledev.fitnesstracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobiledev.fitnesstracker.R
import com.mobiledev.fitnesstracker.domain.ExerciseItem
import com.mobiledev.fitnesstracker.domain.NameGenerator
import kotlinx.android.synthetic.main.exercise_item_details.*
import java.lang.Float.parseFloat
import java.util.*


class ExerciseItemActivity : AppCompatActivity() {

    private var nameGenerator = NameGenerator()
    private val oneUnit = 1.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_item_details)

        val intent = this.intent

        val item = intent.getParcelableExtra("item") as? ExerciseItem

        if (item != null) {
            val isSingleKm = parseFloat(item.distance.toString()) > oneUnit
            distanceAmountTxt.text = if (isSingleKm)
                item.distance.toString() + " km" else
                item.distance.toString() + " kms"

            val isSingleHour = parseFloat(item.timeSpent.toString()) > oneUnit
            timeSpentAmountTxt.text = if (isSingleHour)
                item.timeSpent.toString() + " minute" else
                item.timeSpent.toString() + " minutes"

            fitnessTxt.text = item.exerciseType.toString().capitalize(Locale.getDefault())
            paceTxt.text = item.pace.toString() + " minutes per km"
            exerciseDescriptionTxt.text = nameGenerator.createName(item)
        }
    }
}
