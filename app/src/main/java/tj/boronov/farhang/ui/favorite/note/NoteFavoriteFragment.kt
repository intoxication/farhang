package tj.boronov.farhang.ui.favorite.note

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
import tj.boronov.farhang.adapter.NoteAdapter
import tj.boronov.farhang.databinding.FragmentFavoriteListBinding
import tj.boronov.farhang.ui.BaseFragment
import tj.boronov.farhang.ui.favorite.FavoriteViewModel

class NoteFavoriteFragment : BaseFragment() {

    lateinit var viewModel: FavoriteViewModel
    lateinit var binding: FragmentFavoriteListBinding
    lateinit var noteAdapter: NoteAdapter

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

        noteAdapter = NoteAdapter(requireActivity().supportFragmentManager)
        noteAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && noteAdapter.itemCount < 1) {
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
            adapter = noteAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flowNote.collectLatest {
                noteAdapter.submitData(it)
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
        if (noteAdapter.itemCount == 0) {
            binding.layoutNoDataFavorite.noInternetImage.playAnimation()
        }
    }
}