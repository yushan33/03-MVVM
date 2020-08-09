package com.example.mvvm.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.Data.Word
import com.example.mvvm.Data.WordRoomDatabase
import com.example.mvvm.Repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 創建了一個名為的類WordViewModel，該class獲取Application作為參數並extends AndroidViewModel。
class WordViewModel(application: Application):AndroidViewModel(application) {

    // 添加了一個私有成員變量來保存對存儲庫的引用。
    private val repository: WordRepository

    //使用LiveData並緩存getAlphabetizedWords返回的內容有幾個好處：
    //我們可以將觀察者放在數據上（而不是輪詢更改），並且僅在數據實際更改時才更新UI。
    //存儲庫通過ViewModel與UI完全隔離。
    val allWords: LiveData<List<Word>>

    //在init中，創建WordRoomDatabase的getDatabase的abstract fun wordDao(): WordDao
    //取得WordRepository的儲存庫的allWords
    //全部單字暫存在allWords中
    init {
        val wordsDao = WordRoomDatabase.getDatabase(application,viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWord
    }

    /**
     * 啟動新協程以非阻塞方式插入數據
     */
    // 創建了一個包裝insert()方法，該方法調用存儲庫的insert()方法。這樣，insert()UI 的實現就被封裝了。
    // 我們不想insert阻塞主線程，因此我們要啟動一個新的協程並調用存儲庫的insert，這是一個暫停函數。
    // 如前所述，ViewModel基於其生命週期具有協程範圍viewModelScope，我們在這裡使用它。
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}