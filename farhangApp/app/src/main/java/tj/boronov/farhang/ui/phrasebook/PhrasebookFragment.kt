package tj.boronov.farhang.ui.phrasebook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.boronov.farhang.adapter.CategoriesAdapter
import tj.boronov.farhang.databinding.FragmentPhrasebookBinding
import tj.boronov.farhang.ui.BaseFragment

class PhrasebookFragment : BaseFragment() {

    lateinit var viewModel: PhrasebookViewModel
    lateinit var binding: FragmentPhrasebookBinding
    lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(PhrasebookViewModel::class.java)
        retainInstance = true
        categoriesAdapter = CategoriesAdapter()

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.flow.collectLatest {
                categoriesAdapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPhrasebookBinding.inflate(inflater, container, false)

        binding.phrasebookList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = categoriesAdapter
        }

        return binding.root
    }
}