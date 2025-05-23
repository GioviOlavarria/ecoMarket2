package dev.ecomarket2.entidades;

public enum tipoUsuario {
    ADMINISTRADOR("Administrador del sistema"),
    GERENTE_TIENDA("Gerente de tienda"),
    EMPLEADO_VENTAS("Empleado de ventas"),
    LOGISTICA("Personal de logística"),
    CLIENTE("Cliente");

    private final String descripcion;

    tipoUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
