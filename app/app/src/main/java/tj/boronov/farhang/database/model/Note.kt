package tj.boronov.farhang.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import tj.boronov.farhang.util.*

@Entity(tableName = DATABASE_TABLE_NOTE)
class Note {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TABLE_NOTE_COLUMN_ID)
    @NotNull
    var id: Int = 0

    @ColumnInfo(name = TABLE_NOTE_COLUMN_NAME)
    @NotNull
    var name: String = ""

    @ColumnInfo(name = TABLE_NOTE_COLUMN_DESCRIPTION)
    @NotNull
    var description: String = ""
}