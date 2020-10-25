package com.mobiledev.fitnesstracker

import android.os.Bundle
import android.util.Half.toFloat
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Float.parseFloat


class ExerciseItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_item_details)

        val intent = this.intent

        val item = intent.getParcelableExtra("item") as? ExerciseItem

        if (item != null) {
            var distanceAmountTxt = findViewById<TextView>(R.id.distanceAmountTxt)
            var isSingleKm = parseFloat(item.distance.toString()) > 1.00
            distanceAmountTxt.text = if (isSingleKm)
                item.distance.toString() + " km" else
                item.distance.toString() + " kms"

            var timeSpentAmountTxt = findViewById<TextView>(R.id.timeSpentAmountTxt)
            var isSingleHour = parseFloat(item.timeSpent.toString()) > 1.00
            timeSpentAmountTxt.text = if (isSingleHour)
                item.timeSpent.toString() + " hour" else
                item.timeSpent.toString() + " hours"

            var fitnessTxt = findViewById<TextView>(R.id.fitnessTxt)
            fitnessTxt.text = item.FITNESS_TYPE.toString().capitalize()

            var paceTxt = findViewById<TextView>(R.id.paceTxt)
            paceTxt.text = item.pace.toString() + " km/h"

            var exerciseIdTxt = findViewById<TextView>(R.id.exerciseIdTxt)
            exerciseIdTxt.text = item.id.toString()
        }
    }
}