package id.ac.unhas.projektodolist.db.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.unhas.projektodolist.R
import id.ac.unhas.projektodolist.db.note.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(private val context: Context?, private val listener: (Note, Int) -> Unit) :
    RecyclerView.Adapter<NoteViewHolder>() {

    private var notes = listOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        if (context != null) {
            holder.bindItem(context, notes[position], listener)
        }
    }

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }
}

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(context: Context, note: Note, listener: (Note, Int) -> Unit) {
        itemView.item_judul_note.text = note.judul
        itemView.item_tenggat_waktu.text = note.dueDate?.time.toString()
        itemView.item_note.text = note.note


        itemView.setOnClickListener {
            listener(note, layoutPosition)
        }
    }

}