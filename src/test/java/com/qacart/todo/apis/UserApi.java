package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {
        public static Response register(Object user) {
            return given()
                    .spec(Specs.requestSpec())
                    .body(user)
                    .when().post(Route.REGISTER)
                    .then()
                    .log().all()
                    .extract().response();
        }

        public static Response login(Object user) {
            return  given()
                    .spec(Specs.requestSpec())
                    .body(user)
                    .when().post(Route.LOGIN)
                    .then()
                    .log().all()
                    .extract().response();
        }
}
