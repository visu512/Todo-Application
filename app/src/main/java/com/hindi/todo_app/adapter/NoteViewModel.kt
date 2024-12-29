package com.hindi.todo_app.adapter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hindi.todo_app.roomdb.Note
import com.hindi.todo_app.roomdb.NoteDatabase
import com.hindi.todo_app.roomdb.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// viewModel for show data on activity

class NoteViewModel (application: Application):AndroidViewModel(application) {

    val allNotes : LiveData<List<Note>>
    val repository : NoteRepository

    init {
       val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNote
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}