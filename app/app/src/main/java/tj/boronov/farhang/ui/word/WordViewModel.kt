package tj.boronov.farhang.ui.word

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import tj.boronov.farhang.App
import tj.boronov.farhang.data.model.Word

class WordViewModel : ViewModel() {

    var dictionaryID = MutableLiveData("0")
    var query = MutableLiveData("")

    private var pagingSource: PagingSource<Int, Word>? = null

    val flow = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        if (dictionaryID.value != "0") {
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