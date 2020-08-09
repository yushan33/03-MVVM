package com.example.mvvm.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/* Step 5 - Add a Room database
*   Room is a database layer on top of an SQLite database.
*   Room takes care of mundane tasks that you used to handle with an SQLiteOpenHelper.
*/

// @Database註釋參數聲明該數據庫中的實體並設置版本號，每個實體對應一個將在數據庫中創建的表。exportSchema在此處設置為false以避免生成警告。
@Database(entities = arrayOf(Word::class), version =1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase()  {

    private class WordDatebaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase){
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch { populateDatabase(database.wordDao()) }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao){
            // Delete all content here.
            wordDao.deleteAll()

            // Add sample words.
            var word = Word("Hello")
            wordDao.insert(word)
            word = Word("World!")
            wordDao.insert(word)

            word = Word("TODO!")
            wordDao.insert(word)
        }
    }

    abstract fun wordDao(): WordDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null
//        fun getDatabase(context: Context): WordRoomDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    WordRoomDatabase::class.java,
//                    "word_database"
//                ).build()
//                INSTANCE = instance
//                return instance
//            }
//        }

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
               val instance = Room.databaseBuilder(
                   context.applicationContext,
                   WordRoomDatabase::class.java,
                   "word_database"
               ).addCallback(WordDatebaseCallback(scope))
                   .build()
                INSTANCE = instance
                instance
             }
        }
    }
}