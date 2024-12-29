package com.hindi.todo_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hindi.todo_app.adapter.NoteViewModel
import com.hindi.todo_app.databinding.ActivityAddBinding
import com.hindi.todo_app.roomdb.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var viewModel: NoteViewModel
    private var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType == "Edit") {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)
            binding.editTextTitle.setText(noteTitle)
            binding.editTextDesc.setText(noteDesc)
            binding.SaveBtn.text = "Update Task"
        } else {
            binding.SaveBtn.text = "Save Task"
        }

        binding.SaveBtn.setOnClickListener {
            val noteTitle = binding.editTextTitle.text.toString()
            val noteDesc = binding.editTextDesc.text.toString()

            if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()) {
                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm a", Locale.getDefault())
                val currentDate = sdf.format(Date())

                if (noteType == "Edit") {
                    val updatedNote = Note(noteTitle, noteDesc, currentDate)
                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Task Updated.", Toast.LENGTH_SHORT).show()
                } else {
                    val newNote = Note(noteTitle, noteDesc, currentDate)
                    viewModel.addNote(newNote)
                    Toast.makeText(this, "Task Added.", Toast.LENGTH_SHORT).show()
                }

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
