package com.example.forage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "forageable_table", indices = [Index(value = ["in_season"], unique = true)])
data class Forageable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val address: String,
    @ColumnInfo(name = "in_season")
    val inSeason: Boolean,
    val notes: String?
)