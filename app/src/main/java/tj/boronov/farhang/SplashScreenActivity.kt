package tj.boronov.farhang

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import tj.boronov.farhang.databinding.ActivitySplashScreenBinding
import tj.boronov.farhang.ui.BaseActivity
import tj.boronov.farhang.ui.MainActivity

class SplashScreenActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewAppVersionCode.text =
            "${getString(R.string.version)} ${BuildConfig.VERSION_NAME}"
        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                val startMainActivityIntent =
                    Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(startMainActivityIntent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                this@SplashScreenActivity.finish()
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
    }
}