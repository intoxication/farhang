package tj.boronov.farhang.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import tj.boronov.farhang.App
import tj.boronov.farhang.database.model.Word

class SearchWordViewModel : ViewModel() {

    var dictionaryID = MutableLiveData<String>("0")
    var query = MutableLiveData<String>("")

    private var pagingSource: PagingSource<Int, Word>? = null

    val flow = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        if (query.value.isNullOrEmpty() || query.value.isNullOrBlank()) {
            // While search word is empty
            App.database.wordDao().getByWord("", -1)
                .also {
                    pagingSource = it
                }
        } else if (dictionaryID.value != "0") {
            App.database.wordDao().getByWord("${query.value}%", dictionaryID.value!!.toInt())
                .also {
                    pagingSource = it
                }
        } else {
            App.database.wordDao().getByWord("${query.value}%").also {
                pagingSource = it
            }
        }
    }.flow.cachedIn(viewModelScope)

    fun filterDatabase(_word: String, _dictionaryID: String) {
        query.value = _word
        dictionaryID.value = _dictionaryID
        pagingSource?.invalidate()
    }
}