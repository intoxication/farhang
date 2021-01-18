package tj.boronov.farhang.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import tj.boronov.farhang.util.*

@Entity(tableName = DATABASE_TABLE_PHRASES)
class Phrases {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TABLE_PHRASES_COLUMN_ID)
    @NotNull
    var id: Int = 0

    @ColumnInfo(name = TABLE_PHRASES_COLUMN_TRANSLATE_RU)
    @NotNull
    var translateRu: String = ""

    @ColumnInfo(name = TABLE_PHRASES_COLUMN_TRANSLATE_TJ)
    @NotNull
    var translateTj: String = ""

    @ColumnInfo(name = TABLE_PHRASES_COLUMN_CATEGORY_ID)
    @NotNull
    var categoryID: Int = 0

    @ColumnInfo(name = TABLE_PHRASES_COLUMN_SUBCATEGORY_ID)
    @NotNull
    var subcategoryID: Int = 0
}