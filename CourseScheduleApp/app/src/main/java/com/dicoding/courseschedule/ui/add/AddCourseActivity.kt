package com.dicoding.courseschedule.ui.add


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding

import com.dicoding.courseschedule.util.TimePickerFragment


class AddCourseActivity : AppCompatActivity() , TimePickerFragment.DialogTimeListener{
    private lateinit var binding: ActivityAddCourseBinding
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var timeStart: String
    private lateinit var timeEnd: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = AddViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this,pref).get(AddCourseViewModel::class.java)
        showTime()
        viewModel.saved
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val edCourseName = binding.tvCourseAdd
        val spinnerCourse = binding.tvDayAdd
        val edLecturer = binding.tvLecturerAdd
        val edNote = binding.tvNoteAdd

        return if (item.itemId == R.id.action_insert){
            val courseName = edCourseName.text.toString()
            val dayCourse = spinnerCourse.selectedItemPosition
            val lecturer = edLecturer.text.toString()
            val note = edNote.text.toString()

            viewModel.insertCourse(courseName, dayCourse, timeStart, timeEnd, lecturer, note)
            super.onBackPressed()

            Toast.makeText(this, "Course Added Successfully", Toast.LENGTH_SHORT).show()
            true
        } else{
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val time = "${hour}:${minute}"
        val startTime = findViewById<TextView>(R.id.tv_Start_time)
        val endTime = findViewById<TextView>(R.id.tv_end_time)

        if (tag == STARTTIME){
            startTime.text = time
            timeStart = time
        } else{
            endTime.text = time
            timeEnd = time
        }
    }

    private fun showTime(){
        val timePickerFragment = TimePickerFragment()
        val ibStartTime = binding.imageStartAdd
        val ibEndTime = binding.imageEndAdd

        ibStartTime.setOnClickListener {
            timePickerFragment.show(supportFragmentManager, STARTTIME)
        }

        ibEndTime.setOnClickListener {
            timePickerFragment.show(supportFragmentManager, ENDTIME)
        }
    }
    companion object{
        const val STARTTIME = "starttime"
        const val ENDTIME = "endtime"
    }
}