package com.test;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import static io.restassured.RestAssured.given;

public class FilteringResponseData extends TestBaseWithReqAndRespSpecs {
    private String path = "/get";

    @Test
    public void loggingFilter() {
        given()
            .baseUri((String) readProperties("postmanEchoBaseURL"))
//            .filter(new RequestLoggingFilter())
//            .filter(new ResponseLoggingFilter())
//            or to print specific details to console
            .filter(new RequestLoggingFilter(LogDetail.BODY))
            .filter(new ResponseLoggingFilter(LogDetail.STATUS))
//            .log().all()
            .when()
            .get(path)
            .then()
//            .log().all()
            .statusCode(HttpStatus.SC_OK)
        ;
    }
    @Test
    public void loggingFilterPrintingToTheLogFile() {
        PrintStream fileOutputStream;
        try {
            fileOutputStream = new PrintStream(new File("restassured.log"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        given()
            .baseUri((String) readProperties("postmanEchoBaseURL"))
//            .filter(new RequestLoggingFilter(fileOutputStream))
//            .filter(new ResponseLoggingFilter(fileOutputStream))
//            for specific requests
            .filter(new RequestLoggingFilter(LogDetail.BODY, fileOutputStream))
            .filter(new ResponseLoggingFilter(LogDetail.STATUS, fileOutputStream))
//            .log().all()
            .when()
            .get(path)
            .then()
//            .log().all()
            .statusCode(HttpStatus.SC_OK)
        ;
    }
}
