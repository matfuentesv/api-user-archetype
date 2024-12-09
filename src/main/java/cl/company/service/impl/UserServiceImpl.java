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
    public Users createUser(Users users) {

        if (!existsProductByName(users.getEmail())) {
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
           return userRepository.save(userToSave);
        } else {
            return null;
        }

    }

    @Override
    public Users updateUser(Users users) {
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
            return userRepository.save(userToSave);

        }else {
            return null;
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
