package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.Todo;
import io.restassured.response.Response;

public class TodoSteps {

    public static Todo generateTodo()
    {
        Faker faker=new Faker();
        String item=faker.book().title();
        boolean isCompleted=false;

        return new Todo(isCompleted,item);

    }

    public static String getTodoID(String token)
    {
        Todo todo=new Todo();
       Response response= TodoApi.addTodo(todo,token);
       return response.body().path("_id");



    }

}
