package tj.boronov.farhang.ui.info

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import tj.boronov.farhang.R
import tj.boronov.farhang.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Enable BackHome button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set activity title
        supportActionBar?.title = resources.getString(R.string.info)

        // Override Pending Transition
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_pop_exit_anim);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }
}