package dev.ecomarket2.repositorio;

import dev.ecomarket2.entidades.usuario;
import dev.ecomarket2.entidades.tipoUsuario;
import dev.ecomarket2.entidades.estadoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface repositorioUsuarios extends JpaRepository<usuario, Long> {

    // Buscar por email (para autenticación)
    Optional<usuario> findByEmail(String email);

    // Verificar si existe un email
    boolean existsByEmail(String email);

    // Buscar por tipo de usuario
    List<usuario> findByTipoUsuario(tipoUsuario tipoUsuario);

    // Buscar por estado
    List<usuario> findByEstado(estadoUsuario estadoUsuario);

    // Buscar por tipo y estado
    List<usuario> findByTipoUsuarioAndEstado(tipoUsuario tipoUsuario, estadoUsuario estadoUsuario);

    // Buscar usuarios activos
    @Query("SELECT u FROM usuario u WHERE u.estadoUsuario = 'ACTIVO'")
    List<usuario> findUsuariosActivos();

    // Buscar por nombre o apellido (case insensitive)
    @Query("SELECT u FROM usuario u WHERE LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(u.apellidoUsuario) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<usuario> findByNombreOrApellidoContainingIgnoreCase(@Param("nombre") String nombre);

    // Buscar usuarios por ciudad
    List<usuario> findByCiudadIgnoreCase(String ciudad);

    // Buscar usuarios por región
    List<usuario> findByRegionIgnoreCase(String region);

    // Buscar usuarios creados en un rango de fechas
    @Query("SELECT u FROM usuario u WHERE u.fechaCreacion BETWEEN :fechaInicio AND :fechaFin")
    List<usuario> findByFechaCreacionBetween(@Param("fechaInicio") LocalDateTime fechaInicio,
                                             @Param("fechaFin") LocalDateTime fechaFin);

    // Buscar usuarios con intentos fallidos
    @Query("SELECT u FROM usuario u WHERE u.intentosFallidos >= :intentos")
    List<usuario> findUsuariosConIntentosFallidos(@Param("intentos") Integer intentos);

    // Búsqueda paginada por tipo de usuario
    Page<usuario> findByTipoUsuario(tipoUsuario tipoUsuario, Pageable pageable);

    // Búsqueda combinada con paginación
    @Query("SELECT u FROM usuario u WHERE " +
            "(:tipoUsuario IS NULL OR u.tipoUsuario = :tipoUsuario) AND " +
            "(:estadoUsuario IS NULL OR u.estadoUsuario = :estadoUsuario) AND " +
            "(:ciudad IS NULL OR LOWER(u.ciudad) LIKE LOWER(CONCAT('%', :ciudad, '%'))) AND " +
            "(:nombre IS NULL OR LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(u.apellidoUsuario) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    Page<usuario> findUsuariosConFiltros(@Param("tipoUsuario") tipoUsuario tipoUsuario,
                                         @Param("estado") estadoUsuario estadoUsuario,
                                         @Param("ciudad") String ciudad,
                                         @Param("nombre") String nombre,
                                         Pageable pageable);

    // Actualizar último acceso
    @Modifying
    @Query("UPDATE usuario u SET u.ultimoAcceso = :fechaAcceso WHERE u.id_usuario = :id_usuario")
    void actualizarUltimoAcceso(@Param("id") Long id, @Param("fechaAcceso") LocalDateTime fechaAcceso);

    // Incrementar intentos fallidos
    @Modifying
    @Query("UPDATE usuario u SET u.intentosFallidos = u.intentosFallidos + 1 WHERE u.id_usuario = :id_usuario")
    void incrementarIntentosFallidos(@Param("id") Long id);

    // Resetear intentos fallidos
    @Modifying
    @Query("UPDATE usuario u SET u.intentosFallidos = 0 WHERE u.id_usuario = :id_usuario")
    void resetearIntentosFallidos(@Param("id") Long id);

    // Bloquear usuario
    @Modifying
    @Query("UPDATE usuario u SET u.cuentaBloqueada = true, u.fechaBloqueo = :fechaBloqueo WHERE u.id_usuario = :id_usuario")
    void bloquearUsuario(@Param("id") Long id, @Param("fechaBloqueo") LocalDateTime fechaBloqueo);

    // Desbloquear usuario
    @Modifying
    @Query("UPDATE usuario u SET u.cuentaBloqueada = false, u.fechaBloqueo = null, u.intentosFallidos = 0 WHERE u.id_usuario = :id_usuario")
    void desbloquearUsuario(@Param("id") Long id);

    // Cambiar estado de usuario
    @Modifying
    @Query("UPDATE usuario u SET u.estadoUsuario = :estadoUsuario WHERE u.id_usuario = :id_usuario")
    void cambiarEstadoUsuario(@Param("id") Long id, @Param("estado") estadoUsuario estadoUsuario);

    // Estadísticas - Contar usuarios por tipo
    @Query("SELECT u.tipoUsuario, COUNT(u) FROM usuario u GROUP BY u.tipoUsuario")
    List<Object[]> contarUsuariosPorTipo();

    // Estadísticas - Contar usuarios por estado
    @Query("SELECT u.estadoUsuario, COUNT(u) FROM usuario u GROUP BY u.estadoUsuario")
    List<Object[]> contarUsuariosPorEstado();

    // Usuarios registrados en el último mes
    @Query("SELECT COUNT(u) FROM usuario u WHERE u.fechaCreacion >= :fechaInicio")
    Long contarUsuariosRegistradosDesde(@Param("fechaInicio") LocalDateTime fechaInicio);
}
