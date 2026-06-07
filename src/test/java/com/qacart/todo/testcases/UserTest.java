package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.data.ErrorMessage;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {


    @Test
    public void shouldBeAbleToRegister()
    {
        //String randomEmail = "hatem" + System.currentTimeMillis() + "@example.com";
      /*  String body ="{\n" +
                "    \"firstName\": \"Hatem\",\n" +
                "    \"lastName\": \"Hatamleh\",\n" +
                "    \"email\": \"" + randomEmail + "\",\n" +
                "    \"password\": \"12345678\"\n" +
                "}";

       */
        //معني ال serialization انها تحول ال java object to JSON
                // String randomEmail = "zainab" + System.currentTimeMillis() + "@gmail.com";
                User user= UserSteps.generateUser();
                 Response response= UserApi.register(user);

                User returnedUser=response.body().as(User.class);

                assertThat(response.statusCode(),equalTo(201));
                assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));
    }

    @Test
    public void shouldNotBeAbleToRegister()
    {
        //String randomEmail = "hatem" + System.currentTimeMillis() + "@example.com";
       /* String body ="{\n" +
                "    \"firstName\": \"Hatem\",\n" +
                "    \"lastName\": \"Hatamleh\",\n" +
                "    \"email\": \"hatem1@example.com\",\n"  +
                "    \"password\": \"12345678\"\n" +
                "}";

        */
        User user=UserSteps.getRegisteredUser();
        Response response= UserApi.register(user);

        Error returnedError=response.body().as(Error.class);
        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessage.EMAIL_ALREADY_REGISTERED));


    }
    @Test
    public void shouldBeAbleToLogin()
    {
        //String randomEmail = "hatem" + System.currentTimeMillis() + "@example.com";
      /*  String body ="{\n" +
                "    \"email\": \"zeinabyoussef987@gmail.com\",\n" +  // نفس الـ email المسجل
                "    \"password\": \"ywbna123\"\n" +           // نفس الـ password
                "}";

       */
        User user=UserSteps.getRegisteredUser();
        User loginData=new User(user.getEmail(),user.getPassword());
        Response response= UserApi.login(loginData);


        User returnedUser=response.body().as(User.class);
        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName()));
        assertThat(returnedUser.getAccess_token(),not(equalTo(null)));


    }
    @Test
    public void shouldNotBeAbleToLogin()
    {
        //String randomEmail = "hatem" + System.currentTimeMillis() + "@example.com";
      /*  String body ="{\n" +
                "    \"email\": \"zeinabyoussef987@gmail.com\",\n" +  // نفس الـ email المسجل
                "    \"password\": \"wrongpass123\"\n" +           // نفس الـ password
                "}";

       */
        User user=UserSteps.getRegisteredUser();
        User loginData=new User(user.getEmail(),"WrongPassword");
        Response response= UserApi.login(loginData);

        Error returnedError=response.body().as(Error.class);
        assertThat(response.statusCode(),equalTo(401));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessage.WRONG_LOGIN));



    }
}
