package com.example.myactivitiestodolistdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myactivitiestodolistdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter = TodoAdapter(mutableListOf())

        binding.rvToDoItems.adapter = todoAdapter

        binding.rvToDoItems.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch (Dispatchers.IO){
            val todos = AppDatabase.getDatabase(applicationContext).todoDao().getAllTodos()

            withContext(Dispatchers.Main){
                todoAdapter.setTodos(todos)
            }
        }

        binding.btnAddToDo.setOnClickListener{

            val todoTitle = binding.etTodoTitle.text.toString()

            if(todoTitle.isNotEmpty()){
                val todo = Todo(null, todoTitle)
                todoAdapter.addTodo(todo)
                binding.etTodoTitle.text.clear()
            }
        }

        binding.btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }

    }
}