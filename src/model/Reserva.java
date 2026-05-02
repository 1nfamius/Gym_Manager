package model;

import java.time.LocalDateTime;

public class Reserva {

    public enum Estado { CONFIRMADA, CANCELADA, PENDIENTE }

    private int           idReserva;
    private int           idSocio;
    private int           idSesion;
    private LocalDateTime fechaReserva;
    private Estado        estado;

    // Campos auxiliares para mostrar en listados
    private String nombreSocio;
    private String nombreActividad;
    private LocalDateTime fechaHoraSesion;

    public Reserva(int idReserva, int idSocio, int idSesion,
                   LocalDateTime fechaReserva, Estado estado) {
        this.idReserva   = idReserva;
        this.idSocio     = idSocio;
        this.idSesion    = idSesion;
        this.fechaReserva = fechaReserva;
        this.estado      = estado;
    }

    public Reserva(int idSocio, int idSesion) {
        this.idSocio     = idSocio;
        this.idSesion    = idSesion;
        this.fechaReserva = LocalDateTime.now();
        this.estado      = Estado.CONFIRMADA;
    }

    // Getters
    public int           getIdReserva()        { return idReserva; }
    public int           getIdSocio()          { return idSocio; }
    public int           getIdSesion()         { return idSesion; }
    public LocalDateTime getFechaReserva()     { return fechaReserva; }
    public Estado        getEstado()           { return estado; }
    public String        getNombreSocio()      { return nombreSocio; }
    public String        getNombreActividad()  { return nombreActividad; }
    public LocalDateTime getFechaHoraSesion()  { return fechaHoraSesion; }

    // Setters
    public void setEstado(Estado estado)                        { this.estado           = estado; }
    public void setNombreSocio(String nombre)                   { this.nombreSocio      = nombre; }
    public void setNombreActividad(String nombre)               { this.nombreActividad  = nombre; }
    public void setFechaHoraSesion(LocalDateTime fechaHora)     { this.fechaHoraSesion  = fechaHora; }

    @Override
    public String toString() {
        return String.format("[%d] Socio: %s | Actividad: %s | Fecha: %s | Estado: %s",
                idReserva, nombreSocio, nombreActividad, fechaHoraSesion, estado);
    }
}