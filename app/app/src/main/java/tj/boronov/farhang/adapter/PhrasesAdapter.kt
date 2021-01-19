package tj.boronov.farhang.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tj.boronov.farhang.R
import tj.boronov.farhang.data.model.Phrases

class PhrasesAdapter :
    PagingDataAdapter<Phrases, PhrasesAdapter.WordViewHolder>(
        PhrasesComparator
    ) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        // Get data
        val phrase = getItem(position)

        // Set data to item
        holder.itemView.findViewById<TextView>(R.id.translate_ru).text = phrase?.translateRu
        holder.itemView.findViewById<TextView>(R.id.translate_tj).text = phrase?.translateTj

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.phrase_item, parent, false)
        )
    }

    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view)

    // Phrases comparator
    object PhrasesComparator : DiffUtil.ItemCallback<Phrases>() {
        override fun areItemsTheSame(oldItem: Phrases, newItem: Phrases): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Phrases, newItem: Phrases): Boolean {
            return oldItem.translateRu == newItem.translateRu && oldItem.translateTj == newItem.translateTj
        }
    }
}