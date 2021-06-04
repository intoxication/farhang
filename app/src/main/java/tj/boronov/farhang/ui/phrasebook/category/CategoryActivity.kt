package tj.boronov.farhang.ui.phrasebook.category

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import tj.boronov.farhang.R
import tj.boronov.farhang.adapter.PhrasesAdapter
import tj.boronov.farhang.adapter.ViewPagerFragmentAdapter
import tj.boronov.farhang.data.model.Categories
import tj.boronov.farhang.databinding.ActivityCategoryBinding
import tj.boronov.farhang.dialog.InfoDialog
import tj.boronov.farhang.ui.BaseActivity
import tj.boronov.farhang.util.CATEGORY_AD_SLEEP_TIME
import tj.boronov.farhang.util.vibratePhone

class CategoryActivity : BaseActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var phrasesAdapter: PhrasesAdapter

    private var mInterstitialAd = MutableLiveData<InterstitialAd?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.elevation = 0.0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val baseAdRequest = AdRequest.Builder().build()
        binding.adView.loadAd(baseAdRequest)

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            getString(R.string.category_fullscreen_banner),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd.value = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd.value = interstitialAd
                }
            })

        mInterstitialAd.value?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {}
            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {}
            override fun onAdShowedFullScreenContent() {
                mInterstitialAd.value = null
            }
        }

        mInterstitialAd.observe(this, Observer {
            if (it != null) {
                lifecycleScope.launch {
                    delay(CATEGORY_AD_SLEEP_TIME)
                    withContext(Dispatchers.Main)
                    {
                        it.show(this@CategoryActivity)
                    }
                }
            }
        })

        val category = Categories()
        category.id = intent.getIntExtra("categoryID", 0)
        category.name = intent.getStringExtra("categoryName").toString()
        category.description = intent.getStringExtra("categoryDescription").toString()
        category.image = intent.getStringExtra("categoryImage").toString()

        supportActionBar?.title = category.name

        phrasesAdapter = PhrasesAdapter()
        phrasesAdapter.addLoadStateListener { loadState ->
            showSearchPhrases(
                !(loadState.source.refresh is LoadState.NotLoading
                        && loadState.append.endOfPaginationReached
                        && phrasesAdapter.itemCount < 1)
            )
        }

        binding.searchPhrases.addTextChangedListener {
            viewModel.searchPhrases(it.toString().trim())
        }

        lifecycleScope.launch {
            viewModel.init(category)
            withContext(Dispatchers.Main)
            {
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

        binding.letter1.setOnClickListener {
            vibratePhone(20)
            var position: Int = binding.searchPhrases.selectionStart
            binding.searchPhrases.setText(
                addLetter(
                    binding.searchPhrases.text.toString(),
                    binding.letter1.text.toString(),
                    position
                )
            )
            binding.searchPhrases.setSelection(++position)
        }

        binding.letter2.setOnClickListener {
            vibratePhone(20)
            var position: Int = binding.searchPhrases.selectionStart
            binding.searchPhrases.setText(
                addLetter(
                    binding.searchPhrases.text.toString(),
                    binding.letter2.text.toString(),
                    position
                )
            )
            binding.searchPhrases.setSelection(++position)
        }

        binding.letter3.setOnClickListener {
            vibratePhone(20)
            var position: Int = binding.searchPhrases.selectionStart
            binding.searchPhrases.setText(
                addLetter(
                    binding.searchPhrases.text.toString(),
                    binding.letter3.text.toString(),
                    position
                )
            )
            binding.searchPhrases.setSelection(++position)
        }


        binding.letter4.setOnClickListener {
            vibratePhone(20)
            var position: Int = binding.searchPhrases.selectionStart
            binding.searchPhrases.setText(
                addLetter(
                    binding.searchPhrases.text.toString(),
                    binding.letter4.text.toString(),
                    position
                )
            )
            binding.searchPhrases.setSelection(++position)
        }

        binding.letter5.setOnClickListener {
            vibratePhone(20)
            var position: Int = binding.searchPhrases.selectionStart
            binding.searchPhrases.setText(
                addLetter(
                    binding.searchPhrases.text.toString(),
                    binding.letter5.text.toString(),
                    position
                )
            )
            binding.searchPhrases.setSelection(++position)
        }

        binding.letter6.setOnClickListener {
            vibratePhone(20)
            var position: Int = binding.searchPhrases.selectionStart
            binding.searchPhrases.setText(
                addLetter(
                    binding.searchPhrases.text.toString(),
                    binding.letter6.text.toString(),
                    position
                )
            )
            binding.searchPhrases.setSelection(++position)
        }

        KeyboardVisibilityEvent.setEventListener(
            this,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    binding.letterPanel.isVisible = isOpen
                }
            })
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
                InfoDialog()
                    .show(supportFragmentManager, null)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }

    private fun addLetter(string: String, letter: String, position: Int): String {
        return string.substring(0, position) + letter + string.substring(position)
    }

}