package com.phonebook.okhttp;

import com.google.gson.Gson;
import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ContactSuccessDto;
import okhttp3.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

public class AddContactOkhttpTests {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdEBtYWlsZy5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcyMTMyMDQzNCwiaWF0IjoxNzIwNzIwNDM0fQ.hNtDxd84Wi4TmgltdcrxOlIbdraaZd0mxs4TrmqR7xs";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void addContactSuccessTest() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        ContactDto contactDto = ContactDto.builder()
                .name("Marc")
                .lastName("Overmars")
                .email("arsenal@mail.com")
                .phone("29031973000")
                .address("Nederland")
                .description("half-back")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(contactDto), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = client.newCall(request).execute();

        softAssert.assertTrue(response.isSuccessful());
        softAssert.assertEquals(response.code(), 200);

        ContactSuccessDto contactSuccessDto = gson.fromJson(response.body().string(), ContactSuccessDto.class);

        softAssert.assertTrue(contactSuccessDto.getMessage().contains("Contact was added!"));

        softAssert.assertAll();
    }
}