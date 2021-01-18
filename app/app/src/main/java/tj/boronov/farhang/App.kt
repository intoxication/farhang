package tj.boronov.farhang

import android.app.Application
import androidx.room.Room
import tj.boronov.farhang.database.FarhangDatabase
import tj.boronov.farhang.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, FarhangDatabase::class.java, DATABASE_NAME)
            .createFromAsset("database/data.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var database: FarhangDatabase private set
    }
}