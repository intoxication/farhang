package tj.boronov.farhang.ui.favorite.phrases

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
import tj.boronov.farhang.adapter.PhrasesAdapter
import tj.boronov.farhang.databinding.FragmentFavoriteListBinding
import tj.boronov.farhang.ui.BaseFragment
import tj.boronov.farhang.ui.favorite.FavoriteViewModel

class PhrasesFavoriteFragment : BaseFragment() {

    lateinit var viewModel: FavoriteViewModel
    lateinit var binding: FragmentFavoriteListBinding
    lateinit var phrasesAdapter: PhrasesAdapter

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

        phrasesAdapter = PhrasesAdapter()
        phrasesAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && phrasesAdapter.itemCount < 1) {
                binding.list.visibility = View.GONE
                binding.layoutNoDataFavorite.root.visibility = View.VISIBLE
                binding.layoutNoDataFavorite.noInternetImage.playAnimation()
            } else {
                binding.list.visibility = View.VISIBLE
                binding.layoutNoDataFavorite.root.visibility = View.GONE
            }
        }

        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = phrasesAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowPhrases.collectLatest {
                phrasesAdapter.submitData(it)
            }
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        binding.layoutNoDataFavorite.noInternetImage.pauseAnimation()
    }

    override fun onStop() {
        super.onStop()
        binding.layoutNoDataFavorite.noInternetImage.pauseAnimation()
    }

    override fun onResume() {
        super.onResume()
        if (phrasesAdapter.itemCount == 0) {
            binding.layoutNoDataFavorite.noInternetImage.playAnimation()
        }
    }

}