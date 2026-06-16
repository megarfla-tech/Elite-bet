package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Query("SELECT * FROM analyzed_matches ORDER BY createdAt DESC")
    fun getAllMatchesFlow(): Flow<List<AnalyzedMatch>>

    @Query("SELECT * FROM analyzed_matches WHERE id = :id LIMIT 1")
    suspend fun getMatchById(id: Int): AnalyzedMatch?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: AnalyzedMatch): Long

    @Delete
    suspend fun deleteMatch(match: AnalyzedMatch)

    @Query("DELETE FROM analyzed_matches WHERE isSample = 0")
    suspend fun clearUserMatches()

    @Query("SELECT COUNT(*) FROM analyzed_matches")
    suspend fun getMatchCount(): Int
}
