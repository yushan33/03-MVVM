package com.example.mvvm.Repository

import androidx.lifecycle.LiveData
import com.example.mvvm.Data.Word
import com.example.mvvm.Data.WordDao
/* Step 6 - Repository
*
*/
class WordRepository(private val wordDao: WordDao) {
    val allWord :LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word){
        wordDao.insert(word)
    }
}