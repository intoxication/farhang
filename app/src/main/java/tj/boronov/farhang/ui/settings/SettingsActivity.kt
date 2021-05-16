package tj.boronov.farhang.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tj.boronov.farhang.databinding.ActivitySettingsBinding
import tj.boronov.farhang.dialog.ChangeLanguagesInterfaceFragment
import tj.boronov.farhang.dialog.MainInfoDialog
import tj.boronov.farhang.ui.MainActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.divRefactorLanguages.setOnClickListener {
            ChangeLanguagesInterfaceFragment().show(supportFragmentManager, "ChangeLanguagesInterface")
        }

        binding.switchDarkThem.setOnClickListener {
//            fun Context.isDarkTheme(): Boolean {
//                return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
//            }
        }

        binding.divNotification.setOnClickListener {  }

        binding.divSeeVersion.setOnClickListener {
            MainInfoDialog().show(supportFragmentManager, "MainInfoDialog")
        }

    }

}