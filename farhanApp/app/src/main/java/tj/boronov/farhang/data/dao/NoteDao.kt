package tj.boronov.farhang.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import tj.boronov.farhang.data.model.Note
import tj.boronov.farhang.data.model.Word
import tj.boronov.farhang.util.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM $DATABASE_TABLE_NOTE")
    fun getNote(): PagingSource<Int, Note>

    @Query(
        "SELECT * FROM $DATABASE_TABLE_NOTE" +
                " WHERE $TABLE_NOTE_COLUMN_FAVORITE = 1" +
                " ORDER BY $TABLE_NOTE_COLUMN_NAME, $TABLE_NOTE_COLUMN_DESCRIPTION"
    )
    fun getFavorite(): PagingSource<Int, Note>

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM $DATABASE_TABLE_NOTE WHERE $TABLE_NOTE_COLUMN_NAME LIKE :noteName")
    fun searchNote(noteName: String): PagingSource<Int, Note>

}