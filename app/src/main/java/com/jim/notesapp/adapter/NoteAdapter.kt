package com.jim.notesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jim.notesapp.R
import com.jim.notesapp.model.Note

class NoteAdapter(private var itemClickListener: ItemClickListener) : RecyclerView.Adapter<NoteAdapter.NoteAdapterViewHolder>() {
    var allNotes: List<Note> = ArrayList()
    var currentItem: Note? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapterViewHolder {
        val viewHold =
            LayoutInflater.from(parent.context).inflate(R.layout.single_note, parent, false)
        return NoteAdapterViewHolder(viewHold)
    }

    override fun onBindViewHolder(holder: NoteAdapterViewHolder, position: Int) {
        val currentNote: Note = allNotes.get(position)
        holder.title.text = currentNote.title
        holder.description.text = currentNote.description
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun setNotes(notes: List<Note>) {
        this.allNotes = notes
        notifyDataSetChanged()
    }

   inner class NoteAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val title = itemView.findViewById<TextView>(R.id.title)
        val description = itemView.findViewById<TextView>(R.id.description)
        val deleteButton = itemView.findViewById<ImageView>(R.id.deleteBtn)

        init{
            deleteButton.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            when(p0){
                deleteButton -> {
                    if (position!=RecyclerView.NO_POSITION) {
                        itemClickListener.onDeleteClicked(allNotes,position)
                    }
                }
                itemView -> {
                    if (position!=RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(allNotes,position)
                    }
                }
            }


        }
    }

    interface ItemClickListener {
        fun onDeleteClicked( allNotes: List<Note>, position: Int)
        fun onItemClick(allNotes: List<Note>,position: Int)
    }
}