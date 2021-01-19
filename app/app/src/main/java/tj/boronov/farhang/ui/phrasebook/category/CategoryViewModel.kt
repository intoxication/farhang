package tj.boronov.farhang.ui.phrasebook.category

import androidx.lifecycle.ViewModel
import tj.boronov.farhang.App
import tj.boronov.farhang.data.model.Categories
import tj.boronov.farhang.data.model.Subcategory

class CategoryViewModel : ViewModel() {

    lateinit var category: Categories
    lateinit var subcategoryList: List<Subcategory>


    suspend fun init(_category: Categories) {
        category = _category
        subcategoryList = App.database.phrasebookDao().getSubcategories(category.id)
    }

}