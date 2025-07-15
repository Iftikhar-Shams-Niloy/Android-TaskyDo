package com.example.taskydo.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskydo.data.Task
import com.example.taskydo.databinding.ItemTaskBinding

class TasksAdapter(private val tasks: List<Task>) :  RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

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

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task){
            binding.textViewTitle.text = task.title
            binding.textViewDescription.text = task.description
        }
    }

}