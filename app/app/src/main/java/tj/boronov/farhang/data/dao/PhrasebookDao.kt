package tj.boronov.farhang.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import tj.boronov.farhang.data.model.Categories
import tj.boronov.farhang.data.model.Phrases
import tj.boronov.farhang.data.model.Subcategory
import tj.boronov.farhang.util.*

@Dao
interface PhrasebookDao {

    @Query("SELECT * FROM $DATABASE_TABLE_CATEGORIES")
    fun getCategories(): PagingSource<Int, Categories>

    @Query(
        "SELECT * FROM $DATABASE_TABLE_SUBCATEGORY " +
                "WHERE $TABLE_SUBCATEGORY_COLUMN_CATEGORY_ID =:categoryID"
    )
    suspend fun getSubcategories(categoryID: Int): List<Subcategory>

    @Query(
        "SELECT * FROM $DATABASE_TABLE_PHRASES " +
                "WHERE $TABLE_PHRASES_COLUMN_CATEGORY_ID =:categoryID AND $TABLE_PHRASES_COLUMN_SUBCATEGORY_ID =:subcategoryID"
    )
    fun getPhrases(categoryID: Int, subcategoryID: Int): PagingSource<Int, Phrases>

}