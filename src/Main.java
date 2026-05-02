import controller.ReservaController;
import controller.SocioController;
import utils.Consola;
import utils.DBConnection;

public class Main {

    public static void main(String[] args) {

        mostrarBienvenida();

        SocioController   socioController   = new SocioController();
        ReservaController reservaController = new ReservaController();

        boolean salir = false;
        while (!salir) {
            Consola.titulo("GymManager — Menú principal");
            System.out.println("  1. Gestión de socios");
            System.out.println("  2. Actividades y reservas");
            System.out.println("  0. Salir");
            Consola.separador();

            int opcion = Consola.leerEntero("  Opción", 0, 2);
            switch (opcion) {
                case 1 -> socioController.mostrarMenu();
                case 2 -> reservaController.mostrarMenu();
                case 0 -> salir = true;
            }
        }

        System.out.println("\n  Cerrando GymManager...");
        DBConnection.closeConnection();
        System.out.println("  ¡Hasta pronto!\n");
    }

    private static void mostrarBienvenida() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════╗");
        System.out.println("  ║         G Y M M A N A G E R      ║");
        System.out.println("  ║   Sistema de gestión de gimnasio  ║");
        System.out.println("  ╚══════════════════════════════════╝");
        System.out.println();
    }
}