package com.example.note.roomRelatedFiles

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.note.roomRelatedFiles.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Insert
    suspend fun insertAll(noteList: ArrayList<Note>)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note_Table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_Table ORDER BY priority DESC")
    fun getAllNotes():LiveData<List<Note>>
}