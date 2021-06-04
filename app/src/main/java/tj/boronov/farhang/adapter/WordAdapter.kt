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
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.PopupMenu
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
import tj.boronov.farhang.dialog.WordDialog
import tj.boronov.farhang.util.scale


class WordAdapter(
    _fragmentManager: FragmentManager,
    val context: Context
) :
    PagingDataAdapter<Word, WordAdapter.WordViewHolder>(
        WordComparator
    ) {
    private val fragmentManager = _fragmentManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.word_item, parent, false)
        )
    }

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
        setFavorite(false, word?.favorite ?: 0, holder)

        // Button for add word to favorite
        holder.itemView.findViewById<ImageButton>(R.id.btn_favorite).setOnClickListener {
            word!!.favorite = (word.favorite + 1) % 2
            setFavorite(true, word.favorite, holder)

            CoroutineScope(Dispatchers.IO).launch {
                App.database.wordDao().update(word)
            }
        }

        // menu
        val menu = holder.itemView.findViewById<FrameLayout>(R.id.menu_button)
        menu.setOnClickListener { view: View ->
            val popup = PopupMenu(context, menu)
            popup.inflate(R.menu.word_item_menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.copy_menu_item -> {
                        val clipboard: ClipboardManager =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                        val clip = ClipData.newPlainText(
                            "",
                            word?.word.toString() + " = " + word?.definition.toString()
                        )
                        clipboard.setPrimaryClip(clip)
                        Snackbar.make(
                            view.rootView,
                            view.context.resources.getString(R.string.copy),
                            Snackbar.LENGTH_SHORT
                        )
                            .setTextColor(Color.WHITE)
                            .setBackgroundTint(
                                ContextCompat.getColor(
                                    view.context,
                                    R.color.colorGreen
                                )
                            )
                            .show()

                        return@setOnMenuItemClickListener true
                    }
                    R.id.share_menu_item -> {
                        val sendIntent = Intent()
                        sendIntent.action = Intent.ACTION_SEND
                        sendIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            word?.word.toString() + " = " + word?.definition.toString()
                        )
                        sendIntent.type = "text/plain"
                        startActivity(
                            context,
                            Intent.createChooser(
                                sendIntent,
                                context.resources.getString(R.string.share)
                            ),
                            null
                        )
                        return@setOnMenuItemClickListener true
                    }
                    else -> return@setOnMenuItemClickListener false
                }
            }
            popup.show()
        }
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

    private fun setFavorite(isClicked: Boolean, isFavorite: Int, holder: WordViewHolder) {
        holder.itemView.findViewById<ImageButton>(R.id.btn_favorite).background =
            if (isFavorite == 0) {
                AppCompatResources.getDrawable(
                    holder.itemView.context,
                    R.drawable.ic_favorite
                )
            } else {
                if (isClicked) scale(holder.itemView.findViewById(R.id.btn_favorite))
                AppCompatResources.getDrawable(
                    holder.itemView.context,
                    R.drawable.ic_favorite_true
                )
            }
    }
}