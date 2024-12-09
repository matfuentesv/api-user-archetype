package com.compony;

import cl.company.exception.ApiResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void shouldCreateApiResponseWithSuccess() {

        String message = "Operation successful";
        boolean success = true;


        ApiResponse apiResponse = new ApiResponse(message, success);


        assertNotNull(apiResponse);
        assertEquals(message, apiResponse.getMessage());
        assertTrue(apiResponse.isSuccess());
    }

    @Test
    void shouldCreateApiResponseWithFailure() {
        // Given
        String message = "Operation failed";
        boolean success = false;

        // When
        ApiResponse apiResponse = new ApiResponse(message, success);

        // Then
        assertNotNull(apiResponse);
        assertEquals(message, apiResponse.getMessage());
        assertFalse(apiResponse.isSuccess());
    }



    @Test
    void shouldTestEqualsAndHashCode() {

        ApiResponse response1 = new ApiResponse("Same message", true);
        ApiResponse response2 = new ApiResponse("Same message", true);
        ApiResponse response3 = new ApiResponse("Different message", false);


        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    void shouldTestToString() {

        ApiResponse apiResponse = new ApiResponse("Test message", true);
        String responseString = apiResponse.toString();
        assertNotNull(responseString);
        assertTrue(responseString.contains("Test message"));
        assertTrue(responseString.contains("true"));
    }
}