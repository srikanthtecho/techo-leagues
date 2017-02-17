package com.techolution;


import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={HashservicebrokerApplication.class})
@WebAppConfiguration   
public class BaseApplicationTests {
	@Value("${server.port:8080}")   
    int port;
	@BeforeClass
    public static void setup() {
        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/";
        }
        RestAssured.basePath = basePath;
        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;
    }
}
