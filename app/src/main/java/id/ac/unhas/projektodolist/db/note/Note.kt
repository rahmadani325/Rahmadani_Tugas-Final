package id.ac.unhas.projektodolist.db.note

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "buat_waktu")
    val buatWaktu: Int? = null,

    @ColumnInfo(name = "str_buat_waktu")
    val strBuatWaktu: String? = null,

    @ColumnInfo(name = "update_waktu")
    val updateWaktu: Int? = null,

    @ColumnInfo(name = "tenggat_waktu")
    val tenggatWaktu: Int? = null,

    @ColumnInfo(name = "str_tenggat_waktu")
    val strTenggatWaktu: String? = null,

    @ColumnInfo(name = "tenggat_jam")
    val tenggatJam: Int? = null,

    @ColumnInfo(name = "str_tenggat_jam")
    val strTenggatJam: String? = null,

    @ColumnInfo(name = "judul")
    val judul: String,

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "selesai")
    val isFinished: Boolean? = null

)  : Parcelable