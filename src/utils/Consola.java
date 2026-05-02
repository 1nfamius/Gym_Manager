package utils;

import java.util.Scanner;

public class Consola {

    private static final Scanner scanner = new Scanner(System.in);

    private Consola() {}

    public static String leerTexto(String etiqueta) {
        String valor;
        do {
            System.out.print(etiqueta + ": ");
            valor = scanner.nextLine().trim();
            if (valor.isEmpty()) System.out.println("  El campo no puede estar vacío.");
        } while (valor.isEmpty());
        return valor;
    }

    public static String leerTextoOpcional(String etiqueta) {
        System.out.print(etiqueta + " (opcional, Enter para omitir): ");
        return scanner.nextLine().trim();
    }

    public static int leerEntero(String etiqueta, int min, int max) {
        int valor = -1;
        boolean valido = false;
        while (!valido) {
            System.out.print(etiqueta + " [" + min + "-" + max + "]: ");
            String linea = scanner.nextLine().trim();
            try {
                valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                    valido = true;
                } else {
                    System.out.println("  Introduce un número entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("  Eso no es un número válido.");
            }
        }
        return valor;
    }

    public static int leerEntero(String etiqueta) {
        return leerEntero(etiqueta, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static void separador() {
        System.out.println("─".repeat(50));
    }

    public static void titulo(String texto) {
        separador();
        System.out.println("  " + texto.toUpperCase());
        separador();
    }

    public static void pausar() {
        System.out.print("\n  Pulsa Enter para continuar...");
        scanner.nextLine();
    }
}