package com.example

import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.apache.http.HttpStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

import static io.restassured.RestAssured.given
import static io.restassured.RestAssured.when

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerTest {

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = serverPort;
        RestAssured.filters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()));
    }

    @Test
    void 'api call without auth must fail'(){
        when()
            .get("/main")
        .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void 'api call with authentication must succeed'() {
        given()
            .auth().preemptive().basic("john", "100")
        .when()
            .get("/main")
        .then()
            .statusCode(HttpStatus.SC_OK);
    }

}