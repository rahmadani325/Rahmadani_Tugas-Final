package id.ac.unhas.projektodolist.db.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class InputNoteActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list)
        if(supportActionBar != null){
            supportActionBar?.title = "Tambahkan note"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Back Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}