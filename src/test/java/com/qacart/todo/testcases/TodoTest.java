package com.qacart.todo.testcases;


import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Todo Management")
@Feature("Todo CRUD Operations")
public class TodoTest {

    @Test
    @Story("Create Todo")
    @Description("Verify that a user can successfully create a new todo item")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldBeAbleToAddTodo() {
        String token = UserSteps.getUserToken() ;
        Todo todo = TodoSteps.generateTodoItem() ;

        Response response = TodoApi.addTodo(token , todo) ;
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(returnedTodo.getItem() , equalTo(todo.getItem())) ;
        assertThat(returnedTodo.isCompleted() , equalTo(false)) ;
        assertThat(response.statusCode() , equalTo(201)) ;


    }


    @Test
    @Story("Create Todo")
    @Description("Verify that creating a todo fails when isCompleted field is missing")
    @Severity(SeverityLevel.NORMAL)
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing() {

        String token = UserSteps.getUserToken() ;

        Todo todo = new Todo( "Learn Appium");
        Response response = TodoApi.addTodo(token , todo) ;

        Error returnedError = response.body().as(Error.class);
        assertThat(returnedError.getMessage() , equalTo("\"isCompleted\" is required")) ;

        assertThat(response.statusCode() , equalTo(400)) ;



    }




    @Test
    @Story("Read Todo")
    @Description("Verify that a user can retrieve a specific todo item by its ID")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldBeAbleToGetATodoByID() {

        String token = UserSteps.getUserToken() ;
        Todo todo = TodoSteps.generateTodoItem() ;
        String taskID = TodoSteps.getTodoID( todo ,token ) ;


        Response response =    TodoApi.getTodoByID( token , taskID) ;
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(returnedTodo.getItem() , equalTo(todo.getItem())) ;
        assertThat(returnedTodo.isCompleted() , equalTo(false)) ;
        assertThat(response.statusCode() , equalTo(200)) ;


    }


    @Test
    @Story("Delete Todo")
    @Description("Verify that a user can successfully delete an existing todo item")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldBeAbleToDeleteATodo() {
        Todo todo = TodoSteps.generateTodoItem() ;

        String token = UserSteps.getUserToken() ;
        String taskID = TodoSteps.getTodoID( todo ,token ) ;

        Response response =    TodoApi.deleteTodoByID( token , taskID) ;

        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(returnedTodo.getItem() , equalTo(todo.getItem())) ;
        assertThat(returnedTodo.isCompleted() , equalTo(false)) ;
        assertThat(response.statusCode() , equalTo(200)) ;


    }

}