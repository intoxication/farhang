package tj.boronov.farhang.ui.favorite.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.boronov.farhang.adapter.WordAdapter
import tj.boronov.farhang.databinding.FragmentFavoriteListBinding
import tj.boronov.farhang.ui.BaseFragment
import tj.boronov.farhang.ui.favorite.FavoriteViewModel

class WordFavoriteFragment : BaseFragment() {

    lateinit var viewModel: FavoriteViewModel
    lateinit var binding: FragmentFavoriteListBinding
    lateinit var wordAdapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteListBinding.inflate(layoutInflater, container, false)

        wordAdapter = WordAdapter(requireActivity().supportFragmentManager, requireContext())
        wordAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && wordAdapter.itemCount < 1) {
                binding.list.visibility = View.GONE
                binding.layoutNoDataFavorite.root.visibility = View.VISIBLE
            } else {
                binding.list.visibility = View.VISIBLE
                binding.layoutNoDataFavorite.root.visibility = View.GONE
            }
        }

        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = wordAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowWord.collectLatest {
                wordAdapter.submitData(it)
            }
        }

        return binding.root
    }
}