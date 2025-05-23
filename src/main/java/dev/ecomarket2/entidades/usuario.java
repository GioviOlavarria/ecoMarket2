package dev.ecomarket2.entidades;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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


    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min =2, max = 40, message = "El nombre debe tener entre 2 y 40 caracteres")
    @Column(nullable = false, length = 50)
    private String nombreUsuario;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min =2, max = 40, message = "El apellido debe tener entre 2 y 40 caracteres")
    @Column(nullable = false, length = 50)
    private String apellidoUsuario;

    @NotBlank(message = "El correo es obligatorio")
    @Column(name = "correo_electronico", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(nullable = false)
    private String contrasena;

}
