package com.techolution;

import org.junit.BeforeClass;

import io.restassured.RestAssured;

public class ServiceBrokerApiTests extends BasicAuthenticationTest{
	@BeforeClass
    public static void setup() {
		BaseApplicationTests.setup();
        RestAssured.basePath = "/v2/";
    }
}
