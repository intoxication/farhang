package tj.boronov.farhang.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import tj.boronov.farhang.database.model.Categories
import tj.boronov.farhang.util.*

@Dao
interface PhrasebookDao {

    @Query("SELECT * FROM $DATABASE_TABLE_CATEGORIES")
    fun getCategories(): PagingSource<Int, Categories>

}