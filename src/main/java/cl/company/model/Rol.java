package cl.company.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "Rol")
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    @NotBlank(message = "No puede ingresar un name de rol vacio")
    @NotNull(message = "No puede ingresar un name de rol nulo")
    private String nombre;

    @Column(name = "description")
    @NotBlank(message = "No puede ingresar una description de rol vacio")
    @NotNull(message = "No puede ingresar una description de rol nulo")
    private String description;



}
