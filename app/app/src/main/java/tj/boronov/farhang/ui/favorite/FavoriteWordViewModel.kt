package tj.boronov.farhang.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import tj.boronov.farhang.App
import tj.boronov.farhang.data.model.Word

class FavoriteWordViewModel : ViewModel() {

    private var pagingSource: PagingSource<Int, Word>? = null

    val flow = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        App.database.wordDao().getFavorite().also {
            pagingSource = it
        }
    }.flow.cachedIn(viewModelScope)
}