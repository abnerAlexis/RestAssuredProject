package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class TestBaseWithReqAndRespSpecs {


    protected Object readProperties(Object property) {
        Properties props = new Properties();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("./globalProps.properties");
            props.load(fileInputStream);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props.get(property);
    }

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri((String) readProperties("baseURL"))
            .addHeader("x-api-key", (String) readProperties("API_KEY"))
            .log(LogDetail.ALL)
            .setContentType(ContentType.JSON); //Don't set content type for the get request. You're not sending any request body.
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
            .expectStatusCode(HttpStatus.SC_OK)
            .expectContentType(ContentType.JSON)
            .log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    protected String createWorkSpacePost(String path, Map body) {
        return given()
            .body(body)
            .when()
            .post(path)
            .then()
            .extract()
            .path("workspace.id")
        ;
    }

    protected Response deleteWorkSpace(String workspaceId) {
        return given()
            .when()
            .delete(workspaceId)
            .then()
            .body("workspace.id", is(workspaceId))
            .extract().response()
        ;
    }

    protected void postFileReq(String baseURI, String path) {
        given()
            .baseUri(baseURI)
            .log().all()
            .when()
            .post(path)
            .then()
            .log().all()
            .statusCode(HttpStatus.SC_OK)
        ;
    }
}
