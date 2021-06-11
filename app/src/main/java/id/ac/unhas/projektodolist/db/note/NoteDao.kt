package id.ac.unhas.projektodolist.db.note

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
//    @Query("Select * from note")
//    fun getNotes(): LiveData<List<Note>>
    @Query("SELECT * FROM note ORDER BY tenggat_waktu ASC, tenggat_jam ASC")
    fun getNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun hapusNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note WHERE judul LIKE :judul")
    fun cariHasil(judul: String): LiveData<List<Note>>

    @Query("SELECT * FROM note ORDER BY tenggat_waktu DESC, tenggat_jam DESC")
    fun urutbyTenggatWaktuMenurun(): LiveData<List<Note>>

    @Query("SELECT * FROM note ORDER BY buat_waktu ASC")
    fun urutbyBuatWaktuMenaik(): LiveData<List<Note>>

    @Query("SELECT * FROM note ORDER BY buat_waktu DESC")
    fun urutbyBuatWaktuMenurun(): LiveData<List<Note>>
}