package tj.boronov.farhang.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.boronov.farhang.adapter.NoteAdapter
import tj.boronov.farhang.databinding.FragmentNoteBinding

class NoteFragment : Fragment() {

    lateinit var viewModel: NoteViewModel
    lateinit var binding: FragmentNoteBinding
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNoteBinding.inflate(inflater, container, false)

        noteAdapter = NoteAdapter()

        binding.noteList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            adapter = noteAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest {
                noteAdapter.submitData(it)
            }
        }
        return binding.root
    }
}