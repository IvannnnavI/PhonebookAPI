package com.phonebook.httpclient;

import com.google.gson.Gson;
import com.phonebook.dto.AllContactsDto;
import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsHttpclientTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdEBtYWlsZy5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcyMTMyMDQzNCwiaWF0IjoxNzIwNzIwNDM0fQ.hNtDxd84Wi4TmgltdcrxOlIbdraaZd0mxs4TrmqR7xs";

    Gson gson = new Gson();
    @Test
    public void getAllContactsSuccessTest() throws IOException {

        Response response = Request.Get("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", "Bearer " + token)
                .execute();

        HttpResponse httpResponse = response.returnResponse();
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);

        String responseJson = EntityUtils.toString(httpResponse.getEntity());
        AllContactsDto contactsDto = gson.fromJson(responseJson, AllContactsDto.class);
        List<ContactDto> contacts = contactsDto.getContacts();
        for (ContactDto c : contacts) {
            System.out.println(c.getId());
            System.out.println(c.getName());
            System.out.println(c.getPhone());
        }
    }
    @Test
    public void getAllContactWrongTest() throws IOException {
        Response response = Request.Get("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", "Bearer vvv ")
                .execute();

        HttpResponse httpResponse = response.returnResponse();
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(),401);

        String responseJson = EntityUtils.toString(httpResponse.getEntity());
        ErrorDTO errorDTO = gson.fromJson(responseJson, ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(),"Unauthorized");

    }






}
