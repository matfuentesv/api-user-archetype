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
    private int id;

    @Column(name = "username")
    @NotBlank(message = "No puede ingresar un username vacio")
    @NotNull(message = "No puede ingresar un username nulo")
    private String username;


    @Column(name = "email")
    @NotBlank(message = "No puede email un username vacio")
    @NotNull(message = "No puede email un username nulo")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "No puede email un password vacio")
    @NotNull(message = "No puede email un password nulo")
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;


}
