package tj.boronov.farhang.ui.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import tj.boronov.farhang.adapter.WordAdapter
import tj.boronov.farhang.databinding.FragmentWordBinding
import tj.boronov.farhang.dialog.ChooseDirectDialog
import tj.boronov.farhang.interfaces.ChooseDirectListener
import tj.boronov.farhang.ui.BaseActivity
import tj.boronov.farhang.ui.BaseFragment
import tj.boronov.farhang.util.vibratePhone

class WordFragment : BaseFragment(), ChooseDirectListener {

    lateinit var viewModel: WordViewModel
    lateinit var binding: FragmentWordBinding
    lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        retainInstance = true
        wordAdapter = WordAdapter(requireActivity().supportFragmentManager, requireContext())

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.flow.collectLatest {
                wordAdapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWordBinding.inflate(inflater, container, false)

        binding.btnLang.text = viewModel.direct.value?.first

        wordAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && wordAdapter.itemCount < 1) {
                binding.searchWordList.visibility = View.GONE
                binding.layoutNoData.root.visibility = View.VISIBLE
            } else {
                binding.searchWordList.visibility = View.VISIBLE
                binding.layoutNoData.root.visibility = View.GONE
            }
        }

        binding.searchWordList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = wordAdapter
        }

        binding.searchWord.addTextChangedListener {
            if (viewModel.query.value != it.toString().trim()) {
                viewModel.query.value = it.toString().trim()
                viewModel.pagingSource?.invalidate()
            }
        }

        binding.btnLang.setOnClickListener {
            ChooseDirectDialog(this).show(
                (requireActivity() as BaseActivity).supportFragmentManager,
                null
            )
        }

        binding.letter1.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchWord.selectionStart
            binding.searchWord.setText(
                addLetter(
                    binding.searchWord.text.toString(),
                    binding.letter1.text.toString(),
                    position
                )
            )
            binding.searchWord.setSelection(++position)
        }

        binding.letter2.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchWord.selectionStart
            binding.searchWord.setText(
                addLetter(
                    binding.searchWord.text.toString(),
                    binding.letter2.text.toString(),
                    position
                )
            )
            binding.searchWord.setSelection(++position)
        }

        binding.letter3.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchWord.selectionStart
            binding.searchWord.setText(
                addLetter(
                    binding.searchWord.text.toString(),
                    binding.letter3.text.toString(),
                    position
                )
            )
            binding.searchWord.setSelection(++position)
        }


        binding.letter4.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchWord.selectionStart
            binding.searchWord.setText(
                addLetter(
                    binding.searchWord.text.toString(),
                    binding.letter4.text.toString(),
                    position
                )
            )
            binding.searchWord.setSelection(++position)
        }

        binding.letter5.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchWord.selectionStart
            binding.searchWord.setText(
                addLetter(
                    binding.searchWord.text.toString(),
                    binding.letter5.text.toString(),
                    position
                )
            )
            binding.searchWord.setSelection(++position)
        }

        binding.letter6.setOnClickListener {
            requireContext().vibratePhone(20)
            var position: Int = binding.searchWord.selectionStart
            binding.searchWord.setText(
                addLetter(
                    binding.searchWord.text.toString(),
                    binding.letter6.text.toString(),
                    position
                )
            )
            binding.searchWord.setSelection(++position)
        }

        viewModel.direct.observe(viewLifecycleOwner, Observer {
            binding.btnLang.text = it.first
        })

        setEventListener(
            requireActivity(),
            viewLifecycleOwner,
            KeyboardVisibilityEventListener { isOpen -> binding.letterPanel.isVisible = isOpen })

        return binding.root
    }

    private fun addLetter(string: String, letter: String, position: Int): String {
        return string.substring(0, position) + letter + string.substring(position)
    }

    override fun callbackDirect(direct: String, directId: Int) {
        viewModel.direct.value = Pair(direct, directId)
        viewModel.pagingSource?.invalidate()
    }
}