package com.qacart.todo.testcases;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.data.ErrorMSG;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Epic("User Management")
@Feature("User Authentication")
public class UserTest {

    // post request to register user
    @Test
    @Story("User Registration")
    @Description("Verify that a user can successfully register with valid details")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldBeAbleToRegister() {

        User user = UserSteps.generateRandomUser() ;

        Response response = UserApi.register(user) ;

        User returnedUser = response.body().as(User.class);

        assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName())); ;
        assertThat(response.statusCode() , equalTo(201)); ;
    }


    // post request to register user with existing email

    @Test
    @Story("User Registration")
    @Description("Verify that registration fails when using an existing email")
    @Severity(SeverityLevel.NORMAL)
    public void shouldNotBeAbleToRegisterWithExistingEmail() {

        User user = UserSteps.getRegisteredUser() ;

        Response response = UserApi.register(user) ;

        Error returnedError = response.body().as(Error.class);
        assertThat(returnedError.getMessage() ,  equalTo(ErrorMSG.EMAIL_ALREADY_EXISTS)) ;
        assertThat(response.statusCode() , equalTo(400)) ;

    }



    // post request to login user

    @Test
    @Story("User Login")
    @Description("Verify that a registered user can login successfully with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldBeAbleToLogin() {

        User user = UserSteps.getRegisteredUser() ;
        User loginUser = new User(user.getEmail() , user.getPassword()) ;
        Response response = UserApi.login(loginUser) ;

        User returnedUser = response.body().as(User.class);
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName())) ;
        assertThat(response.statusCode() , equalTo(200)) ;
        assertThat(returnedUser.getAccessToken() , not(equalTo(null))) ;


    }



    // post request to login user with incorrect password

    @Test
    @Story("User Login")
    @Description("Verify that login fails when using incorrect password")
    @Severity(SeverityLevel.NORMAL)
    public void shouldNotBeAbleToLoginIfThePasswordIsNotCorrect() {

        User user = UserSteps.getRegisteredUser() ;
        User loginUser = new User(user.getEmail() , "12544784") ;

        Response response = UserApi.login(loginUser) ;

        Error returnedError = response.body().as(Error.class);
        assertThat(returnedError.getMessage() , equalTo(ErrorMSG.INVALID_CREDENTIALS)) ;

        assertThat(response.statusCode() , equalTo(401)) ;

    }
}