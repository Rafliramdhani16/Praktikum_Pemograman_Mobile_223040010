package com.example.pertemuan4.repository

import androidx.lifecycle.LiveData
import com.example.pertemuan4.dao.NoteDao
import com.example.pertemuan4.models.Note

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()
    
    suspend fun insert(note: Note) {
        noteDao.insertNote(note)
    }
    
    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
    }
}
