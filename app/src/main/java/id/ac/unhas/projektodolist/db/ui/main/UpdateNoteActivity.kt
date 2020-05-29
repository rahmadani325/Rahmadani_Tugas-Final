package id.ac.unhas.projektodolist.db.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem



class UpdateNoteActivity : AppCompatActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_list)
        if(supportActionBar != null){
            supportActionBar?.title = "Add a Task"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Back Button
    }

    // Back Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}