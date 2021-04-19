package tj.boronov.farhang.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import tj.boronov.farhang.R
import tj.boronov.farhang.databinding.ActivityMainBinding
import tj.boronov.farhang.dialog.ChangeLanguagesInterfaceFragment
import tj.boronov.farhang.dialog.MainInfoDialog
import tj.boronov.farhang.ui.settings.SettingsActivity
import tj.boronov.farhang.util.setupWithNavController

class MainActivity : BaseActivity() {

    private var currentNavController: LiveData<NavController>? = null
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.infoFragment -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.navView

        val navGraphIds = listOf(
            R.navigation.nav_word,
            R.navigation.nav_favorite,
            R.navigation.nav_phrasebook,
            R.navigation.nav_note
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}