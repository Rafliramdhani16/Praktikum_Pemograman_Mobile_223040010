package com.example.pertemuan4.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteViewModel : ViewModel() {
    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> get() = _notes

    private val _noteTitle = MutableStateFlow("")
    val noteTitle: StateFlow<String> = _noteTitle.asStateFlow()

    private val _noteContent = MutableStateFlow("")
    val noteContent: StateFlow<String> = _noteContent.asStateFlow()

    fun updateNoteTitle(title: String) {
        _noteTitle.value = title
    }

    fun updateNoteContent(content: String) {
        _noteContent.value = content
    }

    fun addNote() {
        if (_noteTitle.value.isNotBlank() && _noteContent.value.isNotBlank()) {
            val newNote = Note(
                id = System.currentTimeMillis().toString(),
                title = _noteTitle.value,
                content = _noteContent.value
            )
            _notes.add(newNote)
            clearInputs()
        }
    }

    fun deleteNote(id: String) {
        _notes.removeIf { it.id == id }
    }

    private fun clearInputs() {
        _noteTitle.value = ""
        _noteContent.value = ""
    }
}

data class Note(
    val id: String,
    val title: String,
    val content: String
)
