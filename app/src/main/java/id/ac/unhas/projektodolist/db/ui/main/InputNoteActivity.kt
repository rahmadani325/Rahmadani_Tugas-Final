package id.ac.unhas.projektodolist.db.ui.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import id.ac.unhas.projektodolist.R
import id.ac.unhas.projektodolist.db.note.Note
import id.ac.unhas.projektodolist.db.ui.main.NoteViewModel
import id.ac.unhas.projektodolist.db.ui.main.Converter
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class InputNoteActivity : AppCompatActivity() {
    private lateinit var editTextJudul: EditText
    private lateinit var editTextWaktu: EditText
    private lateinit var editTextNote: EditText
    private lateinit var editTextJam: EditText
    private lateinit var btnSimpan: Button
    private lateinit var noteViewModel: NoteViewModel
    private var kalender = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_note)
        if(supportActionBar != null){
            supportActionBar?.title = "Tambah Sebuah Note"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Back Button

        editTextJudul = findViewById(R.id.judul_content)
        editTextWaktu = findViewById(R.id.tenggat_hari_content)
        editTextNote = findViewById(R.id.note_content)
        editTextJam = findViewById(R.id.tenggat_jam_content)
        btnSimpan = findViewById(R.id.btn_simpan)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        editTextWaktu.setOnClickListener{
            setDueDate()
        }

        editTextJam.setOnClickListener {
            setDueTime()
        }

        btnSimpan.setOnClickListener{
            saveList()
        }
    }

    // Back Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun setDueDate(){
        val date = kalender.get(Calendar.DAY_OF_MONTH)
        val month = kalender.get(Calendar.MONTH)
        val year = kalender.get(Calendar.YEAR)

        // Date picker dialog
        val dateListener = DatePickerDialog.OnDateSetListener{ view, year, month, date ->
            kalender.set(Calendar.YEAR, year)
            kalender.set(Calendar.MONTH, month)
            kalender.set(Calendar.DATE, date)
            editTextWaktu.setText(SimpleDateFormat("EEE, MMM dd, yyyy").format(kalender.time))
        }

        DatePickerDialog(this, dateListener, year, month, date).show()
    }

    private fun setDueTime(){
        val hour = kalender.get(Calendar.HOUR_OF_DAY)
        val minute = kalender.get(Calendar.MINUTE)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            kalender.set(Calendar.HOUR_OF_DAY, hour)
            kalender.set(Calendar.MINUTE, minute)
            editTextJam.setText(SimpleDateFormat("HH:mm").format(kalender.time))
        }

        TimePickerDialog(this, timeSetListener, hour, minute, true).show()
    }

    private fun saveList(){
        val current = ZonedDateTime.now(ZoneId.of("+8"))
        val formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy, HH:mm:ss")

        val strBuatWaktu = current.format(formatter)
        val buatWaktu = Converter.dateToInt(current)

        var tenggatWaktu: Int? = null
        var tenggatJam: Int? = null
        var strTenggatWaktu: String? = ""
        var strTenggatJam: String? = ""

        if(editTextJam.text.isNotEmpty()) {
            strTenggatJam = editTextJam.text.toString().trim()
            tenggatJam = Converter.stringTimeToInt(strTenggatJam) // Konversi ke integer
        }

//        val strTenggatWaktu = editTextDate.text.toString().trim()
//        val tenggatWaktu = Converter.stringDateToInt(strTenggatWaktu)
//
//        val strTenggatJam = editTextTime.text.toString().trim()
//        val tenggatJam= Converter.stringTimeToInt(strTenggatJam)
//
        val judul = editTextJudul.text.toString().trim()
        val note = editTextNote.text.toString().trim()



        noteViewModel.insertNote(
            Note(
                buatWaktu = buatWaktu,
                strBuatWaktu = strBuatWaktu,
                judul = judul,
                tenggatWaktu = tenggatWaktu,
                tenggatJam = tenggatJam,
                strTenggatWaktu = strTenggatWaktu,
                strTenggatJam = strTenggatJam,
                note = note,
                isFinished = false
            )
        )

        finish()
    }
}
