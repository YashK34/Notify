package com.example.note

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note.recyclerViewRelatedFiles.NoteAdapter
import com.example.note.roomRelatedFiles.MainViewModel
import com.example.note.roomRelatedFiles.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
     private lateinit var mainViewModel: MainViewModel
    companion object{
        const val REQUEST_CODE =1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

       fab.setOnClickListener {
           val intent = Intent(this@MainActivity,AddTaskActivity::class.java)
           startActivityForResult(intent, REQUEST_CODE)
        }
        mainViewModel=ViewModelProvider(this).get(MainViewModel::class.java)
//        mainViewModel.text.observe(this, Observer {
//            //text_home.text=it
//        })
        val adapter1= NoteAdapter()
        with(recycler_view) {
            layoutManager = LinearLayoutManager(context)
            hasFixedSize()
            adapter = adapter1
            //addItemDecoration(DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL))
        }

        mainViewModel.allNotes.observe(this, Observer {
            it?.let {
                adapter1.setNotes(it)
            }
        })
        val itemTouchHelperCallback=object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val restore=adapter1.getNoteAt(viewHolder.adapterPosition)
               mainViewModel.delete(restore)
                val snak=Snackbar.make(root,"The Task is Complete",Snackbar.LENGTH_LONG)
                    snak.setAction("Undo"){
                        mainViewModel.insert(restore)
                    }
                        .setActionTextColor(Color.RED)
                snak.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== REQUEST_CODE&&resultCode==RESULT_OK){
                val  title = data?.getStringExtra(AddTaskActivity.EXTRA_TITLE)
                val description = data?.getStringExtra(AddTaskActivity.EXTRA_DESCRIPTION)
                val  priority = data?.getIntExtra(AddTaskActivity.EXTRA_PRIORITY, 1)
            val note= Note(title = title!!,description = description!!,priority = priority!!)
            mainViewModel.insert(note)
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings ->
                true
            R.id.delete_all_notes->{
                mainViewModel.deleteAllNotes()
                Toast.makeText(this, "All Tasks Deleted", Toast.LENGTH_SHORT).show()
                true
            }

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