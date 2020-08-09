package com.example.mvvm.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    Step 1 -Create a new Kotlin class file called Word containing the Word data class.
    This class will describe the Entity (which represents the SQLite table) for your words.
    * Each property in the class represents a column in the table.
    * Room will ultimately use these properties to both create the table and instantiate objects from rows in the database.
* */

/*Step 2
Update your Word class with annotations as shown in this code:
 */

@Entity(tableName = "word_table")
class Word(
    @PrimaryKey @ColumnInfo(name = "word") val word:String
)