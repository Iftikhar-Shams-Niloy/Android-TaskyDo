package com.example.taskydo.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskydo.data.model.Task
import com.example.taskydo.databinding.FragmentTasksBinding
import kotlinx.coroutines.launch


class TasksFragment : Fragment(), TasksAdapter.TaskItemClickListener {

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var binding: FragmentTasksBinding
    private val adapter = TasksAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewTasks.adapter = adapter
        fetchAllTasks()
    }

    fun fetchAllTasks(){
        lifecycleScope.launch {
            val tasks: List<Task> = viewModel.fetchTasks()
            Log.d("NILOY!", "Tasks fetched: $tasks")
            adapter.setTasks(tasks)
        }
    }

    override fun onTaskUpdated(task: Task) {
        viewModel.updateTask(task)
        fetchAllTasks()
    }

    override fun onTaskDeleted(task: Task) {
        viewModel.deleteTask(task)
        fetchAllTasks()
    }
}