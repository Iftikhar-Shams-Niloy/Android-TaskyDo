package com.example.taskydo.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskydo.data.model.TaskList
import com.example.taskydo.databinding.ActivityMainBinding
import com.example.taskydo.databinding.DialogAddTaskBinding
import com.example.taskydo.ui.tasks.PriorityTasksFragment
import com.example.taskydo.ui.tasks.TasksFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val tasksFragment: TasksFragment = TasksFragment()
    private var currentTaskList: List<TaskList> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleScope.launch {
                viewModel.getTaskLists().collectLatest { taskLists ->

                    pager.adapter = PagerAdapter(this@MainActivity, taskLists.size+2)
                    pager.currentItem = 1
                    TabLayoutMediator(tabs, pager) { tab, position ->
                        when (position){
                            0 -> tab.text = "Priority"
                            taskLists.size+1 -> tab.customView = Button(this@MainActivity).apply {
                                this.text = "Add New"
                            }
                            else -> tab.text = taskLists[position-1].name
                        }
                    }.attach()
                }
            }
            fab.setOnClickListener { showAddTaskDialog() }
            setContentView(root)
        }
    }

    private fun showAddTaskDialog() {
        DialogAddTaskBinding.inflate(layoutInflater).apply {
            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)

            buttonSave.isEnabled = false
            editTextTaskTitle.addTextChangedListener { inputTitle ->
                buttonSave.isEnabled = isInputValid(inputTitle?.toString())
            }

            imageButtonAddDescription.setOnClickListener {
                val descriptionEditText = editTextTaskDescription
                val animationDuration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                if (descriptionEditText.visibility == View.VISIBLE) {
                    descriptionEditText.animate()
                        .translationY(10f)
                        .alpha(0f)
                        .setDuration(animationDuration)
                        .withEndAction { descriptionEditText.visibility = View.GONE }
                        .start()
                } else {
                    descriptionEditText.alpha = 0f
                    descriptionEditText.visibility = View.VISIBLE
                    descriptionEditText.animate()
                        .translationY(-10f)
                        .alpha(1f)
                        .setDuration(animationDuration)
                        .start()
                }
            }

            binding.pager.currentItem

            buttonSave.setOnClickListener {
                val selectedTaskListId = currentTaskList[binding.pager.currentItem - 1].id
                viewModel.createTask(
                    title =editTextTaskTitle.text.toString(),
                    description = editTextTaskDescription.text.toString(),
                    listId = selectedTaskListId
                    )
                dialog.dismiss()
                tasksFragment.fetchAllTasks()
            }
            dialog.show()
        }
    }

    private fun isInputValid(input: String?): Boolean{
        return !input?.trim().isNullOrEmpty() && input!!.length > 1
    }

    inner class PagerAdapter(activity: FragmentActivity, private val numOfPages: Int) : FragmentStateAdapter(activity) {
        override fun getItemCount() = numOfPages
        override fun createFragment(position: Int): Fragment {
            return when (position){
                0 -> PriorityTasksFragment()
                else -> TasksFragment()
            }
        }
    }
}