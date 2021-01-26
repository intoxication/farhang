package tj.boronov.farhang.ui.note

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
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import tj.boronov.farhang.App
import tj.boronov.farhang.R
import tj.boronov.farhang.databinding.DialogNoteBinding

class NoteDialog : DialogFragment() {

    lateinit var binding: DialogNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogNoteBinding.inflate(layoutInflater, container, false)

        val noteID = requireArguments().getInt("note_id", 0)
        val noteName = requireArguments().getString("note_name").toString()
        val noteDescription = requireArguments().getString("note_description").toString()
        var noteFavorite = requireArguments().getInt("note_favorite", 0)

        binding.noteName.text = noteName
        binding.noteDescription.text = noteDescription

        binding.noteDescription.movementMethod = ScrollingMovementMethod()

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        // Set isFavorite icon in item
        setFavorite(noteFavorite)

        // Change favorite status
        binding.btnFavorite.setOnClickListener {
            noteFavorite = (noteFavorite + 1) % 2
            setFavorite(noteFavorite)
            lifecycleScope.launch {
                App.database.noteDao().update(noteID, noteFavorite)
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
                "$noteName\n$noteDescription"
            )

            clipboard.setPrimaryClip(clip)
        }

        // Share
        binding.btnShare.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "$noteName\n$noteDescription"
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