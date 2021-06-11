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

    fun insertNote(note: Note) = runBlocking {
        this.launch(Dispatchers.IO) {
            noteDao?.insertNote(note)
        }
    }

    fun hapusNote(note: Note) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                noteDao?.hapusNote(note)
            }
        }
    }

    fun updateNote(note: Note) = runBlocking {
        this.launch(Dispatchers.IO) {
            noteDao?.updateNote(note)
        }
    }

    fun cariHasil(judul: String): LiveData<List<Note>>?{
        return noteDao?.cariHasil(judul)
    }

    fun urutbyTenggatWaktuMenurun(): LiveData<List<Note>>?{
        return noteDao?.urutbyTenggatWaktuMenurun()
    }

    fun urutbyBuatWaktuMenaik(): LiveData<List<Note>>?{
        return noteDao?.urutbyBuatWaktuMenaik()
    }

    fun urutbyBuatWaktuMenurun(): LiveData<List<Note>>?{
        return noteDao?.urutbyBuatWaktuMenurun()
    }
}