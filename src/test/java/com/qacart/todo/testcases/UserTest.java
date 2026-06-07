package com.qacart.todo.testcases;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
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
        User user=new User("zainab","youssef","zeinab123@gmail.com","ywbna123");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/register")
                .then()

                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("firstName",equalTo("zainab"));
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
        User user=new User("zainab","youssef","zeinab123@gmail.com","ywbna123");


        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/register")
                .then()

                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message",equalTo("Email is already exists in the Database"));

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
        User user=new User("zeinab123@gmail.com","ywbna123");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/login")
                .then()

                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("firstName",equalTo("zainab"))
                .assertThat().body("access_token",not(equalTo(null)));

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
        User user=new User("zeinab123@gmail.com","wrongpass123");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/api/v1/users/login")
                .then()

                .log().all()
                .assertThat().statusCode(401)
                .assertThat().body("message",equalTo("The email and password combination is not correct, please fill a correct email and password"));


    }
}
