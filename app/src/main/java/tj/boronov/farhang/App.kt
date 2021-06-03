package tj.boronov.farhang

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.room.Room
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import tj.boronov.farhang.data.FarhangDatabase
import tj.boronov.farhang.util.DATABASE_NAME


class App : Application() {

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }

        super.onCreate()
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