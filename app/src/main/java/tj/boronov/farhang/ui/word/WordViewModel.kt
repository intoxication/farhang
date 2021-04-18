package tj.boronov.farhang.ui.word

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import tj.boronov.farhang.App
import tj.boronov.farhang.R
import tj.boronov.farhang.data.model.Word

class WordViewModel : ViewModel() {

    var direct =
        MutableLiveData<Pair<String, Int>>(Pair(App.instance.getString(R.string.direct_all), 0))
    var query = MutableLiveData("")

    var pagingSource: PagingSource<Int, Word>? = null

    val flow = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        if (direct.value?.second != 0) {
            App.database.wordDao().getByWord("${query.value}%", direct.value?.second ?: 0)
                .also {
                    pagingSource = it
                }
        } else {
            App.database.wordDao().getByWord("${query.value}%").also {
                pagingSource = it
            }
        }
    }.flow.cachedIn(viewModelScope)

}