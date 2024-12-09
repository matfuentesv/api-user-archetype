package com.compony;

import cl.company.model.Rol;
import cl.company.model.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTests {

    @Test
    void testUsersModelBuilder() {
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
    void testUsersModelSettersAndGetters() {
        Users user = new Users();

        user.setId(1L);
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("jane.smith@example.com");
        user.setRut("98765432-1");
        user.setPhone("1234567890");
        user.setAddress("456 Elm St");
        user.setPassword("password123");

        Rol role = new Rol();
        role.setName("User");
        user.setRol(role);

        assertEquals(1L, user.getId());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane.smith@example.com", user.getEmail());
        assertEquals("98765432-1", user.getRut());
        assertEquals("1234567890", user.getPhone());
        assertEquals("456 Elm St", user.getAddress());
        assertEquals("password123", user.getPassword());
        assertEquals(role, user.getRol());
    }

    @Test
    void testEqualsAndHashCode() {
        Users user1 = new Users();
        user1.setFirstName("John");
        user1.setLastName("Doe");

        Users user2 = new Users();
        user2.setFirstName("John");
        user2.setLastName("Doe");

        Users user3 = new Users();
        user3.setFirstName("Jane");
        user3.setLastName("Smith");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

//    @Test
//    void testToString() {
//        Users user = new Users();
//        user.setFirstName("John");
//        user.setLastName("Doe");
//
//        String expected = "Users{firstName='John', lastName='Doe'}"; // Ajustar según implementación
//        assertEquals(expected, user.toString());
//    }

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
}
