package com.phonebook.restAssured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDTO;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class PutContactsTests extends TestBase{
    String id;
    String idWrong;

    @BeforeMethod
    public void precondition() {
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

        idWrong = id.substring(0, id.length() - 1) + (id.charAt(id.length() - 1) == 'a' ? 'b' : 'a');

    }



    @Test
    public void updateContactDifferentCitySuccessTest(){
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("Zizu")
                .lastName("Zidane")
                .email("zinadine@mail.com")
                .phone("33333222344")
                .address("Paris")
                .description("halfback")
                .build();

        given()
                .header(AUTH, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("Contact was updated"));

    }

    @Test
    public void updateContactUnauthorizedTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("Zizu")
                .lastName("Zidane")
                .email("zinadine@mail.com")
                .phone("33333222344")
                .address("Paris")
                .description("halfback")
                .build();

        given()
                .header("Authorization","token")
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body(containsString("Unauthorized"));
    }
    @Test
    public void updateContactInvalidFormatError() {
        ContactDto invalidContactDto = ContactDto.builder()
                .id(id)
                .name("Zizu")
                .lastName("Zidane")
                .email("zinadine@mail.com")
                .phone("33333222344")
                .address("Paris")
                .description("halfback")
                .build();

        ErrorDTO errorDTO = given()
                .header(AUTH, TOKEN)
                .body(invalidContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDTO.class);

        Assert.assertTrue(errorDTO.getMessage().toString().contains("email=must be a well-formed email address"));
        Assert.assertTrue(errorDTO.getMessage().toString().contains("phone=Phone number must contain only digits! And length min 10, max 15!"));
    }
    @Test
    public void updateContactNotFoundTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(idWrong)
                .name("Zizu")
                .lastName("Zidane")
                .email("zizu@mail.com")
                .phone("33333222344")
                .address("Paris")
                .description("halfback")
                .build();

        given()
                .header(AUTH,TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(404)
                .assertThat().body(containsString("Contact not found"));
    }
    //сервер обрабатывает неверный id контакта(несуществующий контакт), как неверный формат ввода 400

}
