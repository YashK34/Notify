package com.example.note.roomRelatedFiles

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Note::class],version = 1)
abstract class NoteDatabase:RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null


        fun getDatabase(context: Context, scope: CoroutineScope): NoteDatabase {
//            val tempInstance= INSTANCE
//            if(tempInstance!=null){
//                return tempInstance
//            }

            //only one thread can access this
            //in multi thread environment ,it can make two instance that
            //can access different threads which leads to app crash
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(NoteDatabaseCallBack(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class NoteDatabaseCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.noteDao())
                }
            }
        }
        suspend fun populateDatabase(noteDao: NoteDao) {
            noteDao.deleteAllNotes()
            noteDao.insert(Note(title = "Title1", description = "Description1", priority = 1))
            noteDao.insert(Note(title = "Title2", description = "Description2", priority = 2))
            noteDao.insert(Note(title = "Title3", description = "Description3", priority = 3))
        }
    }
}

