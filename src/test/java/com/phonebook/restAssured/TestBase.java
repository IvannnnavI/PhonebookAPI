package com.phonebook.restAssured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdEBtYWlsZy5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcyMTM5OTkxMCwiaWF0IjoxNzIwNzk5OTEwfQ.ssIfOLVoNDCx8LEeBBkqgheBG64lW3VMcaXZ6lHQbj0";

    public static final String AUTH = "Authorization";
    @BeforeMethod
    public void init() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }
}
