package com.example.taskydo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.glance.visibility
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskydo.data.Task
import com.example.taskydo.data.TaskyDatabase
import com.example.taskydo.databinding.ActivityMainBinding
import com.example.taskydo.databinding.DialogAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: TaskyDatabase
    private val myTaskDao by lazy { database.taskDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.pager) {tab, position->
            tab.text = "Tasks"
        }.attach()

        binding.fab.setOnClickListener { showAddTaskDialog() }

        database = TaskyDatabase.createDatabase(this)


    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.imageButtonAddDescription.setOnClickListener {
            val descriptionEditText = dialogBinding.editTextTaskDescription
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

        dialogBinding.buttonSave.setOnClickListener {
            val task = Task(
                title = dialogBinding.editTextTaskTitle.text.toString(),
                description = dialogBinding.editTextTaskDescription.text.toString()
            )
            thread { myTaskDao.createTask(task) }
            dialog.dismiss()
        }

        dialog.show()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 1
        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }

    }

}