package tj.boronov.farhang.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Word::class, Categories::class, Subcategory::class, Phrases::class],
    version = 2
)
abstract class FarhangDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun phrasebookDao(): PhrasebookDao
}