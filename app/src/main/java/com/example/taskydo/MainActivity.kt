package com.example.taskydo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskydo.data.TaskyDatabase
import com.example.taskydo.databinding.ActivityMainBinding
import com.example.taskydo.databinding.DialogAddTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.pager) {tab, position->
            tab.text = "Tasks"
        }.attach()

        binding.fab.setOnClickListener {
            val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
            MaterialAlertDialogBuilder(this)
                .setTitle("Add New Task")
                .setView(dialogBinding.root)
                .setPositiveButton("Save"){_, _ ->
                    Toast.makeText(this, "Your task is: ${dialogBinding.editText.text}", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        TaskyDatabase.createDatabase(this)

    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount() = 1

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }

    }

}