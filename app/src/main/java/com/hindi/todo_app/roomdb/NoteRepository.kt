package com.hindi.todo_app.roomdb

import androidx.lifecycle.LiveData

// Note Repository
class NoteRepository(private val noteDao: NoteDao) {

    val allNote: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}