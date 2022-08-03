package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewmodel:DetailTaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val titletodo = findViewById<TextView>(R.id.detail_ed_title)
        val descriptodo = findViewById<TextView>(R.id.detail_ed_description)
        val datetodo = findViewById<TextView>(R.id.detail_ed_due_date)
        val buttondelete = findViewById<Button>(R.id.btn_delete_task)

        val pref = ViewModelFactory.getInstance(this)
        viewmodel = ViewModelProvider(this, pref)[DetailTaskViewModel::class.java]
        viewmodel.setTaskId(intent.getIntExtra(TASK_ID,0))

        viewmodel.task.observe(this) {
            titletodo.text = it?.title
            descriptodo.text = it?.description
            datetodo.text = Editable.Factory
                .getInstance()
                .newEditable(DateConverter.convertMillisToString(it.dueDateMillis))
        }
        buttondelete.setOnClickListener {
            viewmodel.deleteTask()
            Toast.makeText(this,"Delete Clear",Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }
    }
}