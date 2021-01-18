package tj.boronov.farhang.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import tj.boronov.farhang.util.*

@Entity(tableName = DATABASE_TABLE_CATEGORIES)
class Categories {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TABLE_CATEGORIES_COLUMN_ID)
    @NotNull
    var id: Int = 0

    @ColumnInfo(name = TABLE_CATEGORIES_COLUMN_NAME)
    @NotNull
    var name: String = ""

    @ColumnInfo(name = TABLE_CATEGORIES_COLUMN_DESCRIPTION)
    @NotNull
    var description: String = ""

    @ColumnInfo(name = TABLE_CATEGORIES_COLUMN_IMAGE)
    @NotNull
    var image: String = ""
}