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
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import id.ac.unhas.projektodolist.db.ui.main.Converter
import java.util.*

class InputNoteActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDate: EditText
    private lateinit var editTextNote: EditText
    private lateinit var editTextTime: EditText
    private lateinit var btnSave: Button
    private lateinit var noteViewModel: NoteViewModel
    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_note)
        if(supportActionBar != null){
            supportActionBar?.title = "Tambah Sebuah Note"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Back Button

        editTextTitle = findViewById(R.id.judul_content)
        editTextDate = findViewById(R.id.tenggat_hari_content)
        editTextNote = findViewById(R.id.note_content)
        editTextTime = findViewById(R.id.tenggat_jam_content)
        btnSave = findViewById(R.id.btn_simpan)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        editTextDate.setOnClickListener{
            setDueDate()
        }

        editTextTime.setOnClickListener {
            setDueTime()
        }

        btnSave.setOnClickListener{
            saveList()
        }
    }

    // Back Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun setDueDate(){
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        // Date picker dialog
        val dateListener = DatePickerDialog.OnDateSetListener{ view, year, month, date ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DATE, date)
            editTextDate.setText(SimpleDateFormat("EEE, MMM dd, yyyy").format(calendar.time))
        }

        DatePickerDialog(this, dateListener, year, month, date).show()
    }

    private fun setDueTime(){
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            editTextTime.setText(SimpleDateFormat("HH:mm").format(calendar.time))
        }

        TimePickerDialog(this, timeSetListener, hour, minute, true).show()
    }

    private fun saveList(){
        val current = ZonedDateTime.now(ZoneId.of("+8"))
        val formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy, HH:mm:ss")

        val strBuatWaktu = current.format(formatter)
        val buatWaktu = Converter.dateToInt(current)

        val strTenggatWaktu = editTextDate.text.toString().trim()
        val tenggatWaktu = Converter.stringDateToInt(strTenggatWaktu)

        val strTenggatJam = editTextTime.text.toString().trim()
        val tenggatJam= Converter.stringTimeToInt(strTenggatJam)

        val judul = editTextTitle.text.toString().trim()
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
