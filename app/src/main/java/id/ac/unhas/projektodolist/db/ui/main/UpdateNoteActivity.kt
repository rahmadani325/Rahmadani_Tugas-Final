package id.ac.unhas.projektodolist.db.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import id.ac.unhas.projektodolist.db.note.Note
import java.text.SimpleDateFormat
import id.ac.unhas.projektodolist.R
import java.util.*



class UpdateNoteActivity : AppCompatActivity {
    private lateinit var editTextJudul: EditText
    private lateinit var editTextWaktu: EditText
    private lateinit var editTextJam: EditText
    private lateinit var editTextNote: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnBatal: Button
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var note: Note
    private var kalender = Calendar.getInstance()

    companion object{
        const val EXTRA_JUDUL_UPDATE = "JUDUL"
        const val EXTRA_WAKTU_UPDATE = "date-month-year"
        const val EXTRA_JAM_UPDATE = "hour:minutes"
        const val EXTRA_NOTE_UPDATE = "NOTE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)
        if (supportActionBar != null) {
            supportActionBar?.title = "Update Note"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Back Buttonnya


        editTextJudul = findViewById(R.id.judul_konten)
        editTextWaktu = findViewById(R.id.tenggat_waktu_konten)
        editTextJam = findViewById(R.id.tenggat_jam_konten)
        editTextNote = findViewById(R.id.note_konten)
        btnUpdate = findViewById(R.id.btn_update)
        btnBatal = findViewById(R.id.btn_batal)
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
    }

    private fun setDueDate(){
        val date = kalender.get(Calendar.DAY_OF_MONTH)
        val month = kalender.get(Calendar.MONTH)
        val year = kalender.get(Calendar.YEAR)

        // Date picker dialog
        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{
                    view, year, month, date ->
                editTextWaktu.setText("" + date + "-" + (month+1) + "-" + year)
            }, year, month, date)

        datePicker.show()
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

    private fun updateNote(note: Note){
        note.judul = editTextJudul.text.toString().trim()
        note.tenggatWaktu = editTextWaktu.text.toString().trim()
        note.tenggatJam = editTextJam.text.toString().trim()
        note.note = editTextNote.text.toString().trim()

        noteViewModel.updateNote(note)

        finish()
    }
}