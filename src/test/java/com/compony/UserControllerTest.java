package com.compony;

import cl.company.controller.UserController;
import cl.company.exception.ApiResponse;
import cl.company.model.Rol;
import cl.company.model.Users;
import cl.company.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Successful() {
        String email = "matias@gmail.com";
        String password = "Mati@s1952";
        ApiResponse response = new ApiResponse("Login successful", true);

        when(userService.login(email, password)).thenReturn(response);

        ResponseEntity<ApiResponse> result = userController.login(email, password);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().isSuccess());
        verify(userService, times(1)).login(email, password);
    }

    @Test
    void findAllUsers_Successful() {
        Rol role = new Rol.Builder().withNombre("Admin").withDescription("Administrator role").build();
        List<Users> mockUsers = Collections.singletonList(
                new Users.Builder()
                        .withFirstName("Test")
                        .withLastName("User")
                        .withEmail("test@example.com")
                        .withRut("12345678-9")
                        .withPhone("1234567890")
                        .withAddress("Test Address")
                        .withRol(role)
                        .build()
        );

        when(userService.findAll()).thenReturn(mockUsers);

        ResponseEntity<List<Users>> result = userController.findAllUsers();

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(1, result.getBody().size());
        verify(userService, times(1)).findAll();
    }

    @Test
    void findUser_Successful() {
        Rol role = new Rol.Builder().withNombre("User").withDescription("Regular user role").build();
        String email = "matias@gmail.com";
        Users mockUser = new Users.Builder()
                .withFirstName("Test")
                .withLastName("User")
                .withEmail(email)
                .withRut("12345678-9")
                .withPhone("1234567890")
                .withAddress("Test Address")
                .withRol(role)
                .build();

        when(userService.findUser(email)).thenReturn(mockUser);

        ResponseEntity<Object> result = userController.findUser(email);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(mockUser, result.getBody());
        verify(userService, times(1)).findUser(email);
    }

    @Test
    void findUser_NotFound() {
        String email = "matias@gmail.com";

        when(userService.findUser(email)).thenReturn(null);

        ResponseEntity<Object> result = userController.findUser(email);

        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue());
        assertFalse(((ApiResponse) result.getBody()).isSuccess());
        verify(userService, times(1)).findUser(email);
    }

    @Test
    void createUser_Successful() {

        Rol role = new Rol.Builder()
                .withNombre("User")
                .withDescription("Regular user role")
                .build();


        Users user = new Users.Builder()
                .withFirstName("New")
                .withLastName("User")
                .withEmail("newuser@example.com")
                .withRut("12345678-9")
                .withPhone("9876543210")
                .withAddress("New Address")
                .withPassword("securepassword")
                .withRol(role)
                .build();


        when(userService.createUser(user)).thenReturn(user);


        Users result = userController.createUser(user);


        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        verify(userService, times(1)).createUser(user);
    }



    @Test
    void updateUser_Successful() {

        Rol role = new Rol.Builder()
                .withNombre("User")
                .withDescription("Regular user role")
                .build();


        Users existingUser = new Users.Builder()
                .withId(1L)
                .withFirstName("Existing")
                .withLastName("User")
                .withEmail("existinguser@example.com")
                .withRut("12345678-9")
                .withPhone("9876543210")
                .withAddress("Existing Address")
                .withPassword("oldpassword")
                .withRol(role)
                .build();


        Users updatedUser = new Users.Builder()
                .withId(1L)
                .withFirstName("Updated")
                .withLastName("User")
                .withEmail("existinguser@example.com")
                .withRut("12345678-9")
                .withPhone("9876543210")
                .withAddress("Updated Address")
                .withPassword("newpassword")
                .withRol(role)
                .build();


        when(userService.updateUser(existingUser)).thenReturn(updatedUser);


        Users result = userController.updateUser(existingUser);


        assertNotNull(result);
        assertEquals(updatedUser.getId(), result.getId());
        assertEquals(updatedUser.getFirstName(), result.getFirstName());
        assertEquals(updatedUser.getLastName(), result.getLastName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getAddress(), result.getAddress());
        assertEquals(updatedUser.getPassword(), result.getPassword());


        verify(userService, times(1)).updateUser(existingUser);
    }


    @Test
    void deleteUser_Successful() {
        // Crear un rol de ejemplo
        Rol role = new Rol.Builder()
                .withNombre("Admin")
                .withDescription("Rol Admin")
                .build();

        // Crear un usuario de ejemplo
        String email = "matias@gmail.com";
        Users mockUser = new Users.Builder()
                .withFirstName("Matias")
                .withLastName("Fuentes")
                .withEmail(email)
                .withRut("190333973")
                .withPhone("9999999")
                .withAddress("Los naranjos 0228")
                .withRol(role)
                .build();


        ApiResponse expectedResponse = new ApiResponse("Usuario eliminado", true);


        when(userService.findUser(email)).thenReturn(mockUser);
        doReturn(ResponseEntity.ok(expectedResponse)).when(userService).deleteUser(email);


        ResponseEntity<Object> result = userController.deleteUser(email);


        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());


        ApiResponse actualResponse = (ApiResponse) result.getBody();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);


        verify(userService, times(1)).findUser(email);
        verify(userService, times(1)).deleteUser(email);
    }




    @Test
    void deleteUser_NotFound() {
        String email = "nonexistent@example.com";

        when(userService.findUser(email)).thenReturn(null);

        ResponseEntity<Object> result = userController.deleteUser(email);

        assertNotNull(result);
        assertEquals(404, result.getStatusCodeValue());
        assertFalse(((ApiResponse) result.getBody()).isSuccess());
        verify(userService, times(1)).findUser(email);
        verify(userService, never()).deleteUser(email);
    }
}
