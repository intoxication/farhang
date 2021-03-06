package tj.boronov.farhang.dialog

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import tj.boronov.farhang.BuildConfig
import tj.boronov.farhang.R
import tj.boronov.farhang.databinding.DialogInfoBinding

class MainInfoDialog : DialogFragment() {

    lateinit var binding: DialogInfoBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogInfoBinding.inflate(layoutInflater, container, false)

        //Dismiss dialog
        binding.btnClose.setOnClickListener {
            dismiss()
        }

        // Set version code
        binding.textViewAppVersionCode.text = "${getString(R.string.version)} ${BuildConfig.VERSION_NAME}"

        // Go to Github
        binding.goToGithub.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/boronov")))
        }

        // Go to Telegram
        binding.goToTelegram.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/boronov_11")))
        }

        // Rate app button
        binding.rateApp.setOnClickListener {
            val uri: Uri = Uri.parse("market://details?id=tj.boronov.farhang")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=tj.boronov.farhang")
                    )
                )
            }
        }
        return binding.root
    }
}