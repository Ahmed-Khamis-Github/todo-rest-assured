package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.UserApi;
import com.qacart.todo.models.User;

public class UserSteps {

    public static User  generateRandomUser(){
      Faker faker = new Faker() ;
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String password = "12345678" ;
        return new User(firstName , lastName , email , password) ;

    }

    public static User getRegisteredUser() {
    User user = generateRandomUser() ;
        UserApi.register(user) ;
        return user ;

    }

    public static String getUserToken() {
        User user = getRegisteredUser() ;
        User loginUser = new User(user.getEmail() , user.getPassword()) ;
       return  UserApi.login(loginUser).body().as(User.class).getAccessToken() ;

    }
}
