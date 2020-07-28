package com.example.note

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note.recyclerViewRelatedFiles.NoteAdapter
import com.example.note.roomRelatedFiles.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
     private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

       fab.setOnClickListener {
            startActivity(Intent(this,AddTaskActivity::class.java))
        }
        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.text.observe(this, Observer {
            text_home.text=it
        })
        val adapter1= NoteAdapter()
        with(recycler_view) {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            adapter = adapter1
        }
        mainViewModel.allNotes.observe(this, Observer {
            it?.let {
                adapter1.setNotes(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    view.findViewById<Button>(R.id.button_first).setOnClickListener {
//        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//    }
//}