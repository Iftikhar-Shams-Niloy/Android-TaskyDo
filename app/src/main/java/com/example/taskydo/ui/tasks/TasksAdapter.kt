package com.example.taskydo.ui.tasks

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskydo.data.Task
import com.example.taskydo.databinding.ItemTaskBinding
import com.google.android.material.checkbox.MaterialCheckBox

class TasksAdapter( private val listener: TaskUpdatedListener) :  RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()

    override fun getItemCount(): Int{
        return tasks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemTaskBinding.inflate( inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.checkBox.isChecked = task.isCompleted
            if (task.isCompleted){
                binding.textViewTitle.paintFlags = binding.textViewTitle.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                binding.textViewDescription.paintFlags = binding.textViewDescription.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.textViewTitle.paintFlags = binding.textViewTitle.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.textViewDescription.paintFlags = binding.textViewDescription.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            binding.starCheckbox.isChecked = task.isStarred
            binding.textViewTitle.text = task.title
            binding.textViewDescription.text = task.description
            binding.checkBox.setOnClickListener {
                val updatedTask = task.copy(isCompleted = binding.checkBox.isChecked)
                listener.onTaskUpdated(updatedTask)
            }
            binding.starCheckbox.setOnClickListener {
                val updatedTask = task.copy(isStarred = binding.starCheckbox.isChecked)
                listener.onTaskUpdated(updatedTask)
            }

        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }

}