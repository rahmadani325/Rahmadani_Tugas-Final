package id.ac.unhas.projektodolist.db.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import id.ac.unhas.projektodolist.db.note.Note
import java.text.SimpleDateFormat
import id.ac.unhas.projektodolist.R
import id.ac.unhas.projektodolist.db.ui.main.Converter
import id.ac.unhas.projektodolist.db.ui.main.NoteViewModel
import java.time.ZonedDateTime
import java.util.*


class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var editTextJudul: EditText
    private lateinit var editTextWaktu: EditText
    private lateinit var editTextJam: EditText
    private lateinit var editTextNote: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnBatal: Button
    private lateinit var chkBoxIsFinished: CheckBox

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var note: Note
    private var kalender = Calendar.getInstance()

    companion object{
        const val EXTRA_JUDUL_UPDATE = "JUDUL"
        const val EXTRA_WAKTU_UPDATE = "date-month-year"
        const val EXTRA_JAM_UPDATE = "hour:minutes"
        const val EXTRA_NOTE_UPDATE = "NOTE"
        const val EXTRA_IS_FINISHED_UPDATE = "false"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)
        if (supportActionBar != null) {
            supportActionBar?.title = "Update Note"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Back Buttonnya


        editTextJudul = findViewById(R.id.judul_content)
        editTextWaktu = findViewById(R.id.tenggat_hari_content)
        editTextJam = findViewById(R.id.tenggat_jam_content)
        editTextNote = findViewById(R.id.note_content)
        btnUpdate = findViewById(R.id.btn_update)
        btnBatal = findViewById(R.id.btn_batal)
        chkBoxIsFinished = findViewById(R.id.checkbox_is_finished)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        getExtra()

        editTextWaktu.setOnClickListener {
            setTenggatWaktu()
        }

        editTextJam.setOnClickListener {
            setTenggatJam()
        }

        btnUpdate.setOnClickListener {
            updateNote(note)
        }

        btnBatal.setOnClickListener {
            finish()
        }
    }

    // Back Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun getExtra(){
        note = intent.getParcelableExtra("EXTRA_LIST")!!
        editTextJudul.setText(intent.getStringExtra(EXTRA_JUDUL_UPDATE))
        editTextWaktu.setText(intent.getStringExtra(EXTRA_WAKTU_UPDATE))
        editTextJam.setText(intent.getStringExtra(EXTRA_JAM_UPDATE))
        editTextNote.setText(intent.getStringExtra(EXTRA_NOTE_UPDATE))
        chkBoxIsFinished.isChecked = intent.getBooleanExtra(EXTRA_IS_FINISHED_UPDATE, false)
    }

    private fun setTenggatWaktu(){
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

    private fun setTenggatJam(){
        val hour = kalender.get(Calendar.HOUR_OF_DAY)
        val minute = kalender.get(Calendar.MINUTE)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            kalender.set(Calendar.HOUR_OF_DAY, hour)
            kalender.set(Calendar.MINUTE, minute)
            editTextJam.setText(SimpleDateFormat("HH:mm").format(kalender.time))
        }

        TimePickerDialog(this, timeSetListener, hour, minute, true).show()
    }

    private fun updateNote(note: Note){
        if(chkBoxIsFinished.isChecked){
            noteViewModel.hapusNote(note)
        } else {

            val current = ZonedDateTime.now()
            val updatedDate = Converter.dateToInt(current)

            var tenggatWaktu: Int? = null
            var strTenggatWaktu: String? = ""
            var tenggatJam: Int? = null
            var strTenggatJam: String? = ""

            if (editTextWaktu.text.isNotEmpty()) {
                strTenggatWaktu = editTextWaktu.text.toString().trim()
                tenggatWaktu = Converter.stringDateToInt(strTenggatWaktu)
            }

            if (editTextJam.text.isNotEmpty()) {
                strTenggatJam = editTextJam.text.toString().trim()
                tenggatJam = Converter.stringTimeToInt(strTenggatJam) // Convert it to int
            }

//        val strDueDate = editTextWaktu.text.toString().trim()
//        val dueDate = Converter.stringDateToInt(strDueDate)
//
//        val strDueHour = editTextJam.text.toString().trim()
//        val dueHour= Converter.stringTimeToInt(strDueHour)

//        note.updateWaktu = updatedDate
//        note.judul = editTextJudul.text.toString().trim()
//        note.tenggatWaktu = editTextWaktu.text.toString().trim()
//        note.tenggatJam = editTextJam.text.toString().trim()
//        note.note = editTextNote.text.toString().trim()
//
//        noteViewModel.updateNote(note)
//
//        finish()

            note.updateWaktu = updatedDate
            note.judul = editTextJudul.text.toString().trim()
            note.tenggatWaktu = tenggatWaktu
            note.tenggatJam = tenggatJam
            note.strTenggatWaktu = strTenggatWaktu
            note.strTenggatJam = strTenggatJam
            note.note = editTextNote.text.toString().trim()
            note.isFinished = chkBoxIsFinished.isChecked

            noteViewModel.updateNote(note)
        }
        finish()
    }
}