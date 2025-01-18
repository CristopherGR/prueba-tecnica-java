package com.exercise.client_service.domain;

import com.exercise.client_service.domain.enums.PersonGender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientUnitTest {

    @Test
    public void clientCreationTest() {
        Client client = new Client(
                "CL-5487945874154687",
                "password",
                true,
                "Name",
                PersonGender.MALE,
                30L,
                "1234567890",
                "Address",
                "555-1234"
        );

        assertEquals("CL-5487945874154687", client.getClientId());
        assertEquals("password", client.getPassword());
        assertTrue(client.isStatus());
        assertEquals("Name", client.getName());
        assertEquals(PersonGender.MALE, client.getGender());
        assertEquals(30L, client.getAge());
        assertEquals("1234567890", client.getIdentification());
        assertEquals("Address", client.getAddress());
        assertEquals("555-1234", client.getPhone());
    }
}
