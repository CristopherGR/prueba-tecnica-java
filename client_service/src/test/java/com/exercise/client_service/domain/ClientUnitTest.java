package com.exercise.client_service.domain;

import com.exercise.client_service.domain.enums.PersonGender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientUnitTest {

    @Test
    public void clientCreationTest() {
        Client client = Client.builder()
                .clientId("CL-5487945874154687")
                .password("password")
                .status(true)
                .name("Name")
                .gender(PersonGender.MALE)
                .age(30L)
                .identification("0212548774")
                .address("Address")
                .phone("555-1234")
                .build();

        assertEquals("CL-5487945874154687", client.getClientId());
        assertEquals("password", client.getPassword());
        assertTrue(client.isStatus());
        assertEquals("Name", client.getName());
        assertEquals(PersonGender.MALE, client.getGender());
        assertEquals(30L, client.getAge());
        assertEquals("0212548774", client.getIdentification());
        assertEquals("Address", client.getAddress());
        assertEquals("555-1234", client.getPhone());
    }
}
