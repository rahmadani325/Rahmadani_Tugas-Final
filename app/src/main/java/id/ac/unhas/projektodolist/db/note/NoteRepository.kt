package id.ac.unhas.projektodolist.db.note

import android.app.Application
import androidx.lifecycle.LiveData
import id.ac.unhas.projektodolist.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class NoteRepository(application: Application) {

    private val noteDao: NoteDao?
    private var notes: LiveData<List<Note>>? = null

    init {
        val db = AppDatabase.getInstance(application.applicationContext)
        noteDao = db?.noteDao()
        notes = noteDao?.getNotes()
    }

    fun getNotes(): LiveData<List<Note>>? {
        return notes
    }

    fun insertJudul(judul: Note) = runBlocking {
        this.launch(Dispatchers.IO) {
            noteDao?.insertJudul(judul)
        }

        fun insertNote(deskripsi: Note) = runBlocking {
            this.launch(Dispatchers.IO) {
                noteDao?.insertNote(deskripsi)
            }
        }

        fun insertTenggatWaktu(tenggatWaktu: Date) = runBlocking {
            this.launch(Dispatchers.IO) {
                noteDao?.insertTenggatWaktu(tenggatWaktu)
            }
        }

        fun delete(note: Note) {
            runBlocking {
                this.launch(Dispatchers.IO) {
                    noteDao?.deleteNote(note)
                }
            }
        }

        fun update(note: Note) = runBlocking {
            this.launch(Dispatchers.IO) {
                noteDao?.updateNote(note)
            }
        }

    }
}