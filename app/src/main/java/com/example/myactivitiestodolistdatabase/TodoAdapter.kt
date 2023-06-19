package com.example.myactivitiestodolistdatabase

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivitiestodolistdatabase.databinding.ItemTodoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoAdapter(
    private val todos: MutableList<Todo>
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val  appDb: AppDatabase = AppDatabase.getDatabase(App.instance)

    inner class TodoViewHolder(private val binding: ItemTodoBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(todo: Todo){
            binding.apply {
                tvTodoTitle.text = todo.title
                cbDone.isChecked = todo.isChecked
                toggleStrikeThrough(tvTodoTitle, todo.isChecked, todo.title)
                cbDone.setOnCheckedChangeListener{_, isChecked->
                    toggleStrikeThrough(tvTodoTitle, isChecked, todo.title)
                    todo.isChecked = isChecked
                }
            }
        }

    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean, title:String?){

        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        GlobalScope.launch {
                appDb.todoDao().update(title, isChecked)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.bind(curTodo)
    }

    fun addTodo(todo: Todo){

        GlobalScope.launch (Dispatchers.IO){
            appDb.todoDao().insert(todo)
        }
        todos.add(todo)
        notifyItemInserted(todos.size - 1)

    }

    fun setTodos(newTodos: List<Todo>) {
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }

    fun deleteDoneTodos(){
        GlobalScope.launch(Dispatchers.IO){
            appDb.todoDao().deleteCheckedTodos()
        }
        todos.removeAll { todo->
            todo.isChecked
        }
        notifyDataSetChanged()
    }


}