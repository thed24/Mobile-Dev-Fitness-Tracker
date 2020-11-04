package com.mobiledev.fitnesstracker.controllers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobiledev.fitnesstracker.R
import com.mobiledev.fitnesstracker.activities.ExerciseItemActivity
import com.mobiledev.fitnesstracker.activities.Modal
import com.mobiledev.fitnesstracker.domain.ExerciseItem
import com.mobiledev.fitnesstracker.persistence.ExerciseType
import javax.inject.Inject

class ExerciseAdapter @Inject constructor(
    private val exerciseController: BaseController<ExerciseItem>,
    private val modal: Modal
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise_item, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount() = exerciseController.getAllEntries().count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = exerciseController.getAllEntries()[position]
        holder.bind(item, this, exerciseController, modal)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val rowTextView: TextView = v.findViewById(R.id.exercise_name)
        private val editExerciseBtn: Button = v.findViewById(R.id.editExerciseBtn)
        private val deleteExerciseBtn: Button = v.findViewById(R.id.deleteExerciseBtn)
        private val view: View = v

        fun bind(
            item: ExerciseItem,
            adapter: ExerciseAdapter,
            controller: BaseController<ExerciseItem>,
            modal: Modal
        ) {
            var exerciseType = if (item.exerciseType == ExerciseType.RUNNING)
                "Run: " else
                "Walk: "
            rowTextView.text = exerciseType + item.timeStamp

            editExerciseBtn.setOnClickListener {
                modal.setContext(view.context)
                modal.updateEntryForm(item, adapter)
            }

            deleteExerciseBtn.setOnClickListener {
                controller.removeEntry(item)
                adapter.notifyDataSetChanged()
            }

            view.setOnClickListener {
                val intent = Intent(view.context, ExerciseItemActivity::class.java)
                intent.putExtra("item", item)
                view.context.startActivity(intent)
            }
        }
    }
}
