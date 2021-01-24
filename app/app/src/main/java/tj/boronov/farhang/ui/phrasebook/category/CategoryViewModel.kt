package tj.boronov.farhang.ui.phrasebook.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import tj.boronov.farhang.App
import tj.boronov.farhang.data.model.Categories
import tj.boronov.farhang.data.model.Phrases
import tj.boronov.farhang.data.model.Subcategory

class CategoryViewModel : ViewModel() {

    lateinit var category: Categories
    lateinit var subcategoryList: List<Subcategory>

    var query = MutableLiveData<String>("")

    private var pagingSource: PagingSource<Int, Phrases>? = null

    suspend fun init(_category: Categories) {
        category = _category
        subcategoryList = App.database.phrasebookDao().getSubcategories(category.id)
    }

    val flow = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        App.database.phrasebookDao().getPhrases(category.id, query.value!!).also {
            pagingSource = it
        }
    }.flow.cachedIn(viewModelScope)

    fun searchPhrases(_phrase: String) {
        query.value = if (_phrase.isEmpty() || _phrase.isBlank()) "" else "%$_phrase%"
        pagingSource?.invalidate()
    }

}