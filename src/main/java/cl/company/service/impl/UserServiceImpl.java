package cl.company.service.impl;


import cl.company.exception.ApiResponse;
import cl.company.model.Users;
import cl.company.repository.UserRepository;
import cl.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse login(String username, String password) {
        final boolean existUser = userRepository.findByUserPassword(username,password).isPresent();
        if(existUser){
            return new ApiResponse("Usuario logeado",true);
        }else {
            return new ApiResponse("Usuario no logeado",false);
        }
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findUser(Long id) {
        if(userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        } else {
            return new Users();
        }
    }

    @Override
    public Users createUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public Users updateUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
