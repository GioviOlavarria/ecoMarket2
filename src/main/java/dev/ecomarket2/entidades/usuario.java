package dev.ecomarket2.entidades;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tabla_usuarios")
public class usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    private String nombreUsuario;

    private String apellidoUsuario;

    @Column(name = "correo_electronico", unique = true, nullable = false)
    private String email;
}
