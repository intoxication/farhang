package tj.boronov.farhang.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class FarhangDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}