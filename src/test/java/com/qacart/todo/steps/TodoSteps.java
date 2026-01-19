package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.Todo;

public class TodoSteps {

    public static Todo generateTodoItem(){
        Faker faker = new Faker() ;

        String item = faker.book(). title() ;
        Boolean isCompleted = false ;
        return new Todo(isCompleted , item) ;
        }


        public static String getTodoID(Todo todo ,String token) {
         return TodoApi.addTodo(token , todo).body().as(Todo.class).getId() ;


        }
}
