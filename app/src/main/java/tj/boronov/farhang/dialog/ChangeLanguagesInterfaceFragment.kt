package tj.boronov.farhang.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tj.boronov.farhang.R
import tj.boronov.farhang.databinding.FragmentChangeLanguagesInterfaceBinding

class ChangeLanguagesInterfaceFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentChangeLanguagesInterfaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeLanguagesInterfaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.interfaceToTj.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.interfaceToRu.setOnClickListener {
            dialog!!.dismiss()
        }

        binding.interfaceToEn.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}