package com.compony;

import cl.company.model.Users;
import cl.company.repository.UserRepository;
import cl.company.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_UserExists_ReturnsSuccessResponse() {
        String email = "test@example.com";
        String password = "password";

        when(userRepository.findByEmailñPassword(email, password)).thenReturn(Optional.of(new Users()));

        var response = userService.login(email, password);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("Usuario logeado", response.getMessage());

        verify(userRepository, times(1)).findByEmailñPassword(email, password);
    }

    @Test
    void login_UserDoesNotExist_ReturnsFailureResponse() {
        String email = "test@example.com";
        String password = "password";

        when(userRepository.findByEmailñPassword(email, password)).thenReturn(Optional.empty());

        var response = userService.login(email, password);

        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("Usuario no logeado", response.getMessage());

        verify(userRepository, times(1)).findByEmailñPassword(email, password);
    }

    @Test
    void createUser_NewUser_ReturnsCreatedUser() {
        Users user = new Users.Builder()
                .withEmail("newuser@example.com")
                .build();

        when(userRepository.findUserName(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(Users.class))).thenReturn(user);

        var createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getEmail(), createdUser.getEmail());

        verify(userRepository, times(1)).findUserName(user.getEmail());
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void createUser_UserAlreadyExists_ReturnsNull() {
        Users user = new Users.Builder()
                .withEmail("existinguser@example.com")
                .build();

        when(userRepository.findUserName(user.getEmail())).thenReturn(Optional.of(user));

        var createdUser = userService.createUser(user);

        assertNull(createdUser);
        verify(userRepository, times(1)).findUserName(user.getEmail());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    void updateUser_UserExists_ReturnsUpdatedUser() {
        Users user = new Users.Builder()
                .withId(1L)
                .withEmail("updateduser@example.com")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);

        var updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals(user.getEmail(), updatedUser.getEmail());

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void updateUser_UserDoesNotExist_ReturnsNull() {
        Users user = new Users.Builder()
                .withId(1L)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        var updatedUser = userService.updateUser(user);

        assertNull(updatedUser);

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    void deleteUser_UserExists_ReturnsSuccessResponse() {
        String email = "user@example.com";
        Users user = new Users.Builder().withId(1L).withEmail(email).build();

        when(userRepository.findUserEmail(email)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(user.getId());

        var response = userService.deleteUser(email);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Eliminación exitosa", response.getBody());

        verify(userRepository, times(1)).findUserEmail(email);
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUser_UserDoesNotExist_ReturnsBadRequest() {
        String email = "nonexistent@example.com";

        when(userRepository.findUserEmail(email)).thenReturn(Optional.empty());

        var response = userService.deleteUser(email);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

        verify(userRepository, times(1)).findUserEmail(email);
        verify(userRepository, never()).deleteById(anyLong());
    }





    @Test
    void testFindAll_ReturnsListOfUsers() {
        Users user1 = new Users.Builder()
                .withId(1L)
                .withEmail("user1@example.com")
                .withFirstName("John")
                .withLastName("Doe")
                .build();

        Users user2 = new Users.Builder()
                .withId(2L)
                .withEmail("user2@example.com")
                .withFirstName("Jane")
                .withLastName("Smith")
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        var users = userService.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testLambdaFindUser() {
        String email = "test@example.com";
        Users user = new Users.Builder()
                .withId(1L)
                .withEmail(email)
                .withFirstName("John")
                .withLastName("Doe")
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user));

        var foundUser = userService.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUser_UserExists() {
        // Datos de entrada
        String email = "test@example.com";
        Users existingUser = new Users.Builder()
                .withId(1L)
                .withFirstName("John")
                .withLastName("Doe")
                .withEmail(email)
                .build();

        // Simulación del repositorio
        when(userRepository.findUserEmail(email)).thenReturn(Optional.of(existingUser));

        // Llamada al servicio
        Users result = userService.findUser(email);

        // Verificaciones
        assertNotNull(result);
        assertEquals(existingUser.getId(), result.getId());
        assertEquals(existingUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findUserEmail(email);
    }

    @Test
    void testFindUser_UserDoesNotExist() {
        // Datos de entrada
        String email = "nonexistent@example.com";

        // Simulación del repositorio
        when(userRepository.findUserEmail(email)).thenReturn(Optional.empty());

        // Llamada al servicio
        Users result = userService.findUser(email);

        // Verificaciones
        assertNotNull(result);
        assertNull(result.getId()); // Comprobar que los valores son predeterminados
        assertNull(result.getEmail());
        verify(userRepository, times(1)).findUserEmail(email);
    }



}
