package id.ac.unhas.projektodolist.db.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "buat_waktu")
    val buatWaktu: Date? = null,

    @ColumnInfo(name = "update_waktu")
    val updateWaktu: Date? = null,

    @ColumnInfo(name = "tenggat_waktu")
    val tenggatWaktu: Date? = null,

    @ColumnInfo(name = "judul")
    val judul: String,

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "selesai")
    val isFinished: Boolean? = null
)