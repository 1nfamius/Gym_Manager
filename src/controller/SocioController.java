package controller;

import model.Socio;
import service.SocioService;
import utils.Consola;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controlador de la sección de gestión de socios.
 * Separa la lógica de presentación (menú/consola) de la lógica de negocio (SocioService).
 */
public class SocioController {

    private final SocioService socioService = new SocioService();

    public void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            Consola.titulo("Gestión de socios");
            System.out.println("  1. Listar socios activos");
            System.out.println("  2. Buscar socio por ID");
            System.out.println("  3. Dar de alta nuevo socio");
            System.out.println("  4. Modificar datos de un socio");
            System.out.println("  5. Dar de baja un socio");
            System.out.println("  0. Volver al menú principal");
            Consola.separador();

            int opcion = Consola.leerEntero("  Opción", 0, 5);
            switch (opcion) {
                case 1 -> listarSocios();
                case 2 -> buscarSocio();
                case 3 -> altaSocio();
                case 4 -> modificarSocio();
                case 5 -> bajaSocio();
                case 0 -> salir = true;
            }
        }
    }

    private void listarSocios() {
        Consola.titulo("Socios activos");
        List<Socio> socios = socioService.listarActivos();
        if (socios.isEmpty()) {
            System.out.println("  No hay socios activos registrados.");
        } else {
            socios.forEach(s -> System.out.println("  " + s));
        }
        Consola.pausar();
    }

    private void buscarSocio() {
        Consola.titulo("Buscar socio");
        int id = Consola.leerEntero("  ID del socio");
        Socio socio = socioService.buscarPorId(id);
        if (socio != null) {
            System.out.println("\n  " + socio);
        } else {
            System.out.println("  No se encontró ningún socio con ID " + id + ".");
        }
        Consola.pausar();
    }

    private void altaSocio() {
        Consola.titulo("Alta de nuevo socio");
        String nombre    = Consola.leerTexto("  Nombre");
        String apellidos = Consola.leerTexto("  Apellidos");
        String email     = Consola.leerTexto("  Email");
        String telefono  = Consola.leerTextoOpcional("  Teléfono");

        LocalDate fechaNac = null;
        String fechaStr = Consola.leerTextoOpcional("  Fecha de nacimiento (AAAA-MM-DD)");
        if (!fechaStr.isEmpty()) {
            try {
                fechaNac = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                System.out.println("  Formato de fecha incorrecto, se omitirá.");
            }
        }

        Socio nuevo = new Socio(nombre, apellidos, email, telefono, fechaNac);
        if (socioService.insertar(nuevo)) {
            System.out.println("\n  Socio dado de alta correctamente.");
        } else {
            System.out.println("\n  Error al dar de alta el socio. Comprueba que el email no esté registrado.");
        }
        Consola.pausar();
    }

    private void modificarSocio() {
        Consola.titulo("Modificar socio");
        int id = Consola.leerEntero("  ID del socio a modificar");
        Socio socio = socioService.buscarPorId(id);
        if (socio == null) {
            System.out.println("  Socio no encontrado.");
            Consola.pausar();
            return;
        }
        System.out.println("  Datos actuales: " + socio);
        System.out.println("  (Deja en blanco para mantener el valor actual)");

        String nombre = Consola.leerTextoOpcional("  Nuevo nombre [" + socio.getNombre() + "]");
        if (!nombre.isEmpty()) socio.setNombre(nombre);

        String apellidos = Consola.leerTextoOpcional("  Nuevos apellidos [" + socio.getApellidos() + "]");
        if (!apellidos.isEmpty()) socio.setApellidos(apellidos);

        String email = Consola.leerTextoOpcional("  Nuevo email [" + socio.getEmail() + "]");
        if (!email.isEmpty()) socio.setEmail(email);

        String telefono = Consola.leerTextoOpcional("  Nuevo teléfono [" + socio.getTelefono() + "]");
        if (!telefono.isEmpty()) socio.setTelefono(telefono);

        if (socioService.actualizar(socio)) {
            System.out.println("\n  Socio actualizado correctamente.");
        } else {
            System.out.println("\n  Error al actualizar el socio.");
        }
        Consola.pausar();
    }

    private void bajaSocio() {
        Consola.titulo("Baja de socio");
        int id = Consola.leerEntero("  ID del socio");
        Socio socio = socioService.buscarPorId(id);
        if (socio == null) {
            System.out.println("  Socio no encontrado.");
            Consola.pausar();
            return;
        }
        System.out.println("  Vas a dar de baja a: " + socio.getNombreCompleto());
        String confirm = Consola.leerTexto("  ¿Confirmar? (s/n)");
        if (confirm.equalsIgnoreCase("s")) {
            if (socioService.darDeBaja(id)) {
                System.out.println("  Socio dado de baja correctamente.");
            } else {
                System.out.println("  Error al dar de baja al socio.");
            }
        } else {
            System.out.println("  Operación cancelada.");
        }
        Consola.pausar();
    }
}