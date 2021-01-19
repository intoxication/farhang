package tj.boronov.farhang.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import tj.boronov.farhang.data.model.Note
import tj.boronov.farhang.util.DATABASE_TABLE_NOTE

@Dao
interface NoteDao {
    @Query("SELECT * FROM $DATABASE_TABLE_NOTE")
    fun getNote(): PagingSource<Int, Note>
}