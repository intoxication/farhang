package tj.boronov.farhang.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import tj.boronov.farhang.util.*

@Entity(tableName = DATABASE_TABLE_NAME)
class Word {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TABLE_WORD_COLUMN_ID)
    @NotNull
    var id: Int = 0

    @ColumnInfo(name = TABLE_WORD_COLUMN_WORD)
    @NotNull
    var word: String = ""

    @ColumnInfo(name = TABLE_WORD_COLUMN_DEFINITION)
    @NotNull
    var definition: String = ""

    @ColumnInfo(name = TABLE_WORD_COLUMN_DICTIONARY)
    @NotNull
    var dictionaryID: Int = 0

    @ColumnInfo(name = TABLE_WORD_COLUMN_FAVORITE)
    @NotNull
    var favorite: Int = 0
}