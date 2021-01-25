package tj.boronov.farhang.ui.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import tj.boronov.farhang.App
import tj.boronov.farhang.data.model.Note

class NoteViewModel : ViewModel() {

    var query = MutableLiveData("")
    private var pagingSource: PagingSource<Int, Note>? = null

    val flow = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        App.database.noteDao().searchNote("%${query.value}%").also {
            pagingSource = it
        }
    }.flow.cachedIn(viewModelScope)

    fun filterDatabase(_noteTitle: String) {
        query.value = _noteTitle
        pagingSource?.invalidate()
    }
}