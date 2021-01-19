package tj.boronov.farhang.ui.phrasebook.singleCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import tj.boronov.farhang.App
import tj.boronov.farhang.database.model.Phrases

class SubcategoriesViewModel : ViewModel() {

    private var pagingSource: PagingSource<Int, Phrases>? = null

    var categoryID = 0
    var subcategoryID = 0

    val flow = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        App.database.phrasebookDao().getPhrases(categoryID, subcategoryID).also {
            pagingSource = it
        }
    }.flow.cachedIn(viewModelScope)
}