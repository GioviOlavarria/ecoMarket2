package dev.ecomarket2.entidades;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Data
@Entity
@Table(name = "tabla_usuarios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;


    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String nombreUsuario;

    @NotBlank(message = "El apellido de usuario no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String apellidoUsuario;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(name = "correo_electronico", unique = true, nullable = false)
    private String email;

}
