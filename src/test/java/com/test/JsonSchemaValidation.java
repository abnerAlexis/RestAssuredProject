package com.test;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidation extends TestBaseWithReqAndRespSpecs {
    private String path = "/get";
    @Test
    public void validateJsonSchema() {
        given()
            .baseUri((String) readProperties("postmanEchoBaseURL"))
            .log().all()
            .when()
            .get(path)
            .then()
            .log().all()
            .statusCode(HttpStatus.SC_OK)
            .body(matchesJsonSchemaInClasspath("echoget.json"))
        ;
    }
}
/* IMPORTANT
echoget.json file is expected schema.
Before running the method clear all required, id and example scopes in echoget.json. Then run the
method.
 */