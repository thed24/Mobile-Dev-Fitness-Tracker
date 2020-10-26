package com.mobiledev.fitnesstracker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val data: List<ExerciseItem>,
    private val exerciseController: ExerciseController,
    private val modalController: ModalController
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.exercise_item, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount() = data.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, this, exerciseController, modalController)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val rowTextView: TextView = v.findViewById(R.id.exercise_name)
        private val editExerciseBtn: Button = v.findViewById(R.id.editExerciseBtn)
        private val deleteExerciseBtn: Button = v.findViewById(R.id.deleteExerciseBtn)
        private val view: View = v

        fun bind(
            item: ExerciseItem,
            adapter: ExerciseAdapter,
            controller: ExerciseController,
            modalController: ModalController
        ) {
            rowTextView.text = "Exercise Entry " + item.id.toString()

            editExerciseBtn.setOnClickListener {
                modalController.callUpdateEntryForm(item, adapter)
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