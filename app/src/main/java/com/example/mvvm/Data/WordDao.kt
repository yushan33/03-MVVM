package com.example.mvvm.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/* Step 3 - Implement the DAO
    In the DAO (data access object), you specify SQL queries and associate them with method calls.
    The compiler checks the SQL and generates queries from convenience annotations for common queries,
    such as @Insert. Room uses the DAO to create a clean API for your code.
*/

/* Step 4 -  LiveData
*   When data changes you usually want to take some action, such as displaying the updated data in the UI.
*   This means you have to observe the data so that when it changes, you can react.
*
*   LiveData, a lifecycle library class for data observation, solves this problem.
*   Use a return value of type LiveData in your method description,
*   and Room generates all necessary code to update the LiveData when the database is updated.
*/

//DAOs must either be interfaces or abstract classes.
@Dao
interface WordDao {

    //按字母排列
    //@Query("SELECT * from word_table ORDER BY word ASC")
    //fun getAlphabetizedWords(): List<Word>

    //更改為使用LiveData
    //LiveData, a lifecycle library class for data observation, solves this problem.
    // Use a return value of type LiveData in your method description,
    // and Room generates all necessary code to update the LiveData when the database is updated.
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords():LiveData<List<Word>>

    //IGNORE 忽略
    //The selected onConflict strategy ignores a new word if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    //Declares a suspend function to delete all the words.
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}