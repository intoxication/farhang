package tj.boronov.farhang.ui.phrasebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import tj.boronov.farhang.App
import tj.boronov.farhang.database.Categories
import tj.boronov.farhang.database.Word

class PhrasebookViewModel : ViewModel() {

    private var pagingSource: PagingSource<Int, Categories>? = null

    val flow = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        App.database.phrasebookDao().getCategories().also {
            pagingSource = it
        }
    }.flow.cachedIn(viewModelScope)
}