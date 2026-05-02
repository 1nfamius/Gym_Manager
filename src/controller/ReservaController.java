package controller;

import model.Actividad;
import model.Reserva;
import service.ActividadService;
import utils.Consola;

import java.util.List;

/**
 * Controlador de la sección de actividades y reservas.
 */
public class ReservaController {

    private final ActividadService actividadService = new ActividadService();

    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            Consola.titulo("Actividades y reservas");
            System.out.println("  1. Ver actividades disponibles");
            System.out.println("  2. Ver mis reservas");
            System.out.println("  3. Hacer una reserva");
            System.out.println("  4. Cancelar una reserva");
            System.out.println("  0. Volver al menú principal");
            Consola.separador();

            int opcion = Consola.leerEntero("  Opción", 0, 4);
            switch (opcion) {
                case 1 -> listarActividades();
                case 2 -> verReservas();
                case 3 -> hacerReserva();
                case 4 -> cancelarReserva();
                case 0 -> salir = true;
            }
        }
    }

    private void listarActividades() {
        Consola.titulo("Actividades disponibles");
        List<Actividad> actividades = actividadService.listarActivas();
        if (actividades.isEmpty()) {
            System.out.println("  No hay actividades disponibles.");
        } else {
            actividades.forEach(a -> System.out.println("  " + a));
        }
        Consola.pausar();
    }

    private void verReservas() {
        Consola.titulo("Mis reservas");
        int idSocio = Consola.leerEntero("  ID del socio");
        List<Reserva> reservas = actividadService.listarReservasSocio(idSocio);
        if (reservas.isEmpty()) {
            System.out.println("  No hay reservas confirmadas para este socio.");
        } else {
            reservas.forEach(r -> System.out.println("  " + r));
        }
        Consola.pausar();
    }

    private void hacerReserva() {
        Consola.titulo("Nueva reserva");
        int idSocio  = Consola.leerEntero("  ID del socio");
        int idSesion = Consola.leerEntero("  ID de la sesión");
        if (actividadService.crearReserva(idSocio, idSesion)) {
            System.out.println("  Reserva realizada correctamente.");
        } else {
            System.out.println("  No se pudo realizar la reserva (sin plazas o ya existe).");
        }
        Consola.pausar();
    }

    private void cancelarReserva() {
        Consola.titulo("Cancelar reserva");
        int idReserva = Consola.leerEntero("  ID de la reserva a cancelar");
        if (actividadService.cancelarReserva(idReserva)) {
            System.out.println("  Reserva cancelada. Plaza devuelta a la sesión.");
        } else {
            System.out.println("  Error al cancelar la reserva.");
        }
        Consola.pausar();
    }
}