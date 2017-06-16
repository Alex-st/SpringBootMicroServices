package integration;

import com.dto.UserDto;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by alex on 09.07.17.
 */
public class ServiceIntegrationTest {

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            port = "34361";
        }
        RestAssured.port = Integer.valueOf(port);

        String basePath = System.getProperty("server.uri");
        if (basePath == null) {
            basePath = "/client";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://192.168.0.103";
        }
        RestAssured.baseURI = baseHost;
    }

    @Test
    public void successfullGetDefaultUser() {
        given()
            .when()
            .get("/user/0")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .body("id", equalTo(0));
    }

    @Test
    public void successfullySaveUser() {
        UserDto userDto = new UserDto(86, "testCreated user 2");

        given()
            .contentType("application/json")
            .body(userDto)
            .when().post("/user").then()
                .statusCode(HttpStatus.SC_OK);
     }
}
