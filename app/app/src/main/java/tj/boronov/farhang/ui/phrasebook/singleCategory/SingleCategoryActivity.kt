package tj.boronov.farhang.ui.phrasebook.singleCategory

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tj.boronov.farhang.R
import tj.boronov.farhang.database.model.Categories
import tj.boronov.farhang.databinding.ActivitySingleCategoryBinding
import tj.boronov.farhang.ui.InfoDialog

class SingleCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleCategoryBinding
    lateinit var viewModel: SingleCategoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SingleCategoryViewModel::class.java)

        supportActionBar?.elevation = 0.0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val category = Categories()
        category.id = intent.getIntExtra("categoryID", 0)
        category.name = intent.getStringExtra("categoryName").toString()
        category.description = intent.getStringExtra("categoryDescription").toString()
        category.image = intent.getStringExtra("categoryImage").toString()

        lifecycleScope.launch {
            viewModel.init(category)
            withContext(Dispatchers.Main)
            {
                Glide.with(this@SingleCategoryActivity)
                    .load(Uri.parse("file:///android_asset/icon/" + viewModel.category.image))
                    .into(binding.categoryIcon)

                binding.categoryName.text = viewModel.category.name
                binding.categoryDescription.text = viewModel.category.description

                binding.viewPager.adapter =
                    ViewPagerFragmentAdapter(viewModel, this@SingleCategoryActivity)

                TabLayoutMediator(binding.tabLayout, binding.viewPager,
                    TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                        tab.text = viewModel.subcategoryList[position].name
                    }
                ).attach()
            }
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