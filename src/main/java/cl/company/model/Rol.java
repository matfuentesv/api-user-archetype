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
    private String name;

    @Column(name = "description")
    @NotBlank(message = "No puede ingresar una description de rol vacio")
    @NotNull(message = "No puede ingresar una description de rol nulo")
    private String description;


    public Rol() {}


    private Rol(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public static class Builder {
        private Long  id;
        private String name;
        private String description;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withNombre(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Rol build() {
            return new Rol(id,name, description);
        }
    }
}
