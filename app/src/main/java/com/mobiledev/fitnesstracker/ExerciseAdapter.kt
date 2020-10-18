package com.mobiledev.fitnesstracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val data: List<ExerciseItem>,
    private val controller: ExerciseController
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise_item, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount() = data.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, this, controller)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val rowTextView: TextView = v.findViewById(R.id.exercise_name)
        private val editExerciseBtn: Button = v.findViewById(R.id.editExerciseBtn)
        private val deleteExerciseBtn: Button = v.findViewById(R.id.deleteExerciseBtn)

        fun bind(item: ExerciseItem, context: ExerciseAdapter, controller: ExerciseController) {
            rowTextView.text = "Exercise Entry " + item.id.toString()
            editExerciseBtn.setOnClickListener {
                controller.callUpdateEntryForm(item)
                context.notifyDataSetChanged()
            }
            deleteExerciseBtn.setOnClickListener {
                controller.removeEntry(item)
                context.notifyDataSetChanged()
            }
        }
    }
}