package com.example.note.roomRelatedFiles

import com.example.note.roomRelatedFiles.Note
import com.example.note.roomRelatedFiles.NoteDao

class NoteRepository(private val noteDao: NoteDao){

    var allNotes=noteDao.getAllNotes()

    suspend fun insertNote(note: Note){
         noteDao.insert(note)
    }
    suspend fun update(note: Note){
        noteDao.updateNote(note)
    }
    suspend fun delete(note: Note){
            noteDao.deleteNote(note)
    }
    fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }
//    fun get():LiveData<List<Note>>{
//        return allNotes
//    }
}