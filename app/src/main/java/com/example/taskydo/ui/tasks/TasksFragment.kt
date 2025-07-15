package com.example.taskydo.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskydo.data.Task
import com.example.taskydo.data.TaskDao
import com.example.taskydo.data.TaskyDatabase
import com.example.taskydo.databinding.FragmentTasksBinding
import kotlin.concurrent.thread

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private val taskDao: TaskDao by lazy { TaskyDatabase.createDatabase(requireContext()).taskDao() }

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
        thread {
            val myTasks = taskDao.getAllTasks()
            requireActivity().runOnUiThread {
                binding.recyclerViewTasks.adapter = TasksAdapter(tasks = myTasks)
            }
        }
    }
}