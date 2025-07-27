package com.example.taskydo.ui.tasks

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskydo.data.model.Task
import com.example.taskydo.databinding.ItemTaskBinding

class TasksAdapter( private val listener: TasksFragment) :  RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

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
        this.tasks = tasks.sortedBy { it.isCompleted }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {
                root.setOnLongClickListener {
                    listener.onTaskDeleted(task)
                    true
                }
                checkBox.isChecked = task.isCompleted
                if (task.isCompleted){
                    textViewTitle.paintFlags = textViewTitle.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                    textViewDescription.paintFlags = textViewDescription.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewTitle.paintFlags = textViewTitle.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    textViewDescription.paintFlags = textViewDescription.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                starCheckbox.isChecked = task.isStarred
                textViewTitle.text = task.title
                if (task.description.isNullOrEmpty()){
                    textViewDescription.visibility = View.GONE
                } else {
                    textViewDescription.text = task.description
                    textViewDescription.visibility = View.VISIBLE
                }
                textViewDescription.text = task.description
                checkBox.setOnClickListener {
                    val updatedTask = task.copy(isCompleted = checkBox.isChecked)
                    listener.onTaskUpdated(updatedTask)
                }
                starCheckbox.setOnClickListener {
                    val updatedTask = task.copy(isStarred = starCheckbox.isChecked)
                    listener.onTaskUpdated(updatedTask)
                }

            }

        }
    }

    interface TaskItemClickListener {

        fun onTaskUpdated(task: Task)
        
        fun onTaskDeleted(task: Task)
    }

}