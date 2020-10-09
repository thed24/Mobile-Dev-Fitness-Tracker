package com.mobiledev.fitnesstracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
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

        val newView = inflater.inflate(R.layout.exercise_item, parent, false)

        val newViewText = newView.findViewById<TextView>(R.id.exercise_name)
        newViewText.text = "Exercise Entry " + currentValue.id.toString()

        val newViewEditButton = newView.findViewById<TextView>(R.id.editExerciseBtn)
        newViewEditButton.setOnClickListener {
        }

        val newViewDeleteButton = newView.findViewById<TextView>(R.id.deleteExerciseBtn)
        newViewDeleteButton.setOnClickListener {
        }

        return newView
    }
}