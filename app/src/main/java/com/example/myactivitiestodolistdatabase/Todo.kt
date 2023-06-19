package com.example.myactivitiestodolistdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo("title") val title: String?,
    @ColumnInfo("isChecked") var isChecked:Boolean = false
        )