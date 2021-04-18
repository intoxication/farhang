package tj.boronov.farhang.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tj.boronov.farhang.R
import tj.boronov.farhang.databinding.ChooseDirectDialogBinding
import tj.boronov.farhang.interfaces.ChooseDirectListener

class ChooseDirectDialog(listener: ChooseDirectListener) : BottomSheetDialogFragment() {
    private lateinit var binding: ChooseDirectDialogBinding
    private val chooseLanguageListener = listener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChooseDirectDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.directAll.setOnClickListener {
            chooseLanguageListener.callbackDirect(getString(R.string.direct_all), 0)
            dismiss()
        }

        binding.directTjRu.setOnClickListener {
            chooseLanguageListener.callbackDirect(getString(R.string.direct_tJ_ru), 1)
            dismiss()
        }

        binding.directTjTj.setOnClickListener {
            chooseLanguageListener.callbackDirect(getString(R.string.direct_tj_tj), 2)
            dismiss()
        }

        binding.directRuTj.setOnClickListener {
            chooseLanguageListener.callbackDirect(getString(R.string.direct_ru_tj), 3)
            dismiss()
        }
    }
}