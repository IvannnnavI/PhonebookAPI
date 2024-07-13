package com.phonebook.restAssured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDTO;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class AddContactTests extends TestBase{
    ContactDto contactDto = ContactDto.builder()
            .name("Zizu")
            .lastName("Zidane")
            .email("zinadine@mail.com")
            .phone("33333222344")
            .address("Madrid")
            .description("halfback")
            .build();

    @Test
    public void addContactSuccessTest(){
//        String message =
        given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("Contact was added!"));
//                .extract().path("message");
//
//        System.out.println(message);

        //Contact was added! ID: 0ff5f2b5-f435-489e-b0e6-fb768762b473
    }

    @Test
    public void addContactWithoutName(){
        ContactDto contactDto1 = ContactDto.builder()

                .lastName("Zidane")
                .email("zinadine@mail.com")
                .phone("33333222344")
                .address("Madrid")
                .description("halfback")
                .build();

        ErrorDTO errorDTO = given().header(AUTH, TOKEN)
                .body(contactDto1)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDTO.class);
        Assert.assertTrue(errorDTO.getMessage().toString().contains("name=must not be blank"));

    }

    @Test
    public void addContactWithInvalidPhone(){
        ContactDto contactDto2 = ContactDto.builder()
                .name("Zizu")
                .lastName("Zidane")
                .email("zinadine@mail.com")
                .phone("3344")
                .address("Madrid")
                .description("halfback")
                .build();

//        ErrorDTO errorDTO =
                given()
                .header(AUTH, TOKEN)
                .body(contactDto2)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                        .assertThat().body("message.phone",containsString("Phone number must contain only digits! And length min 10, max 15!"));

//        System.out.println(errorDTO.getMessage());
    }
}
