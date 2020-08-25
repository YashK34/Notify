package com.example.note

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        number_picker_priority.maxValue=10
        number_picker_priority.minValue=1
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.elevation=4.4f
        supportActionBar?.title="Add Task"
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_task_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    companion object{
        const val EXTRA_TITLE="title"
        const val EXTRA_DESCRIPTION="description"
        const val EXTRA_PRIORITY="priority"
    }
    private fun saveNotes() {
        val title: String = edit_text_title.text.toString()
        val description: String = edit_text_description.text.toString()
        val priority: Int = number_picker_priority.value
        val data = Intent()
        if (title.trim { it <= ' ' }.isEmpty() || description.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            return
        }
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)
        data.putExtra(EXTRA_PRIORITY, priority)
        setResult(Activity.RESULT_OK, data)
        finish()

    }
}