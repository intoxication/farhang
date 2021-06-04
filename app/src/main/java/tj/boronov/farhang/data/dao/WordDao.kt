package tj.boronov.farhang.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import tj.boronov.farhang.data.model.Word
import tj.boronov.farhang.util.DATABASE_TABLE_WORD
import tj.boronov.farhang.util.TABLE_WORD_COLUMN_DICTIONARY
import tj.boronov.farhang.util.TABLE_WORD_COLUMN_FAVORITE
import tj.boronov.farhang.util.TABLE_WORD_COLUMN_WORD

@Dao
interface WordDao {

    @Query(
        "SELECT * FROM $DATABASE_TABLE_WORD" +
                " WHERE $TABLE_WORD_COLUMN_FAVORITE = 1"
    )
    fun getFavorite(): PagingSource<Int, Word>

    @Query(
        "SELECT * FROM $DATABASE_TABLE_WORD" +
                " WHERE $TABLE_WORD_COLUMN_WORD like :word"
    )
    fun getByWord(word: String): PagingSource<Int, Word>

    @Query(
        "SELECT * FROM $DATABASE_TABLE_WORD" +
                " WHERE $TABLE_WORD_COLUMN_WORD like :word AND $TABLE_WORD_COLUMN_DICTIONARY = :dictionaryID"
    )
    fun getByWord(word: String, dictionaryID: Int): PagingSource<Int, Word>

    @Update
    suspend fun update(word: Word)
}