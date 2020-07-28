package com.example.note.roomRelatedFiles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _text = MutableLiveData<String>().apply {
        value = "Add Tasks"
    }
    val text: LiveData<String> = _text
    // view Model for database
    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>
    init {
        val noteDao=NoteDatabase.getDatabase(application,viewModelScope).noteDao()
        repository= NoteRepository(noteDao)
        allNotes=repository.allNotes
    }
    fun insert(note: Note)=viewModelScope.launch (Dispatchers.IO){
        repository.insertNote(note)
    }
    fun update(note: Note)=viewModelScope.launch (Dispatchers.IO){
        repository.update(note)
    }
    fun delete(note: Note)=viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }
    fun deleteAllNotes()=viewModelScope.launch(Dispatchers.IO){
        repository.deleteAllNotes()
    }
}