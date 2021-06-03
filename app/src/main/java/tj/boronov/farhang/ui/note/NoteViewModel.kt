package tj.boronov.farhang.ui.note

import android.util.Log
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
    var pagingSource: PagingSource<Int, Note>? = null

    val flow = Pager(
        PagingConfig(pageSize = 10, enablePlaceholders = false)
    ) {
        Log.d("TAG_TEST", "wft: ")
        App.database.noteDao().searchNote("%${query.value}%").also {
            pagingSource = it
        }
    }.flow.cachedIn(viewModelScope)
}