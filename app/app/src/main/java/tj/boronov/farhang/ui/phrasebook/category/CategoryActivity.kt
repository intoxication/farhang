package tj.boronov.farhang.ui.phrasebook.category

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import tj.boronov.farhang.R
import tj.boronov.farhang.adapter.PhrasesAdapter
import tj.boronov.farhang.adapter.ViewPagerFragmentAdapter
import tj.boronov.farhang.data.model.Categories
import tj.boronov.farhang.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    lateinit var viewModel: CategoryViewModel
    lateinit var phrasesAdapter: PhrasesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0.0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        phrasesAdapter = PhrasesAdapter()
        phrasesAdapter.addLoadStateListener { loadState ->
            showSearchPhrases(
                !(loadState.source.refresh is LoadState.NotLoading
                        && loadState.append.endOfPaginationReached
                        && phrasesAdapter.itemCount < 1)
            )
        }

        val category = Categories()
        category.id = intent.getIntExtra("categoryID", 0)
        category.name = intent.getStringExtra("categoryName").toString()
        category.description = intent.getStringExtra("categoryDescription").toString()
        category.image = intent.getStringExtra("categoryImage").toString()

        binding.searchPhrases.addTextChangedListener {
            viewModel.searchPhrases(it.toString().trim())
        }

        lifecycleScope.launch {
            viewModel.init(category)
            withContext(Dispatchers.Main)
            {
                Glide.with(this@CategoryActivity)
                    .load(Uri.parse("file:///android_asset/icon/" + viewModel.category.image))
                    .into(binding.categoryIcon)

                binding.categoryName.text = viewModel.category.name
                binding.categoryDescription.text = viewModel.category.description

                binding.viewPager.adapter =
                    ViewPagerFragmentAdapter(
                        viewModel,
                        this@CategoryActivity
                    )

                TabLayoutMediator(binding.tabLayout, binding.viewPager,
                    TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                        tab.text = viewModel.subcategoryList[position].name
                    }
                ).attach()
            }
        }

        binding.phrasesList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = phrasesAdapter
        }
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                phrasesAdapter.submitData(it)
            }
        }
    }

    private fun showSearchPhrases(show: Boolean) {
        if (show) {
            binding.phrasesList.visibility = View.VISIBLE
            binding.tabLayout.visibility = View.INVISIBLE
            binding.viewPager.visibility = View.INVISIBLE
        } else {
            binding.phrasesList.visibility = View.INVISIBLE
            binding.tabLayout.visibility = View.VISIBLE
            binding.viewPager.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> {
                finish()
                return true
            }
            R.id.infoFragment -> {
                InfoDialog().show(supportFragmentManager, "infoDialog")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }
}