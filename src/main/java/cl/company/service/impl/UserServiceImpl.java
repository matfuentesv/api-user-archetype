package cl.company.service.impl;


import cl.company.exception.ApiResponse;
import cl.company.model.Users;
import cl.company.repository.UserRepository;
import cl.company.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Log
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse login(String email, String password) {
        final boolean existUser = userRepository.findByEmailñPassword(email,password).isPresent();
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
    public Users findUser(String email) {
        Optional<Users> userOptional = userRepository.findUserEmail(email);
        return userOptional.orElseGet(() -> new Users.Builder().build());
    }

    @Override
    public ResponseEntity<Object> createUser(Users users) {

        if(!existsProductByName(users.getEmail())){
            Users userToSave = new Users.Builder()
                                        .withFirstName(users.getFirstName())
                                        .withLastName(users.getLastName())
                                        .withRut(users.getRut())
                                        .withEmail(users.getEmail())
                                        .withPhone(users.getPhone())
                                        .withAddress(users.getAddress())
                                        .withPassword(users.getPassword())
                                        .withRol(users.getRol())
                                        .build();
            Users createdUser = userRepository.save(userToSave);
            return ResponseEntity.ok(createdUser);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("No se puedo crear el usuario,ya existe",false));
        }
    }

    @Override
    public ResponseEntity<Object> updateUser(Users users) {
        if(existsUserById(users.getId())){
            Users userToSave = new Users.Builder()
                                    .withId(users.getId())
                                    .withFirstName(users.getFirstName())
                                    .withLastName(users.getLastName())
                                    .withRut(users.getRut())
                                    .withEmail(users.getEmail())
                                    .withPhone(users.getPhone())
                                    .withAddress(users.getAddress())
                                    .withPassword(users.getPassword())
                                    .withRol(users.getRol())
                                    .build();
            Users createdUser = userRepository.save(userToSave);
            return ResponseEntity.ok(createdUser);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("No se puedo actualizar el usuario,no existe",false));
        }
    }

    @Override
    public boolean existsProductByName(String username) {
        return userRepository.findUserName(username).isPresent();
    }

    @Override
    public boolean existsUserById(Long id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public ResponseEntity<Object> deleteUser(String email) {
        Optional<Users> userOptional = userRepository.findUserEmail(email);
        if(userOptional.isPresent()){
            Users user = userOptional.get();
            userRepository.deleteById(user.getId());
            return ResponseEntity.ok("Eliminación exitosa");
        }else {
            return ResponseEntity.badRequest().build();
        }


    }
}
