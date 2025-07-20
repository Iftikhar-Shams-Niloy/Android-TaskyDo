package com.example.taskydo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
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
import com.example.taskydo.util.TaskydoApplication.Companion.taskDao
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
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

        CoroutineScope(Dispatchers.Main).launch {
            startTitleTicker()
        }

    }

    suspend fun startTitleTicker(){
        var count = 1
        while (true){
            delay(1000)
            binding.toolbar.title = "Seconds: $count"
            count++
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

            buttonSave.setOnClickListener {
                viewModel.createTask(
                    editTextTaskTitle.text.toString(),
                    editTextTaskDescription.text.toString()
                    )
                dialog.dismiss()
                tasksFragment.fetchAllTasks()
            }

            dialog.show()
        }
    }

    fun isInputValid(input: String?): Boolean{
        return !input?.trim().isNullOrEmpty() && input!!.length > 1
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 1
        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }

    }

}