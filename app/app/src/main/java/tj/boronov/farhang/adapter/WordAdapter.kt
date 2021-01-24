package tj.boronov.farhang.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tj.boronov.farhang.R
import tj.boronov.farhang.data.model.Word
import tj.boronov.farhang.ui.word.WordDialog

class WordAdapter(_fragmentManager: FragmentManager) :
    PagingDataAdapter<Word, WordAdapter.WordViewHolder>(
        WordComparator
    ) {
    private val fragmentManager = _fragmentManager

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        // Get data
        val word = getItem(position)

        // Set data to item
        holder.itemView.findViewById<TextView>(R.id.text_word).text = word?.word
        holder.itemView.findViewById<TextView>(R.id.text_definition_word).text = word?.definition

        // Dialog box with detailed information on a word
        holder.itemView.setOnClickListener {
            val data = Bundle()
            val wordDialog = WordDialog()

            data.putString("text_word", word?.word)
            data.putString("definition_word", word?.definition)
            data.putInt("word_id", word?.id ?: 0)
            data.putInt("word_favorite", word?.favorite ?: 0)

            wordDialog.arguments = data

            wordDialog.show(fragmentManager, "word_dialog")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.word_item, parent, false)
        )
    }

    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // Word comparator
    object WordComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.favorite == newItem.favorite
        }
    }
}