package com.hindi.todo_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hindi.todo_app.R
import com.hindi.todo_app.roomdb.Note

class NoteRvAdapter(
    private val context: Context,
    private var noteList: MutableList<Note>, // A mutable list of Note objects
    private val noteClickInterface: NoteClickInterface,
    private val noteClickDeleteInterface: NoteClickDeleteInterface
) : RecyclerView.Adapter<NoteRvAdapter.ViewHolder>() {

    // ViewHolder class for RecyclerView
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.noteTitle)
        val noteDescription: TextView = itemView.findViewById(R.id.noteDescription)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteBtn)
        val time: TextView = itemView.findViewById(R.id.timeStamp)
    }

    // Inflate the item layout and create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.noteTitle.text = note.notesTitle
        holder.noteDescription.text = note.description
        holder.time.text = "${note.timeStamp}"

        // Set click listener for the note item
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(note)
        }

        // Set click listener for the delete icon
        holder.deleteIcon.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(note)
        }
    }

    // Return the size of the list
    override fun getItemCount(): Int {
        return noteList.size
    }

    // Update list and notify adapter
    fun updateList(newList: List<Note>) {
        noteList.clear()
        noteList.addAll(newList)
        notifyDataSetChanged()
    }

    // Interface for click actions
    interface NoteClickInterface {
        fun onNoteClick(note: Note)
    }

    interface NoteClickDeleteInterface {
        fun onDeleteIconClick(note: Note)
    }
}
