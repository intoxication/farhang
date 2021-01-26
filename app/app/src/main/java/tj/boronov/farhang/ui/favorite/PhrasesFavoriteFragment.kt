package tj.boronov.farhang.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.boronov.farhang.adapter.PhrasesAdapter
import tj.boronov.farhang.adapter.WordAdapter
import tj.boronov.farhang.databinding.FragmentFavoriteListBinding

class PhrasesFavoriteFragment : Fragment() {

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
}