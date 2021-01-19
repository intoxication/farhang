package tj.boronov.farhang.ui.phrasebook.singleCategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.boronov.farhang.R
import tj.boronov.farhang.adapter.PhrasesAdapter
import tj.boronov.farhang.databinding.FragmentSubcategoriesBinding
import tj.boronov.farhang.ui.favorite.FavoriteWordViewModel

class SubcategoriesFragment : Fragment() {

    lateinit var viewModel: SubcategoriesViewModel
    lateinit var binding: FragmentSubcategoriesBinding
    lateinit var phrasesAdapter: PhrasesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SubcategoriesViewModel::class.java)

        viewModel.categoryID = requireArguments().getInt("categoryID")
        viewModel.subcategoryID = requireArguments().getInt("subcategoryID")

        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubcategoriesBinding.inflate(layoutInflater, container, false)

        phrasesAdapter = PhrasesAdapter()

        binding.phrasesList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = phrasesAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest {
                phrasesAdapter.submitData(it)
            }
        }
        return binding.root
    }
}