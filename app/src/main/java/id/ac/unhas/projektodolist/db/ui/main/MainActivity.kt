package id.ac.unhas.projektodolist.db.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.content.Intent
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unhas.projektodolist.R
import id.ac.unhas.projektodolist.db.note.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_details.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var floatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton = findViewById(R.id.fab)

        noteRV.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(this) { note, i ->
            showAlertMenu(note)
        }
        noteRV.adapter = noteAdapter

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getNotes()?.observe(this, Observer {
            noteAdapter.setNotes(it)
        })

        floatingActionButton.setOnClickListener{
            inputNote()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenu -> showAlertDialogAdd()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun inputNote(){
        val addIntent = Intent(this, InputNoteActivity::class.java)
        startActivity(addIntent)
    }

    private fun cariNote(menu: Menu?){
        val item = menu?.findItem(R.id.search_list)

        val searchView = item?.actionView as androidx.appcompat.widget.SearchView?
        searchView?.isSubmitButtonEnabled = true

        searchView?.setOnQueryTextListener(
            object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(query != null){
                        getItemsFromDb(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText != null){
                        getItemsFromDb(newText)
                    }
                    return true
                }
            }
        )
    }

    private fun getItemsFromDb(searchText: String){
        var searchText = searchText
        searchText = "%$searchText%"

        noteViewModel.searchResult(searchText)?.observe(this, Observer {
            noteAdapter.setNotes(it)
        })
    }

    private fun sortNote(){
        val items = arrayOf("Tenggat Waktu", "Waktu Buat")

        val builder = AlertDialog.Builder(this)
        val alert = AlertDialog.Builder(this)
        builder.setTitle("Sort by ...")
            .setItems(items){dialog, which ->
                when(which){
                    0 -> {
                        alert.setTitle(items[which])
                            .setPositiveButton("Ascending"){dialog, _ ->
                                noteViewModel.getNotes()?.observe(this, Observer {
                                    noteAdapter.setNotes(it)
                                })
                                dialog.dismiss()
                            }
                            .setNegativeButton("Descending"){dialog, _ ->
                                noteViewModel.sortByDueDateDescending()?.observe(this, Observer {
                                    noteAdapter.setNotes(it)
                                })
                                dialog.dismiss()
                            }
                            .show()
                    }
                    1 -> {
                        alert.setTitle(items[which])
                            .setPositiveButton("Ascending"){dialog, _ ->
                                noteViewModel.sortByCreatedDateAscending()?.observe(this, Observer {
                                    noteAdapter.setNotes(it)
                                })
                                dialog.dismiss()
                            }
                            .setNegativeButton("Descending"){dialog, _ ->
                                noteViewModel.sortByDueDateDescending()?.observe(this, Observer {
                                    noteAdapter.setNotes(it)
                                })
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        builder.show()
    }

    private fun showAlertMenu(note: Note) {
        val items = arrayOf("Details", "Edit", "Delete")

        val builder = AlertDialog.Builder(this)
        val alert = AlertDialog.Builder(this)
        builder.setItems(items){ dialog, which ->
            // the user clicked on colors[which]
            when (which) {
                0 -> {
//                    showAlertDialogEdit(note)
                    listDetails(alert, note)
                }
                1 -> {
//                    noteViewModel.deleteNote(note)
                      updateNote(note)

                }
                2 -> {
                    alert.setTitle("Hapus note ?")
                        .setMessage("Yakin?")
                        .setPositiveButton("Ya"){dialog, _ ->
                            noteViewModel.deleteNote(note)
                            dialog.dismiss()
                        }
                        .setNegativeButton("Tidak"){dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
        builder.show()
    }

    private fun listDetails(alert: AlertDialog.Builder, note: Note){
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.item_details, null)

        val judul: TextView = dialogView.findViewById(R.id.judul)
        val buatWaktu: TextView = dialogView.findViewById(R.id.tenggat_hari_content)
        val tenggatJam: TextView = dialogView.findViewById(R.id.tenggat_jam_content)
        val additionalNote: TextView = dialogView.findViewById(R.id.note_content)

        judul.text = note.judul
        buatWaktu.text = note.strBuatWaktu
        tenggatJam.text = "${note.strTenggatWaktu}, ${note.strTenggatJam}"
        additionalNote.text = note.note

        alert.setView(dialogView)
            .setNeutralButton("OK"){dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun updateNote(note: Note){
        val addIntent = Intent(this, UpdateNoteActivity::class.java)
            .putExtra("EXTRA_LIST", note)
            .putExtra(UpdateNoteActivity.EXTRA_JUDUL_UPDATE, note.judul)
            .putExtra(UpdateNoteActivity.EXTRA_WAKTU_UPDATE, note.strTenggatWaktu)
            .putExtra(UpdateNoteActivity.EXTRA_JAM_UPDATE, note.strTenggatJam)
            .putExtra(UpdateNoteActivity.EXTRA_NOTE_UPDATE, note.note)
            .putExtra(UpdateNoteActivity.EXTRA_IS_FINISHED_UPDATE, note.isFinished)

        startActivity(addIntent)
    }
//    private fun showAlertDialogAdd() {
//        val alert = AlertDialog.Builder(this)
//
//        val editText = EditText(applicationContext)
//        editText.hint = "Enter your text"
//
//        alert.setTitle("New Note")
//        alert.setView(editText)
//
//        alert.setPositiveButton("Save") { dialog, _ ->
//            noteViewModel.insertNote(
//                Note(note = editText.text.toString())
//            )
//            dialog.dismiss()
//        }
//
//        alert.setNegativeButton("Cancel") { dialog, _ ->
//            dialog.dismiss()
//        }
//
//        alert.show()
//    }
//
//
//
//    private fun showAlertDialogEdit(note: Note) {
//        val alert = AlertDialog.Builder(this)
//
//        val editText = EditText(applicationContext)
//        editText.setText(note.note)
//
//        alert.setTitle("Edit Note")
//        alert.setView(editText)
//
//        alert.setPositiveButton("Update") { dialog, _ ->
//            note.note = editText.text.toString()
//            noteViewModel.updateNote(note)
//            dialog.dismiss()
//        }
//
//        alert.setNegativeButton("Cancel") { dialog, _ ->
//            dialog.dismiss()
//        }
//
//        alert.show()
//    }
}
