package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analyzed_matches")
data class AnalyzedMatch(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val homeTeam: String,
    val awayTeam: String,
    val league: String,
    val matchDate: String,
    val homeOdd: Double,
    val drawOdd: Double,
    val awayOdd: Double,
    val rawAnalysisJson: String, // Stringified JSON response from Gemini
    val isSample: Boolean = false, // If true, this is a pre-seeded match for first-load
    val createdAt: Long = System.currentTimeMillis()
)
