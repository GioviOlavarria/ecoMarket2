package dev.ecomarket2.entidades;

public enum estadoUsuario {
    ACTIVO("Usuario Activo"),
    INACTIVO("Usuario Inactivo"),
    SUSPENDIDO("Usuario Suspendido"),
    BLOQUEADO("Usuario Bloqueado"),
    PENDIENTE_VERIFICACION("Pendiente de verificación");

    private final String descripcion;

    estadoUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
