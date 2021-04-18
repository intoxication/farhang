package tj.boronov.farhang.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import tj.boronov.farhang.App
import tj.boronov.farhang.data.model.Note
import tj.boronov.farhang.data.model.Phrases
import tj.boronov.farhang.data.model.Word

class FavoriteViewModel : ViewModel() {

    private var pagingSourceWord: PagingSource<Int, Word>? = null
    private var pagingSourcePhrases: PagingSource<Int, Phrases>? = null
    private var pagingSourceNote: PagingSource<Int, Note>? = null


    val flowWord = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        App.database.wordDao().getFavorite().also {
            pagingSourceWord = it
        }
    }.flow.cachedIn(viewModelScope)

    val flowPhrases = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        App.database.phrasebookDao().getFavorite().also {
            pagingSourcePhrases = it
        }
    }.flow.cachedIn(viewModelScope)

    val flowNote = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        App.database.noteDao().getFavorite().also {
            pagingSourceNote = it
        }
    }.flow.cachedIn(viewModelScope)
}