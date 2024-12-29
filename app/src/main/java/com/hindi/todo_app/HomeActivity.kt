package com.hindi.todo_app

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.hindi.todo_app.adapter.NoteRvAdapter
import com.hindi.todo_app.adapter.NoteViewModel
import com.hindi.todo_app.databinding.ActivityHomeBinding
import com.hindi.todo_app.roomdb.Note

class HomeActivity : AppCompatActivity(),
    NoteRvAdapter.NoteClickInterface,
    NoteRvAdapter.NoteClickDeleteInterface {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var noteRvAdapter: NoteRvAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // RecyclerView setup
        noteRvAdapter = NoteRvAdapter(this, mutableListOf(), this, this)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = noteRvAdapter
        }

        // Observe ViewModel
        viewModel.allNotes.observe(this) { notes ->
            noteRvAdapter.updateList(notes)
            if (notes.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyTextView.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyTextView.visibility = View.GONE
            }
        }

        // Add Task
        binding.AddTaskBtn.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("noteType", "Add")
            startActivity(intent)
        }

        // Logout Click Listener
        binding.imageView3.setOnClickListener {
            showLogoutDialog()
        }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this, AddActivity::class.java).apply {
            putExtra("noteType", "Edit")
            putExtra("noteTitle", note.notesTitle)
            putExtra("noteDescription", note.description)
            putExtra("noteID", note.id)
        }
        startActivity(intent)
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
    }

    private fun showLogoutDialog() {
        val email = auth.currentUser?.email ?: "Unknown"
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("\n$email\n\nDo you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                auth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
