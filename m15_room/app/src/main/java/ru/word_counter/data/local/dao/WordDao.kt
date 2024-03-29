package ru.word_counter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.word_counter.data.local.entity.Word


@Dao
interface WordDao {

    @Query("SELECT * FROM word LIMIT :limit")
    fun getLimitListWord(limit: Int): Flow<List<Word>>

    @Query("SELECT EXISTS(SELECT * FROM word WHERE word LIKE :word)")
    suspend fun isWordExists(word: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("UPDATE word SET counter = counter + 1 WHERE word LIKE :word")
    suspend fun update(word: String)

    @Query("DELETE FROM word")
    suspend fun deleteAll()
}