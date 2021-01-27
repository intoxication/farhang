package tj.boronov.farhang.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import tj.boronov.farhang.data.model.Word
import tj.boronov.farhang.util.*

@Dao
interface WordDao {

    @Query(
        "SELECT * FROM $DATABASE_TABLE_WORD" +
                " WHERE $TABLE_WORD_COLUMN_FAVORITE = 1" +
                " ORDER BY $TABLE_WORD_COLUMN_WORD, $TABLE_WORD_COLUMN_DICTIONARY"
    )
    fun getFavorite(): PagingSource<Int, Word>

    @Query(
        "SELECT * FROM $DATABASE_TABLE_WORD" +
                " WHERE $TABLE_WORD_COLUMN_WORD like :word" +
                " ORDER BY $TABLE_WORD_COLUMN_WORD"
    )
    fun getByWord(word: String): PagingSource<Int, Word>

    @Query(
        "SELECT * FROM $DATABASE_TABLE_WORD" +
                " WHERE $TABLE_WORD_COLUMN_WORD like :word AND $TABLE_WORD_COLUMN_DICTIONARY = :dictionaryID" +
                " ORDER BY $TABLE_WORD_COLUMN_WORD"
    )
    fun getByWord(word: String, dictionaryID: Int): PagingSource<Int, Word>

    @Update
    suspend fun update(word: Word)
}