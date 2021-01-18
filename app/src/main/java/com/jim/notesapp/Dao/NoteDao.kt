package com.jim.notesapp.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jim.notesapp.model.Note

@Dao
interface NoteDao {

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Insert
    fun insert(note: Note)

    @Query("DELETE FROM notes_table ")
    fun deleteAll()

    @Query("SELECT * FROM notes_table")
    fun getAllNotes():LiveData<List<Note>>
}