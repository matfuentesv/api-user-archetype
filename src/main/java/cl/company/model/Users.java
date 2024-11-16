package cl.company.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotBlank(message = "No puede ingresar un username vacio")
    @NotNull(message = "No puede ingresar un username nulo")
    private String username;

    @Column(name = "email")
    @NotBlank(message = "No puede email un username vacio")
    @NotNull(message = "No puede email un username nulo")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "No puede password un password vacio")
    @NotNull(message = "No puede password un password nulo")
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    // Constructor público requerido por JPA
    public Users() {}

    // Constructor privado para el Builder
    private Users(Long id,String username, String email, String password, Rol rol) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // Builder interno
    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private Rol rol;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withRol(Rol rol) {
            this.rol = rol;
            return this;
        }

        public Users build() {
            return new Users(id,username, email, password, rol);
        }
    }
}
