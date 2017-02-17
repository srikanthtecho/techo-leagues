package com.techolution;

import static io.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class HashControllerTests extends BaseApplicationTests {
	@BeforeClass
    public static void setup() {
		BaseApplicationTests.setup();
        RestAssured.basePath = "/TechoHash/";
    }
	
	@Test
	public void completeFlowTest() throws Exception {
		String body = "value";
		//POST test
		given().auth().basic("dinesh", "dinesh").body(body).then().expect().statusCode(201).when()
				.post("/techohashclinet/2");
		
		//GET Test
		given().auth().basic("dinesh", "dinesh").body("value").expect().statusCode(200).when().get("/techohashclinet/2");
		
		//Delete Test
		given().auth().basic("dinesh", "dinesh").expect().statusCode(200).when().delete("/techohashclinet/2");
		
		given().auth().basic("dinesh", "dinesh").expect().statusCode(410).when().delete("/techohashclinet/2");
	}
}
