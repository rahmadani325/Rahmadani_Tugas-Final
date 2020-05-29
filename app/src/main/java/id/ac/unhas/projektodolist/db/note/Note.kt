package id.ac.unhas.projektodolist.db.note

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "buat_waktu")
    var buatWaktu: Int? = null,

    @ColumnInfo(name = "str_buat_waktu")
    var strBuatWaktu: String? = null,

    @ColumnInfo(name = "update_waktu")
    var updateWaktu: Int? = null,

    @ColumnInfo(name = "tenggat_waktu")
    var tenggatWaktu: Int? = null,

    @ColumnInfo(name = "str_tenggat_waktu")
    var strTenggatWaktu: String? = null,

    @ColumnInfo(name = "tenggat_jam")
    var tenggatJam: Int? = null,

    @ColumnInfo(name = "str_tenggat_jam")
    var strTenggatJam: String? = null,

    @ColumnInfo(name = "judul")
    var judul: String,

    @ColumnInfo(name = "note")
    var note: String,

    @ColumnInfo(name = "selesai")
    var isFinished: Boolean? = null

)  : Parcelable