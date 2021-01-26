package tj.boronov.farhang.ui.word

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import tj.boronov.farhang.App
import tj.boronov.farhang.R
import tj.boronov.farhang.databinding.DialogWordBinding

class WordDialog : DialogFragment() {

    lateinit var binding: DialogWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogWordBinding.inflate(layoutInflater, container, false)

        val wordID = requireArguments().getInt("word_id", 0)
        val wordText = requireArguments().getString("text_word").toString()
        val wordDefinition = requireArguments().getString("definition_word").toString()
        var wordFavorite = requireArguments().getInt("word_favorite", 0)

        binding.textWord.text = wordText
        binding.textDefinitionWord.text = wordDefinition

        binding.textDefinitionWord.movementMethod = ScrollingMovementMethod()

        binding.btnClose.setOnClickListener {
            dismiss()
        }
        // Set isFavorite icon in item
        setFavorite(wordFavorite)

        // Change favorite status
        binding.btnFavorite.setOnClickListener {
            wordFavorite = (wordFavorite + 1) % 2
            setFavorite(wordFavorite)
            lifecycleScope.launch {
                App.database.wordDao().update(wordID, wordFavorite)
            }
        }

        // Copy to buffer
        binding.btnCopy.setOnClickListener {
            Snackbar.make(
                it,
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
                "$wordText\n$wordDefinition"
            )

            clipboard.setPrimaryClip(clip)
        }

        // Share
        binding.btnShare.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "$wordText\n$wordDefinition"
            )
            sendIntent.type = "text/plain"
            ContextCompat.startActivity(
                it.context,
                Intent.createChooser(sendIntent, it.resources.getString(R.string.share)),
                null
            )
        }
        return binding.root
    }

    private fun setFavorite(isFavorite: Int) {
        binding.btnFavorite.background =
            if (isFavorite == 0) {
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_favorite
                )
            } else {
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_favorite_true
                )
            }
    }

}