package com.compony;

import cl.company.exception.ApiResponse;
import cl.company.model.Rol;
import cl.company.model.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTests {

    @Test
    void testUsersModel() {
        // Create a Role object
        Rol role = new Rol.Builder()
                .withNombre("Admin")
                .withDescription("Administrator role")
                .build();

        // Create a Users object
        Users user = new Users.Builder()
                .withFirstName("John")
                .withLastName("Doe")
                .withEmail("johndoe@example.com")
                .withRut("12345678-9")
                .withPhone("9876543210")
                .withAddress("123 Main St")
                .withRol(role)
                .build();

        // Assertions for Users
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("12345678-9", user.getRut());
        assertEquals("9876543210", user.getPhone());
        assertEquals("123 Main St", user.getAddress());
        assertEquals(role, user.getRol());
    }

    @Test
    void testRolModel() {
        // Create a Role object
        Rol role = new Rol.Builder()
                .withNombre("User")
                .withDescription("Regular user role")
                .build();

        // Assertions for Role
        assertEquals("User", role.getName());
        assertEquals("Regular user role", role.getDescription());
    }

    @Test
    void testApiResponseModel() {
        // Create an ApiResponse object
        ApiResponse response = new ApiResponse("Success", true);

        // Assertions for ApiResponse
        assertEquals("Success", response.getMessage());
        assertTrue(response.isSuccess());

        // Update ApiResponse
        response.setMessage("Updated Message");
        response.setSuccess(false);

        // Assertions after update
        assertEquals("Updated Message", response.getMessage());
        assertFalse(response.isSuccess());
    }
}
