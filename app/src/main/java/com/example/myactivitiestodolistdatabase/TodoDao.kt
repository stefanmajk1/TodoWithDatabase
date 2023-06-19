package com.example.myactivitiestodolistdatabase

import androidx.room.*

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Query("DELETE FROM todo_table WHERE isChecked = 1")
    suspend fun deleteCheckedTodos()

    @Query("UPDATE todo_table SET isChecked = :checked WHERE title= :tvTitle")
    suspend fun update(tvTitle: String?, checked: Boolean)

    @Query("SELECT * FROM todo_table WHERE id = :todoId")
    suspend fun getTodoById(todoId: Int?): Todo?

    @Query("SELECT * FROM todo_table")
    suspend fun getAllTodos(): List<Todo>
}