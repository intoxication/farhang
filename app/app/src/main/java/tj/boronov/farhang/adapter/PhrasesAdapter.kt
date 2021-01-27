package tj.boronov.farhang.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tj.boronov.farhang.App
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

        // Set isFavorite icon in item
        holder.itemView.findViewById<Button>(R.id.btn_favorite).background =
            if (phrase?.favorite == 0) {
                AppCompatResources.getDrawable(
                    holder.itemView.context,
                    R.drawable.ic_favorite
                )
            } else {
                AppCompatResources.getDrawable(
                    holder.itemView.context,
                    R.drawable.ic_favorite_true
                )
            }

        // Button for add word to favorite
        holder.itemView.findViewById<Button>(R.id.btn_favorite).setOnClickListener {
            phrase!!.favorite = (phrase.favorite + 1) % 2

            CoroutineScope(Dispatchers.IO).launch {
                App.database.phrasebookDao().update(phrase)
            }
        }

        // On phrases click
        holder.itemView.setOnClickListener { view ->
            val popup = PopupMenu(holder.itemView.context, holder.itemView)
            popup.inflate(R.menu.phrases_menu)

            val clipboard: ClipboardManager =
                view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            popup.setOnMenuItemClickListener {
                Snackbar.make(
                    view.rootView,
                    view.context.resources.getString(R.string.copy),
                    Snackbar.LENGTH_SHORT
                )
                    .setTextColor(Color.WHITE)
                    .setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorGreen))
                    .show()

                when (it.itemId) {
                    R.id.copy_ru -> {
                        val clip = ClipData.newPlainText(
                            "",
                            phrase?.translateRu
                        )
                        clipboard.setPrimaryClip(clip)
                        true
                    }
                    R.id.copy_tj -> {
                        val clip = ClipData.newPlainText(
                            "",
                            phrase?.translateTj
                        )
                        clipboard.setPrimaryClip(clip)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        // On long phrases click
        holder.itemView.setOnLongClickListener {
            val clipboard: ClipboardManager =
                it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            Snackbar.make(
                it.rootView,
                it.context.resources.getString(R.string.copy),
                Snackbar.LENGTH_SHORT
            )
                .setTextColor(Color.WHITE)
                .setBackgroundTint(ContextCompat.getColor(it.context, R.color.colorGreen))
                .show()

            val clip = ClipData.newPlainText(
                "",
                phrase?.translateTj + " — " + phrase?.translateRu
            )
            clipboard.setPrimaryClip(clip)
            return@setOnLongClickListener true
        }
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
            return oldItem.favorite == newItem.favorite
        }
    }
}