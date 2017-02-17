package com.techolution;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.RestAssured;


public class BasicAuthenticationTest    {

    @Test
    public void basicAuthentication() throws Exception {
        given().auth().basic("dinesh", "dinesh").expect().statusCode(200).when().get("/mappings");
    }

    @Test
    public void basicAuthenticationUsingDefault() throws Exception {
        authentication = basic("dinesh", "dinesh");
        try {
            expect().statusCode(200).when().get("/mappings");
        } finally {
            RestAssured.reset();
        }
    }

    @Test
    public void explicitExcludeOfBasicAuthenticationWhenUsingDefault() throws Exception {
        authentication = basic("dinesh", "wrongpassword");
        try {
            given().auth().none().and().expect().statusCode(401).when().get("/mappings");
        } finally {
            RestAssured.reset();
        }
    }

}
