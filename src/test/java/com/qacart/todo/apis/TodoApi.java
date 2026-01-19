package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Route;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {

    public static Response addTodo(String token, Object todo) {
        return given()
                .spec(Specs.requestSpec())
                .body(todo)
                 .auth().oauth2(token)
                .when()
                .post("/api/v1/tasks")
                .then()
                .log().all()
                .extract().response();



    }

    public static Response getTodoByID(String token , String taskID) {
        return  given()
                .spec(Specs.requestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.TODOS + taskID)
                .then()
                .log().all()
                .extract().response();
    }


    public static Response deleteTodoByID(String token , String taskID) {
        return  given()
                .spec(Specs.requestSpec())
                .auth().oauth2(token)
                .when()
                .delete(Route.TODOS + taskID)
                .then()
                .log().all()
                .extract().response();
    }
}
