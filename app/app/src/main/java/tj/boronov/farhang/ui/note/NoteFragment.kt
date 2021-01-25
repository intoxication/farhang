package tj.boronov.farhang.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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
    ): View {

        binding = FragmentNoteBinding.inflate(inflater, container, false)

        noteAdapter = NoteAdapter(requireActivity().supportFragmentManager)
        noteAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && noteAdapter.itemCount < 1) {
                binding.noteList.visibility = View.GONE
                binding.layoutNoNote.root.visibility = View.VISIBLE
            } else {
                binding.noteList.visibility = View.VISIBLE
                binding.layoutNoNote.root.visibility = View.GONE
            }
        }

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

        binding.searchNote.addTextChangedListener {
            viewModel.filterDatabase(it.toString().trim())
        }

        return binding.root
    }
}