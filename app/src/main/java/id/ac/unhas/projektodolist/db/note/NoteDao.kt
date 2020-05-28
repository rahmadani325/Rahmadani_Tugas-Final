package id.ac.unhas.projektodolist.db.note

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface NoteDao {
    @Query("Select * from note")
    fun getNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertJudul(judul: Note)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(deskripsi: Note)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTenggatWaktu(tenggatWaktu: Date)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}