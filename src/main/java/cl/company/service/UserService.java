package cl.company.service;



import cl.company.exception.ApiResponse;
import cl.company.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ApiResponse login(String userName, String password);
    List<Users> findAll();
    Users findUser(Long id);
    ResponseEntity<Object> createUser(Users users);
    ResponseEntity<Object> updateUser(Users users);
    boolean existsProductByName(String username);
    boolean existsUserById(Long id);
    ResponseEntity<Object> deleteUser(Long id);
}
