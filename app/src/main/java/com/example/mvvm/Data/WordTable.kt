package com.example.mvvm.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*Step 2
Update your Word class with annotations as shown in this code:
 */

@Entity(tableName = "word_table")
class WordTable(
    @PrimaryKey @ColumnInfo(name = "word") val word:String
)