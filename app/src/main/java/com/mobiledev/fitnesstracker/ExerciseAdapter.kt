package com.mobiledev.fitnesstracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView


class ExerciseAdapter(var context: Context, var arrayItems: List<ExerciseItem>): BaseAdapter() {

    override fun getCount(): Int {
        return arrayItems.size;
    }

    override fun getItem(position: Int): Any {
        return arrayItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentValue = arrayItems[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rowView = inflater.inflate(R.layout.exercise_item, parent, false)

        val rowTextView = rowView.findViewById<TextView>(R.id.exercise_name)
        rowTextView.text = "Exercise Entry " + currentValue.id.toString()

        val rowEditBtn = rowView.findViewById<Button>(R.id.editExerciseBtn)
        rowEditBtn.setOnClickListener { }

        val rowDeleteBtn = rowView.findViewById<Button>(R.id.deleteExerciseBtn)
        rowDeleteBtn.setOnClickListener { }

        return rowView
    }
}