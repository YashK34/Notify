package com.example.note.roomRelatedFiles


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_Table")
data class Note (

    @PrimaryKey(autoGenerate = true)
    val id:Long=0L,

    val title:String,
    val description:String,
    val priority:Int
)