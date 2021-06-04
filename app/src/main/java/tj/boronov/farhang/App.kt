package tj.boronov.farhang

import android.app.Application
import androidx.room.Room
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import tj.boronov.farhang.data.FarhangDatabase
import tj.boronov.farhang.util.DATABASE_NAME


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this) {}
        mFirebaseAnalytics = Firebase.analytics
        FirebaseMessaging.getInstance().subscribeToTopic("all")
        instance = this
        database = Room.databaseBuilder(this, FarhangDatabase::class.java, DATABASE_NAME)
            .createFromAsset("database/data.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var instance: App
        lateinit var database: FarhangDatabase private set
        private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    }
}