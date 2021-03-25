package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static utils.FileUtils.readStringFromFile;

public class ReqresPageTest {

    @BeforeAll
    static void setup() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void successesRecourseListTest() {
        given()
                .when()
                .get("/api/unknown")
                .then()
                .log().body()
                .statusCode(200)                
                .body("support.text",
                        is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    void successRegisterWithDataInFileTest() {
        String data = readStringFromFile("./src/test/resources/register_data.txt");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/api/register")
                .then()              
                .log().body()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }

    @Test
    void successCreateUserWithDataInFileTest() {
        String data = readStringFromFile("./src/test/resources/user_data.txt");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/api/users")
                .then()
                .log().body()
                .statusCode(201)                
                .body("id", is(notNullValue()));
    }

    @Test
    void successUpdateUserWithDataInFileTest() {
        String data = readStringFromFile("./src/test/resources/user_data.txt");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .put("/api/users/2")
                .then()
                .log().body()
                .statusCode(200)                
                .body("updatedAt", is(notNullValue()));
    }

    @Test
    void successDeleteUserWithDataInFileTest() {
        given()
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }
}
