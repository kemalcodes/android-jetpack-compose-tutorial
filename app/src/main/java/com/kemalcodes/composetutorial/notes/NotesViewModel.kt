package com.kemalcodes.composetutorial.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NotesState(
    val notes: List<Note> = emptyList(),
    val searchQuery: String = ""
)

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = DatabaseProvider.getDatabase(application).noteDao()
    private val _state = MutableStateFlow(NotesState())
    val state: StateFlow<NotesState> = _state.asStateFlow()
    private var searchJob: Job? = null

    init { observeNotes("") }

    fun addNote(title: String, content: String) {
        viewModelScope.launch { dao.insert(Note(title = title, content = content)) }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch { dao.delete(note) }
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
        observeNotes(query)
    }

    private fun observeNotes(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val flow = if (query.isEmpty()) dao.getAllNotes() else dao.searchNotes(query)
            flow.collect { notes -> _state.update { it.copy(notes = notes) } }
        }
    }
}
