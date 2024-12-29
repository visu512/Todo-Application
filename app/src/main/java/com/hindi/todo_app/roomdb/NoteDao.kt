package com.hindi.todo_app.roomdb

// Dao (data access object)

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    // Insert notes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    // Update notes
    @Update
    suspend fun update(note: Note)

    // Delete notes
    @Delete
    suspend fun delete(note: Note)

    @Query("Select * from notesTable order by id ASC")
//    @Query("Select * from notesTable")
    fun getAllNotes(): LiveData<List<Note>>
}