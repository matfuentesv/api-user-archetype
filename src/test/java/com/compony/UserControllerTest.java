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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

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

//    @Test
//    void createUser_Successful() {
//        Rol role = new Rol.Builder().withNombre("User").withDescription("Regular user role").build();
//        Users user = new Users.Builder()
//                .withFirstName("New")
//                .withLastName("User")
//                .withEmail("newuser@example.com")
//                .withRut("12345678-9")
//                .withPhone("9876543210")
//                .withAddress("New Address")
//                .withRol(role)
//                .build();
//
//        ApiResponse response = new ApiResponse("User created", true);
//
//        when(userService.createUser(user)).thenReturn(response);
//
//        ResponseEntity<Object> result = userController.createUser(user);
//
//        assertNotNull(result);
//        assertEquals(200, result.getStatusCodeValue());
//        assertTrue(((ApiResponse) result.getBody()).isSuccess());
//        verify(userService, times(1)).createUser(user);
//    }

    //    @Test
//    void updateUser_Successful() {
//        Rol role = new Rol.Builder().withNombre("User").withDescription("Regular user role").build();
//        Users user = new Users.Builder()
//                .withFirstName("Existing")
//                .withLastName("User")
//                .withEmail("existinguser@example.com")
//                .withRut("12345678-9")
//                .withPhone("9876543210")
//                .withAddress("Existing Address")
//                .withRol(role)
//                .build();
//
//        ApiResponse response = new ApiResponse("User updated", true);
//
//        when(userService.updateUser(user)).thenReturn(response);
//
//        ResponseEntity<Object> result = userController.updateUser(user);
//
//        assertNotNull(result);
//        assertEquals(200, result.getStatusCodeValue());
//        assertTrue(((ApiResponse) result.getBody()).isSuccess());
//        verify(userService, times(1)).updateUser(user);
//    }
//
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

        // Crear la respuesta esperada
        ApiResponse expectedResponse = new ApiResponse("Usuario eliminado", true);

        // Simular el comportamiento del servicio
        when(userService.findUser(email)).thenReturn(mockUser);
        doReturn(ResponseEntity.ok(expectedResponse)).when(userService).deleteUser(email);

        // Llamar al método del controlador
        ResponseEntity<Object> result = userController.deleteUser(email);

        // Verificaciones
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue()); // Verifica el código HTTP

        // Verificar el cuerpo de la respuesta
        ApiResponse actualResponse = (ApiResponse) result.getBody();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse); // Compara el objeto completo

        // Verifica que los métodos del servicio fueron llamados
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
