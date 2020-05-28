package id.ac.unhas.projektodolist.db.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.unhas.projektodolist.db.note.Note
import id.ac.unhas.projektodolist.db.note.NoteRepository
import java.util.*

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var noteRepository = NoteRepository(application)
    private var notes: LiveData<List<Note>>? = noteRepository.getNotes()

//    fun insertNote(note: Note) {
//        noteRepository.insert(note)
//    }

    fun getNotes(): LiveData<List<Note>>? {
        return notes
    }

    fun insertJudul(judul: Note){
        noteRepository.insertJudul(judul)
    }

    fun insertNote(deskripsi: Note){
        noteRepository.insertNote(deskripsi)
    }

    fun insertTenggatWaktu(tenggatWaktu: Date){
        noteRepository.insertTenggatWaktu(tenggatWaktu)
    }

    fun deleteNote(note: Note) {
        noteRepository.delete(note)
    }

    fun updateNote(note: Note) {
        noteRepository.update(note)
    }

}