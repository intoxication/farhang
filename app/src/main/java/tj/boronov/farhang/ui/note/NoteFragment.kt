package tj.boronov.farhang.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import tj.boronov.farhang.adapter.NoteAdapter
import tj.boronov.farhang.databinding.FragmentNoteBinding
import tj.boronov.farhang.ui.BaseFragment
import tj.boronov.farhang.util.vibratePhone

class NoteFragment : BaseFragment() {

    lateinit var viewModel: NoteViewModel
    lateinit var binding: FragmentNoteBinding
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        retainInstance = true

        noteAdapter = NoteAdapter(requireActivity().supportFragmentManager)

        lifecycleScope.launchWhenCreated {
            viewModel.flow.collectLatest {
                noteAdapter.submitData(it)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNoteBinding.inflate(inflater, container, false)

        noteAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && noteAdapter.itemCount < 1) {
                binding.noteList.visibility = View.GONE
                binding.layoutNoNote.root.visibility = View.VISIBLE
                binding.layoutNoNote.noDataImage.playAnimation()
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

        binding.searchNote.addTextChangedListener {
            if (viewModel.query.value != it.toString().trim()) {
                viewModel.query.value = it.toString()
                viewModel.pagingSource?.invalidate()
            }
        }

        binding.letter1.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchNote.selectionStart
            binding.searchNote.setText(
                addLetter(
                    binding.searchNote.text.toString(),
                    binding.letter1.text.toString(),
                    position
                )
            )
            binding.searchNote.setSelection(++position)
        }

        binding.letter2.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchNote.selectionStart
            binding.searchNote.setText(
                addLetter(
                    binding.searchNote.text.toString(),
                    binding.letter2.text.toString(),
                    position
                )
            )
            binding.searchNote.setSelection(++position)
        }

        binding.letter3.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchNote.selectionStart
            binding.searchNote.setText(
                addLetter(
                    binding.searchNote.text.toString(),
                    binding.letter3.text.toString(),
                    position
                )
            )
            binding.searchNote.setSelection(++position)
        }

        binding.letter4.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchNote.selectionStart
            binding.searchNote.setText(
                addLetter(
                    binding.searchNote.text.toString(),
                    binding.letter4.text.toString(),
                    position
                )
            )
            binding.searchNote.setSelection(++position)
        }

        binding.letter5.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchNote.selectionStart
            binding.searchNote.setText(
                addLetter(
                    binding.searchNote.text.toString(),
                    binding.letter5.text.toString(),
                    position
                )
            )
            binding.searchNote.setSelection(++position)
        }

        binding.letter6.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchNote.selectionStart
            binding.searchNote.setText(
                addLetter(
                    binding.searchNote.text.toString(),
                    binding.letter6.text.toString(),
                    position
                )
            )
            binding.searchNote.setSelection(++position)
        }

        KeyboardVisibilityEvent.setEventListener(
            requireActivity(),
            viewLifecycleOwner,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    binding.letterPanel.isVisible = isOpen
                }
            })

        return binding.root
    }

    private fun addLetter(string: String, letter: String, position: Int): String {
        return string.substring(0, position) + letter + string.substring(position)
    }

    override fun onPause() {
        super.onPause()
        binding.layoutNoNote.noDataImage.pauseAnimation()
    }

    override fun onStop() {
        super.onStop()
        binding.layoutNoNote.noDataImage.pauseAnimation()
    }

    override fun onResume() {
        super.onResume()
        if (noteAdapter.itemCount == 0) {
            binding.layoutNoNote.noDataImage.playAnimation()
        }
    }
}