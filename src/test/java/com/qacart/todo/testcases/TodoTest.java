package com.qacart.todo.testcases;

import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {

    @Test
    public void shouldBeAbleToAddTodo()
    {
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZhMjQ4NzA1Njg3MTdhMDAxNTI2ZWY0NCIsImZpcnN0TmFtZSI6InphaW5hYiIsImxhc3ROYW1lIjoieW91c3NlZiIsImlhdCI6MTc4MDc4MDU0Mn0.GVu8OVFK06ThQyzS9S4mrEEMzZClVx0DZi3fHGzTYzM";
        /*
        String body = "{\n" +
                "    \"isCompleted\": false,\n" +
                "    \"item\": \"learn Appium\"\n" +
                "}";

         */
        Todo todo=new Todo(false,"learn Appium");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when().post("api/v1/tasks")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("item",equalTo("learn Appium"))
                .assertThat().body("isCompleted",equalTo(false));
    }
    @Test
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing()
    {
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZhMjQ4NzA1Njg3MTdhMDAxNTI2ZWY0NCIsImZpcnN0TmFtZSI6InphaW5hYiIsImxhc3ROYW1lIjoieW91c3NlZiIsImlhdCI6MTc4MDc4MDU0Mn0.GVu8OVFK06ThQyzS9S4mrEEMzZClVx0DZi3fHGzTYzM";
       /* String body = "{\n" +
                "    \"item\": \"learn Appium\"\n" +
                "}";

        */
        Todo todo=new Todo("learn Appium");
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when().post("api/v1/tasks")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message",equalTo("\"isCompleted\" is required"));

}
    @Test
    public void shouldBeAbleToGetATodoByID()
    {
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZhMjQ4NzA1Njg3MTdhMDAxNTI2ZWY0NCIsImZpcnN0TmFtZSI6InphaW5hYiIsImxhc3ROYW1lIjoieW91c3NlZiIsImlhdCI6MTc4MDc4MDU0Mn0.GVu8OVFK06ThQyzS9S4mrEEMzZClVx0DZi3fHGzTYzM";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when().get("api/v1/tasks/6a24977b68717a001526f018")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("item",equalTo("learn Appium"))
                .assertThat().body("isCompleted",equalTo(false));
    }
    @Test
    public void shouldBeAbleToDeleteATodoByID()
    {
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZhMjQ4NzA1Njg3MTdhMDAxNTI2ZWY0NCIsImZpcnN0TmFtZSI6InphaW5hYiIsImxhc3ROYW1lIjoieW91c3NlZiIsImlhdCI6MTc4MDc4MDU0Mn0.GVu8OVFK06ThQyzS9S4mrEEMzZClVx0DZi3fHGzTYzM";
        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when().delete("api/v1/tasks/6a24977b68717a001526f018")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("item",equalTo("learn Appium"))
                .assertThat().body("isCompleted",equalTo(false));
    }




}
