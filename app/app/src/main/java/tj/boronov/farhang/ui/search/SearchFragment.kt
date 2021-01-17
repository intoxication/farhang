package tj.boronov.farhang.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.boronov.farhang.R
import tj.boronov.farhang.ui.WordAdapter
import tj.boronov.farhang.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    lateinit var viewModel: SearchWordViewModel
    lateinit var binding: FragmentSearchBinding
    lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchWordViewModel::class.java)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.btnLang.text = resources.getStringArray(R.array.lang)[0]

        wordAdapter = WordAdapter()
        wordAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && wordAdapter.itemCount < 1) {
                binding.searchWordList.visibility = View.GONE
                binding.layoutNoData.root.visibility = View.VISIBLE
            } else {
                binding.searchWordList.visibility = View.VISIBLE
                binding.layoutNoData.root.visibility = View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                wordAdapter.submitData(it)
            }
        }

        binding.searchWordList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = wordAdapter
        }

        binding.searchWord.addTextChangedListener {
            viewModel.filterDatabase(it.toString())
        }

        binding.btnLang.setOnClickListener {
            it.isClickable = false

            MaterialAlertDialogBuilder(requireContext())
                .setCancelable(false)
                .setTitle(resources.getString(R.string.choose_lang))
                .setSingleChoiceItems(
                    R.array.lang,
                    viewModel.dictionaryID.toInt()
                )
                { dialog, which ->
                    viewModel.filterDatabase(viewModel.word, which.toString())
                    binding.btnLang.text = resources.getStringArray(R.array.lang)[which]
                    dialog.cancel()
                    it.isClickable = true
                }
                .show()
        }

        binding.letter1.setOnClickListener {
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

        return binding.root
    }

    private fun addLetter(string: String, letter: String, position: Int): String {
        return string.substring(0, position) + letter + string.substring(position)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.btnLang.text =
            resources.getStringArray(R.array.lang)[viewModel.dictionaryID.toInt()]
    }
}