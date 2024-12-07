package cl.company.controller;


import cl.company.exception.ApiResponse;
import cl.company.model.Users;
import cl.company.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;

@RestController
@RequestMapping("/api")
@Log
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> login(@RequestHeader String email, @RequestHeader String password) {
        log.log(Level.INFO, "Iniciando sesión para el usuario: {0}", email);

        ApiResponse response = userService.login(email, password);
        if (response.isSuccess()) {
            log.log(Level.INFO, "Usuario {0} inició sesión correctamente", email);
        } else {
            log.log(Level.WARNING, "Fallo de inicio de sesión para el usuario: {0}", email);
        }

         return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/findAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Users>> findAllUsers() {
        log.info("Solicitud recibida para listar todos los usuarios.");
        List<Users> usersList = userService.findAll();
        log.log(Level.INFO, "Se han encontrado {0} usuarios.", usersList.size());
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/findUser/{id}")
    public ResponseEntity<Object> findUser(@PathVariable Long id) {
        log.log(Level.INFO, "Buscando usuario con ID: {0}", id);

        if (StringUtils.containsWhitespace(String.valueOf(id)) || id == null) {
            log.warning("El ID no es válido o está vacío.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("El ID no es válido o está vacío.", false));
        }

        Users user = userService.findUser(id);
        if (user != null) {
            log.log(Level.INFO, "Usuario encontrado: {0}", user.getUsername());
            return ResponseEntity.ok(user);
        } else {
            log.log(Level.WARNING, "Usuario no encontrado con ID: {0}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Usuario no encontrado", false));
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@Valid @RequestBody Users users, BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (users == null) {
            log.info("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron",false));
        }

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Error en los argumentos del método.");
        }

        return ResponseEntity.ok(userService.createUser(users));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody Users users, BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (users == null) {
            log.info("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron",false));
        }

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Error en los argumentos del método.");
        }

        return ResponseEntity.ok(userService.updateUser(users));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        log.log(Level.INFO, "Intentando eliminar usuario con ID: {0}", id);

        if (StringUtils.containsWhitespace(String.valueOf(id)) || id == null) {
            log.warning("El ID no es válido o está vacío.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("El ID no es válido o está vacío.", false));
        }

        Users user = userService.findUser(id);
        if (user != null && user.getUsername() != null) {
            userService.deleteUser(id);
            log.log(Level.INFO, "Usuario con ID: {0} eliminado exitosamente.", id);
            return ResponseEntity.ok(new ApiResponse("Usuario eliminado", true));
        } else {
            log.log(Level.WARNING, "Usuario no encontrado con ID: {0}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Usuario no encontrado", false));
        }
    }
}
