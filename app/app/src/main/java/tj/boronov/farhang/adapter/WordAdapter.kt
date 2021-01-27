package tj.boronov.farhang.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tj.boronov.farhang.App
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
            data.putInt("word_dictionaryID", word?.dictionaryID ?: 0)

            wordDialog.arguments = data

            wordDialog.show(fragmentManager, "word_dialog")
        }

        // Set isFavorite icon in item
        holder.itemView.findViewById<Button>(R.id.btn_favorite).background =
            if (word?.favorite == 0) {
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

        // Button for copy word to clipboard
        holder.itemView.findViewById<Button>(R.id.btn_copy).setOnClickListener {
            Snackbar.make(
                it.rootView,
                it.context.resources.getString(R.string.copy),
                Snackbar.LENGTH_SHORT
            )
                .setTextColor(Color.WHITE)
                .setBackgroundTint(ContextCompat.getColor(it.context, R.color.colorGreen))
                .show()

            val clipboard: ClipboardManager =
                it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val clip = ClipData.newPlainText(
                "",
                word?.word.toString() + " = " + word?.definition.toString()
            )

            clipboard.setPrimaryClip(clip)
        }

        // Button to send word to others
        holder.itemView.findViewById<Button>(R.id.btn_share).setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                word?.word.toString() + " = " + word?.definition.toString()
            )
            sendIntent.type = "text/plain"
            startActivity(
                it.context,
                Intent.createChooser(sendIntent, it.resources.getString(R.string.share)),
                null
            )
        }

        // Button for add word to favorite
        holder.itemView.findViewById<Button>(R.id.btn_favorite).setOnClickListener {
            word!!.favorite = (word.favorite + 1) % 2
            CoroutineScope(Dispatchers.IO).launch {
                App.database.wordDao().update(word)
            }
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