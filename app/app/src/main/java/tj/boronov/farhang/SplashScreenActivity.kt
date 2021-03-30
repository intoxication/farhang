package tj.boronov.farhang

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import tj.boronov.farhang.databinding.ActivitySplashScreenBinding
import tj.boronov.farhang.ui.BaseActivity
import tj.boronov.farhang.ui.MainActivity
import tj.boronov.farhang.util.SPLASH_SCREEN_TIME_DELAY

class SplashScreenActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewAppVersionCode.text = "${getString(R.string.version)} ${BuildConfig.VERSION_NAME}"
        val startMainActivityIntent = Intent(this, MainActivity::class.java)
        lifecycleScope.launchWhenResumed {
            delay(SPLASH_SCREEN_TIME_DELAY)
            withContext(Dispatchers.Main)
            {
                startActivity(startMainActivityIntent)
                this@SplashScreenActivity.finish()
            }
        }
    }
}