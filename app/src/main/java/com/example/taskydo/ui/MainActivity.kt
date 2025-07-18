package com.example.taskydo.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskydo.data.Task
import com.example.taskydo.data.TaskyDatabase
import com.example.taskydo.databinding.ActivityMainBinding
import com.example.taskydo.databinding.DialogAddTaskBinding
import com.example.taskydo.ui.tasks.TasksFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val database: TaskyDatabase by lazy { TaskyDatabase.getDatabase(this) }
    private val myTaskDao by lazy { database.taskDao() }
    private val tasksFragment: TasksFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            pager.adapter = PagerAdapter(this@MainActivity)
            TabLayoutMediator(tabs, pager) { tab, _ ->
                tab.text = "Tasks"
            }.attach()
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
                buttonSave.isEnabled = !inputTitle?.trim().isNullOrEmpty()
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

            buttonSave.setOnClickListener {
                val task = Task(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDescription.text.toString()
                )
                thread { myTaskDao.createTask(task) }
                dialog.dismiss()
                tasksFragment.fetchAllTasks()
            }

            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 1
        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }

    }

}