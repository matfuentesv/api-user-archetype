package cl.company.repository;


import cl.company.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    @Query("SELECT u FROM Users u WHERE u.username = :username  AND u.password = :password")
    Optional<Users> findByUserPassword(@Param("username") String username, @Param("password") String password);
}
