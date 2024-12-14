package cl.company.repository;


import cl.company.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    @Query("SELECT u FROM Users u WHERE u.firstName = :name")
    Optional<Users> findUserName(@Param("name") String name);

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Optional<Users> findUserEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE u.email = :email  AND u.password = :password")
    Optional<Users> findByEmailñPassword(@Param("email") String email, @Param("password") String password);
}
