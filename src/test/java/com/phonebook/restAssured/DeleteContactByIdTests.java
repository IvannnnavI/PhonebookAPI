package com.phonebook.restAssured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDTO;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdTests extends TestBase {

    String id;

    @BeforeMethod
    public void precondition(){
        ContactDto contactDto = ContactDto.builder()
                .name("Zizu")
                .lastName("Zidane")
                .email("zinadine@mail.com")
                .phone("33333222344")
                .address("Madrid")
                .description("halfback")
                .build();

        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");

        String[] split = message.split(": ");
        id = split[1];

    }
        @Test
        public void deleteContactByIdSuccessTEst(){
//            String message =
                    given()
                    .header(AUTH, TOKEN)
                    .when()
                    .delete("contacts/" + id)
                    .then()
                    .assertThat().statusCode(200)
                    .assertThat().body("message",equalTo("Contact was deleted!"));
//            System.out.println(message);

        }

        @Test
    public void deleteContactByWrongIdTest(){
//            ErrorDTO errorDTO =
                    given()
                    .header(AUTH, TOKEN)
                    .when()
                    .delete("contacts/агоу!")
                    .then()
                    .assertThat().statusCode(400)
                            .assertThat().body("message",containsString("not found in your contacts!"));
//                    .extract().response().as(ErrorDTO.class);

        }
}
