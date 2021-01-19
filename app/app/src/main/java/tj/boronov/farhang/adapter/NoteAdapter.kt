package tj.boronov.farhang.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import tj.boronov.farhang.R
import tj.boronov.farhang.data.model.Note

class NoteAdapter :
    PagingDataAdapter<Note, NoteAdapter.WordViewHolder>(
        NoteComparator
    ) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        // Get data
        val note = getItem(position)

        // Set data to item
        holder.itemView.findViewById<TextView>(R.id.note_name).text = note?.name
        holder.itemView.findViewById<TextView>(R.id.note_id).text = "#${note?.id.toString()}"

        //On note click
        holder.itemView.setOnClickListener {
            MaterialAlertDialogBuilder(it.context)
                .setTitle(note?.name)
                .setMessage(note?.description)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        )
    }

    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // Notes comparator
    object NoteComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.name == newItem.name
        }
    }
}