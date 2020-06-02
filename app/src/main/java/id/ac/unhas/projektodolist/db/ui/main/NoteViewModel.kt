package id.ac.unhas.projektodolist.db.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.unhas.projektodolist.db.note.Note
import id.ac.unhas.projektodolist.db.note.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var noteRepository = NoteRepository(application)
    private var notes: LiveData<List<Note>>? = noteRepository.getNotes()

    fun getNotes(): LiveData<List<Note>>? {
        return notes
    }

    fun insertNote(note: Note) {
        noteRepository.insertNote(note)
    }

    fun hapusNote(note: Note) {
        noteRepository.hapusNote(note)
    }

    fun updateNote(note: Note) {
        noteRepository.updateNote(note)
    }

    fun cariHasil(title: String): LiveData<List<Note>>?{
        return noteRepository.cariHasil(title)
    }

    fun urutbyTenggatWaktuMenurun(): LiveData<List<Note>>?{
        return noteRepository.urutbyTenggatWaktuMenurun()
    }

    fun urutbyBuatWaktuMenaik(): LiveData<List<Note>>?{
        return noteRepository.urutbyBuatWaktuMenaik()
    }

    fun urutbyBuatWaktuMenurun(): LiveData<List<Note>>?{
        return noteRepository.urutbyBuatWaktuMenurun()
    }

}