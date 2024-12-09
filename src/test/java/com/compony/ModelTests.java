package com.compony;

import cl.company.model.Rol;
import cl.company.model.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTests {

    @Test
    void testUsersModelBuilder() {

        Rol role = new Rol.Builder()
                .withNombre("Admin")
                .withDescription("Administrator role")
                .build();


        Users user = new Users.Builder()
                .withFirstName("John")
                .withLastName("Doe")
                .withEmail("johndoe@example.com")
                .withRut("12345678-9")
                .withPhone("9876543210")
                .withAddress("123 Main St")
                .withRol(role)
                .build();


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



    @Test
    void testRolModel() {

        Rol role = new Rol.Builder()
                .withNombre("User")
                .withDescription("Regular user role")
                .build();


        assertEquals("User", role.getName());
        assertEquals("Regular user role", role.getDescription());
    }

    @Test
    void testEquals() {
        Rol role1 = new Rol();
        role1.setId(1L);
        role1.setName("Admin");
        role1.setDescription("Administrator role");

        Rol role2 = new Rol();
        role2.setId(1L);
        role2.setName("Admin");
        role2.setDescription("Administrator role");

        Rol role3 = new Rol();
        role3.setId(2L);
        role3.setName("User");
        role3.setDescription("Regular user role");

        assertEquals(role1, role2);
        assertNotEquals(role1, role3);
    }

    @Test
    void testHashCode() {
        Rol role1 = new Rol();
        role1.setId(1L);
        role1.setName("Admin");
        role1.setDescription("Administrator role");

        Rol role2 = new Rol();
        role2.setId(1L);
        role2.setName("Admin");
        role2.setDescription("Administrator role");

        Rol role3 = new Rol();
        role3.setId(2L);
        role3.setName("User");
        role3.setDescription("Regular user role");

        assertEquals(role1.hashCode(), role2.hashCode());
        assertNotEquals(role1.hashCode(), role3.hashCode());
    }

    @Test
    void testToString() {
        Rol role = new Rol();
        role.setId(1L);
        role.setName("Admin");
        role.setDescription("Administrator role");

        String expected = "Rol(id=1, name=Admin, description=Administrator role)";
        assertEquals(expected, role.toString());
    }

    @Test
    void testSettersAndGetters() {
        Rol role = new Rol();

        role.setId(1L);
        role.setDescription("Administrator role");

        assertEquals(1L, role.getId());
        assertEquals("Administrator role", role.getDescription());
    }


}
