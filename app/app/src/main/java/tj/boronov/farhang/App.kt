package tj.boronov.farhang

import android.app.Application
import androidx.room.Room
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import tj.boronov.farhang.data.FarhangDatabase
import tj.boronov.farhang.util.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mFirebaseAnalytics = Firebase.analytics

        database = Room.databaseBuilder(this, FarhangDatabase::class.java, DATABASE_NAME)
            .createFromAsset("database/data.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var database: FarhangDatabase private set
        private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    }
}