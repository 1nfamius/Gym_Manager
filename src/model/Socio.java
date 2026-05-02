package model;

import java.time.LocalDate;

public class Socio {

    private int       idSocio;
    private String    nombre;
    private String    apellidos;
    private String    email;
    private String    telefono;
    private LocalDate fechaNacimiento;
    private LocalDate fechaAlta;
    private boolean   activo;

    // Constructor completo (para crear desde BD)
    public Socio(int idSocio, String nombre, String apellidos, String email,
                 String telefono, LocalDate fechaNacimiento, LocalDate fechaAlta, boolean activo) {
        this.idSocio         = idSocio;
        this.nombre          = nombre;
        this.apellidos       = apellidos;
        this.email           = email;
        this.telefono        = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta       = fechaAlta;
        this.activo          = activo;
    }

    // Constructor para nuevos socios (sin id, lo asigna la BD)
    public Socio(String nombre, String apellidos, String email, String telefono, LocalDate fechaNacimiento) {
        this.nombre          = nombre;
        this.apellidos       = apellidos;
        this.email           = email;
        this.telefono        = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta       = LocalDate.now();
        this.activo          = true;
    }

    // Getters
    public int       getIdSocio()         { return idSocio; }
    public String    getNombre()          { return nombre; }
    public String    getApellidos()       { return apellidos; }
    public String    getNombreCompleto()  { return nombre + " " + apellidos; }
    public String    getEmail()           { return email; }
    public String    getTelefono()        { return telefono; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public LocalDate getFechaAlta()       { return fechaAlta; }
    public boolean   isActivo()           { return activo; }

    // Setters
    public void setNombre(String nombre)               { this.nombre    = nombre; }
    public void setApellidos(String apellidos)         { this.apellidos = apellidos; }
    public void setEmail(String email)                 { this.email     = email; }
    public void setTelefono(String telefono)           { this.telefono  = telefono; }
    public void setActivo(boolean activo)              { this.activo    = activo; }

    @Override
    public String toString() {
        return String.format("[%d] %s %s | %s | %s",
                idSocio, nombre, apellidos, email, activo ? "ACTIVO" : "BAJA");
    }
}