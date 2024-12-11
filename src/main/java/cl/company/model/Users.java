package cl.company.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Users")
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "No puede ingresar un nombre vacío")
    @NotNull(message = "No puede ingresar un nombre nulo")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "No puede ingresar un apellido vacío")
    @NotNull(message = "No puede ingresar un apellido nulo")
    private String lastName;

    @Column(name = "rut", unique = true)
    @NotBlank(message = "No puede ingresar un RUT vacío")
    @NotNull(message = "No puede ingresar un RUT nulo")
    private String rut;

    @Column(name = "email", unique = true)
    @Email(message = "Debe ingresar un email válido")
    @NotBlank(message = "No puede ingresar un email vacío")
    @NotNull(message = "No puede ingresar un email nulo")
    private String email;

    @Column(name = "phone")
    @NotBlank(message = "No puede ingresar un teléfono vacío")
    @NotNull(message = "No puede ingresar un teléfono nulo")
    private String phone;

    @Column(name = "address")
    @NotBlank(message = "No puede ingresar una dirección vacía")
    @NotNull(message = "No puede ingresar una dirección nula")
    private String address;

    @Column(name = "password")
    private String password;



    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    // Constructor público requerido por JPA
    public Users() {}

    // Constructor privado para el Builder
    private Users(Long id, String firstName, String lastName, String rut, String email, String phone, String address, String password, Rol rol) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rut = rut;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.rol = rol;
    }


    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String rut;
        private String email;
        private String phone;
        private String address;
        private String password;
        private Rol rol;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withRut(String rut) {
            this.rut = rut;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
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
            return new Users(id, firstName, lastName, rut, email, phone, address, password, rol);
        }
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rut='" + rut + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                '}';
    }
}
