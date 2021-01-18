package tj.boronov.farhang.database

import androidx.room.Database
import androidx.room.RoomDatabase
import tj.boronov.farhang.database.dao.NoteDao
import tj.boronov.farhang.database.dao.PhrasebookDao
import tj.boronov.farhang.database.dao.WordDao
import tj.boronov.farhang.database.model.*

@Database(
    entities = [Word::class, Categories::class, Subcategory::class, Phrases::class, Note::class],
    version = 2
)
abstract class FarhangDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun phrasebookDao(): PhrasebookDao
    abstract fun noteDao(): NoteDao
}