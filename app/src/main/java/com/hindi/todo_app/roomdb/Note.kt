package com.hindi.todo_app.roomdb

// Model class
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class Note(
    @ColumnInfo(name = "title") val notesTitle: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "timestamp") val timeStamp: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0;

}