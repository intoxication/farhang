package tj.boronov.farhang.ui.phrasebook

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
import tj.boronov.farhang.adapter.CategoriesAdapter
import tj.boronov.farhang.databinding.FragmentPhrasebookBinding

class PhrasebookFragment : Fragment() {

    lateinit var viewModel: PhrasebookViewModel
    lateinit var binding: FragmentPhrasebookBinding
    lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(PhrasebookViewModel::class.java)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPhrasebookBinding.inflate(inflater, container, false)

        categoriesAdapter = CategoriesAdapter()


        binding.phrasebookList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = categoriesAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest {
                categoriesAdapter.submitData(it)
            }
        }
        return binding.root
    }
}