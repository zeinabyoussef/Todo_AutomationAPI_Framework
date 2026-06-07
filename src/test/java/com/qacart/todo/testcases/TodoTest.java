package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.data.ErrorMessage;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {

    @Test
    public void shouldBeAbleToAddTodo()
    {
        String token= UserSteps.getUserToken();
        /*
        String body = "{\n" +
                "    \"isCompleted\": false,\n" +
                "    \"item\": \"learn Appium\"\n" +
                "}";

         */
        Todo todo= TodoSteps.generateTodo();

        Response response= TodoApi.addTodo(todo,token);
        Todo returnedTodo=response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedTodo.getItem(),equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(),equalTo(false));


    }
    @Test
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing()
    {
        String token= UserSteps.getUserToken();       /* String body = "{\n" +
                "    \"item\": \"learn Appium\"\n" +
                "}";

        */
        Todo todo=new Todo("learn Appium");
        Response response= TodoApi.addTodo(todo,token);
        //Todo returnedTodo=response.body().as(Todo.class);
        Error returnedError=response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessage.COMPLETED_REQUIRED));


}
    @Test
    public void shouldBeAbleToGetATodoByID()
    {

        String token= UserSteps.getUserToken();

        Response response= TodoApi.getTodo(token);
        Todo returnedTodo=response.body().as(Todo.class);


        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getItem(),equalTo("learn Appium"));
        assertThat(returnedTodo.getIsCompleted(),equalTo(false));


    }
    @Test
    public void shouldBeAbleToDeleteATodoByID() {
        String token= UserSteps.getUserToken();        Response response= TodoApi.deleteTodo(token);

        Todo returnedTodo=response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo("learn Appium"));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));


    }

    }
