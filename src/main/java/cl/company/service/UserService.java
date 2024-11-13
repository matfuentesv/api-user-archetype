package cl.company.service;



import cl.company.exception.ApiResponse;
import cl.company.model.Users;

import java.util.List;

public interface UserService {

    ApiResponse login(String userName, String password);
    List<Users> findAll();
    Users findUser(Long id);
    Users createUser(Users users);
    Users updateUser(Users users);
    void deleteUser(Long id);
}
