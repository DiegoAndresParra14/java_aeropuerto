/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

import Services.SistemaVuelos;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaVuelos sistema = new SistemaVuelos();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n==================================");
            System.out.println("     SISTEMA AEROPUERTO JAVA      ");
            System.out.println("==================================");
            System.out.println("1. Registrar aeropuerto");
            System.out.println("2. Registrar pasajero");
            System.out.println("3. Registrar vuelo");
            System.out.println("4. Listar vuelos");
            System.out.println("5. Buscar vuelos por destino");
            System.out.println("6. Reservar vuelo");
            System.out.println("7. Ver pasajeros de un vuelo");
            System.out.println("8. Cambiar estado de vuelo");
            System.out.println("9. Ver estadísticas");
            System.out.println("10. Consultar rutas (RETO ADICIONAL)");
            System.out.println("11. Salir");
            System.out.print("Seleccione una opción: ");

            if (!scanner.hasNextLine()) break;
            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor ingrese un número válido.");
                continue;
            }

            try {
            switch (opcion) {
                case 1 -> {
                    System.out.print("Código: "); String cod = scanner.nextLine();
                    System.out.print("Nombre: "); String nom = scanner.nextLine();
                    System.out.print("Ciudad: "); String ciu = scanner.nextLine();
                    System.out.print("País: "); String pais = scanner.nextLine();
                    sistema.registrarAeropuerto(cod, nom, ciu, pais);
                }
                case 2 -> {
                    System.out.print("Documento: "); String doc = scanner.nextLine();
                    System.out.print("Nombre: "); String nom = scanner.nextLine();
                    System.out.print("Edad: "); 
                    int edad = Integer.parseInt(scanner.nextLine());
                    sistema.registrarPasajero(doc, nom, edad);
                }
                case 3 -> {
                    System.out.print("Código del vuelo: "); String codV = scanner.nextLine();
                    System.out.print("Código aeropuerto origen: "); String origen = scanner.nextLine();
                    System.out.print("Código aeropuerto destino: "); String destino = scanner.nextLine();
                    System.out.print("Capacidad máxima: "); 
                    int cap = Integer.parseInt(scanner.nextLine());
                    sistema.registrarVuelo(codV, origen, destino, cap);
                }
                case 4 -> sistema.listarVuelos();
                case 5 -> {
                    System.out.print("Ingrese destino: ");
                    String dest = scanner.nextLine();
                    sistema.buscarVuelosPorDestino(dest);
                }
                case 6 -> {
                    System.out.print("Documento del pasajero: "); String docP = scanner.nextLine();
                    System.out.print("Código del vuelo: "); String codV = scanner.nextLine();
                    sistema.reservarVuelo(docP, codV);
                }
                case 7 -> {
                    System.out.print("Código del vuelo: ");
                    sistema.verPasajerosDeVuelo(scanner.nextLine());
                }
                case 8 -> {
                    System.out.print("Código del vuelo: "); String codV = scanner.nextLine();
                    System.out.println("1. PROGRAMADO | 2. ABORDANDO | 3. EN_VUELO | 4. FINALIZADO | 5. CANCELADO");
                    System.out.print("Nuevo estado (número): ");
                    int est = Integer.parseInt(scanner.nextLine());
                    sistema.cambiarEstadoVuelo(codV, est);
                }
                case 9 -> sistema.verEstadisticas();
                case 10 -> { // RETO ADICIONAL
                    System.out.print("Ciudad origen: "); String origen = scanner.nextLine();
                    System.out.print("Ciudad destino: "); String destino = scanner.nextLine();
                    sistema.consultarRutasDisponibles(origen, destino);
                }
                case 11 -> {
                    System.out.println("¡Gracias por utilizar el sistema!");
                    salir = true;
                }
                default -> System.out.println("❌ Opción no válida.");
            }
            } catch (NumberFormatException e) {
                System.out.println("❌ Ingresa un número entero válido; vuelve a intentar la operación.");
            } catch (java.util.NoSuchElementException e) {
                salir = true;
            }
        }
        scanner.close();
    }
}

