package dev.ecomarket2.entidades;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private tipoUsuario tipoUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private estadoUsuario estadoUsuario;

    @Column(length = 200)
    private String direccion;

    @Column(length = 50)
    private String ciudad;

    @Column(length = 50)
    private String region;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @Column(name = "intentos_fallidos")
    private Integer intentosFallidos = 0;

    @Column(name = "cuenta_bloqueada")
    private Boolean cuentaBloqueada = false;

    @Column(name = "fecha_bloqueo")
    private LocalDateTime fechaBloqueo;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estadoUsuario == null) {
            estadoUsuario = estadoUsuario.ACTIVO;
        }
        if (tipoUsuario == null) {
            tipoUsuario = tipoUsuario.CLIENTE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
