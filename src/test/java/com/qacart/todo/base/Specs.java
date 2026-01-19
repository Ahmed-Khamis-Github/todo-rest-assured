package com.qacart.todo.base;

import com.qacart.todo.data.Route;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {

    public static RequestSpecification requestSpec() {
        return  given()
                .baseUri(Route.BASE_URL)
                .contentType(io.restassured.http.ContentType.JSON)
                .log().all() ;

    }
}
