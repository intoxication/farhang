package tj.boronov.farhang.ui.phrasebook.singleCategory

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import tj.boronov.farhang.database.model.Categories
import tj.boronov.farhang.databinding.ActivitySingleCategoryBinding

class SingleCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleCategoryBinding
    private lateinit var category: Categories

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0.0f

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        category = Categories()
        category.id = intent.getIntExtra("categoryID", 0)
        category.name = intent.getStringExtra("categoryName").toString()
        category.description = intent.getStringExtra("categoryDescription").toString()
        category.image = intent.getStringExtra("categoryImage").toString()

        Glide.with(this)
            .load(Uri.parse("file:///android_asset/icon/" + category.image))
            .into(binding.categoryIcon)

        binding.categoryName.text = category.name
        binding.categoryDescription.text = category.description
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}