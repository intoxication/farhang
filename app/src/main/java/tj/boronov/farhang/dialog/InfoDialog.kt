package tj.boronov.farhang.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import tj.boronov.farhang.databinding.DialogInfoPhrasesBinding

class InfoDialog : DialogFragment() {

    lateinit var binding: DialogInfoPhrasesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogInfoPhrasesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}