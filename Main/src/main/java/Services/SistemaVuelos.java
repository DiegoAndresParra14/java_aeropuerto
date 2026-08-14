/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Models.Aeropuerto;
import Models.EstadoVuelo;
import Models.Pasajero;
import Models.Persona;
import Models.Piloto;
import Models.Vuelo;
import exceptions.VueloLlenoException;
import repositories.Repositorio;

import java.util.*;

public class SistemaVuelos {
    // Uso de la clase genérica
    private Repositorio<Aeropuerto> repoAeropuertos = new Repositorio<>();
    private Repositorio<Vuelo> repoVuelos = new Repositorio<>();
    private Repositorio<Persona> repoPersonas = new Repositorio<>(); // Polimorfismo

    public SistemaVuelos() {
        precargarDatos();
    }

    private void precargarDatos() {
        // Aeropuertos
        repoAeropuertos.agregar(new Aeropuerto("BOG", "El Dorado", "Bogotá", "Colombia"));
        repoAeropuertos.agregar(new Aeropuerto("MDE", "José María Córdova", "Medellín", "Colombia"));
        repoAeropuertos.agregar(new Aeropuerto("CLO", "Alfonso Bonilla Aragón", "Cali", "Colombia"));
        repoAeropuertos.agregar(new Aeropuerto("CTG", "Rafael Núñez", "Cartagena", "Colombia"));
        repoAeropuertos.agregar(new Aeropuerto("PSO", "Antonio Nariño", "Pasto", "Colombia"));

        // Personas (Pasajeros y un Piloto para probar polimorfismo)
        repoPersonas.agregar(new Pasajero("1001", "Laura Gómez", 25));
        repoPersonas.agregar(new Pasajero("1002", "Andrés Martínez", 32));
        repoPersonas.agregar(new Pasajero("1003", "Carolina Díaz", 28));
        repoPersonas.agregar(new Pasajero("1004", "Daniel Torres", 41));
        repoPersonas.agregar(new Piloto("9999", "Carlos Rodríguez", "PIL-001", 2500));

        // Vuelos
        repoVuelos.agregar(new Vuelo("AV101", buscarAeropuerto("BOG"), buscarAeropuerto("MDE"), 180));
        repoVuelos.agregar(new Vuelo("AV202", buscarAeropuerto("BOG"), buscarAeropuerto("CTG"), 150));
        repoVuelos.agregar(new Vuelo("AV303", buscarAeropuerto("MDE"), buscarAeropuerto("CLO"), 120));
        repoVuelos.agregar(new Vuelo("AV404", buscarAeropuerto("CLO"), buscarAeropuerto("BOG"), 160));
        repoVuelos.agregar(new Vuelo("AV505", buscarAeropuerto("BOG"), buscarAeropuerto("PSO"), 100));
    }

    public void registrarAeropuerto(String codigo, String nombre, String ciudad, String pais) {
        if (buscarAeropuerto(codigo) != null) {
            System.out.println("❌ Error: Ya existe un aeropuerto con el código " + codigo);
            return;
        }
        repoAeropuertos.agregar(new Aeropuerto(codigo, nombre, ciudad, pais));
        System.out.println("✅ Aeropuerto registrado exitosamente.");
    }

    public void registrarPasajero(String documento, String nombre, int edad) {
        if (buscarPersona(documento) != null) {
            System.out.println("❌ Error: El documento ya está registrado.");
            return;
        }
        if (edad <= 0) {
            System.out.println("❌ Error: La edad debe ser mayor que 0.");
            return;
        }
        repoPersonas.agregar(new Pasajero(documento, nombre, edad));
        System.out.println("✅ Pasajero registrado exitosamente.");
    }

    public void registrarVuelo(String codigo, String codOrigen, String codDestino, int capacidad) {
        if (buscarVuelo(codigo) != null) {
            System.out.println("❌ Error: El código de vuelo ya existe.");
            return;
        }
        Aeropuerto origen = buscarAeropuerto(codOrigen);
        Aeropuerto destino = buscarAeropuerto(codDestino);

        if (origen == null || destino == null) {
            System.out.println("❌ Error: Aeropuerto de origen o destino no encontrado.");
            return;
        }
        if (origen.getCodigo().equals(destino.getCodigo())) {
            System.out.println("❌ Error: El origen y destino deben ser diferentes.");
            return;
        }
        repoVuelos.agregar(new Vuelo(codigo, origen, destino, capacidad));
        System.out.println("✅ Vuelo registrado exitosamente.");
    }

    public void listarVuelos() {
        System.out.println("\n--- LISTA DE VUELOS ---");
        for (Vuelo v : repoVuelos.obtenerTodos()) {
            imprimirInfoVuelo(v);
        }
    }

    public void buscarVuelosPorDestino(String busqueda) {
        System.out.println("\nResultados encontrados para destino: " + busqueda);
        boolean encontrado = false;
        
        // Uso de trim(), toLowerCase() y contains()
        String filtro = busqueda.trim().toLowerCase();

        for (Vuelo v : repoVuelos.obtenerTodos()) {
            if (v.getDestino().getCiudad().toLowerCase().contains(filtro)) {
                System.out.println(v.getCodigo() + " | " + v.getOrigen().getCiudad() + " -> " + v.getDestino().getCiudad());
                System.out.println("Estado: " + v.getEstado());
                System.out.println("--------------------------------");
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No se encontraron vuelos para esa ciudad.");
    }

    public void reservarVuelo(String documento, String codVuelo) {
        Persona persona = buscarPersona(documento);
        Vuelo vuelo = buscarVuelo(codVuelo);

        if (persona == null || !(persona instanceof Pasajero)) {
            System.out.println("❌ Error: Pasajero no encontrado.");
            return;
        }
        if (vuelo == null) {
            System.out.println("❌ Error: Vuelo no encontrado.");
            return;
        }
        if (vuelo.getEstado() != EstadoVuelo.PROGRAMADO) {
            System.out.println("❌ Error: El vuelo no está en estado PROGRAMADO.");
            return;
        }
        
        Pasajero pasajero = (Pasajero) persona;
        if (vuelo.getPasajeros().contains(pasajero)) {
            System.out.println("❌ Error: El pasajero ya tiene una reserva en este vuelo.");
            return;
        }

        try {
            vuelo.reservarAsiento(pasajero);
            System.out.println("✅ Reserva realizada correctamente.");
        } catch (VueloLlenoException e) {
            System.out.println("❌ " + e.getMessage()); // Manejo de la excepción personalizada
        }
    }

    public void verPasajerosDeVuelo(String codVuelo) {
        Vuelo vuelo = buscarVuelo(codVuelo);
        if (vuelo == null) {
            System.out.println("❌ Vuelo no encontrado.");
            return;
        }
        System.out.println("\nPasajeros del vuelo " + vuelo.getCodigo() + ":");
        for (Pasajero p : vuelo.getPasajeros()) {
            System.out.println(p.obtenerDescripcion()); // Uso de Polimorfismo
        }
        if (vuelo.getPasajeros().isEmpty()) System.out.println("No hay pasajeros registrados.");
    }

    public void cambiarEstadoVuelo(String codVuelo, int opcionEstado) {
        Vuelo vuelo = buscarVuelo(codVuelo);
        if (vuelo == null) {
            System.out.println("❌ Vuelo no encontrado.");
            return;
        }
        
        EstadoVuelo nuevoEstado = switch (opcionEstado) {
            case 1 -> EstadoVuelo.PROGRAMADO;
            case 2 -> EstadoVuelo.ABORDANDO;
            case 3 -> EstadoVuelo.EN_VUELO;
            case 4 -> EstadoVuelo.FINALIZADO;
            case 5 -> EstadoVuelo.CANCELADO;
            default -> null;
        };

        if (nuevoEstado != null) {
            vuelo.setEstado(nuevoEstado);
            System.out.println("✅ Estado actualizado a: " + nuevoEstado);
        } else {
            System.out.println("❌ Opción de estado inválida.");
        }
    }

    public void verEstadisticas() {
        System.out.println("\n========= ESTADÍSTICAS =========");
        System.out.println("Aeropuertos registrados: " + repoAeropuertos.obtenerTodos().size());
        System.out.println("Vuelos registrados: " + repoVuelos.obtenerTodos().size());
        System.out.println("Personas en sistema: " + repoPersonas.obtenerTodos().size());
        
        int programados = 0, enVuelo = 0, finalizados = 0, cancelados = 0, reservasTotal = 0;
        Vuelo vueloMayorOcupacion = null;
        
        Set<String> destinos = new HashSet<>(); // Uso de SET
        Map<String, Integer> vuelosPorDestino = new HashMap<>(); // Uso de MAP

        for (Vuelo v : repoVuelos.obtenerTodos()) {
            // Conteo de estados
            switch (v.getEstado()) {
                case PROGRAMADO -> programados++;
                case EN_VUELO -> enVuelo++;
                case FINALIZADO -> finalizados++;
                case CANCELADO -> cancelados++;
            }
            
            reservasTotal += v.getPasajeros().size();
            
            // Cálculo de mayor ocupación
            if (vueloMayorOcupacion == null || v.getPasajeros().size() > vueloMayorOcupacion.getPasajeros().size()) {
                vueloMayorOcupacion = v;
            }

            // Alimentar Set y Map
            String ciudadDestino = v.getDestino().getCiudad();
            destinos.add(ciudadDestino);
            vuelosPorDestino.put(ciudadDestino, vuelosPorDestino.getOrDefault(ciudadDestino, 0) + 1);
        }

        System.out.println("Vuelos programados: " + programados);
        System.out.println("Vuelos en vuelo: " + enVuelo);
        System.out.println("Vuelos finalizados: " + finalizados);
        System.out.println("Vuelos cancelados: " + cancelados);
        System.out.println("Total reservas realizadas: " + reservasTotal);
        
        if (vueloMayorOcupacion != null) {
            System.out.println("Vuelo con mayor ocupación: " + vueloMayorOcupacion.getCodigo() + " - " + 
                    vueloMayorOcupacion.getOrigen().getCiudad() + " -> " + vueloMayorOcupacion.getDestino().getCiudad() + 
                    " (" + vueloMayorOcupacion.getPasajeros().size() + "/" + vueloMayorOcupacion.getCapacidad() + " pasajeros)");
        }

        System.out.println("\n=== DESTINOS DISPONIBLES (SET) ===");
        for (String d : destinos) {
            System.out.println("- " + d);
        }

        System.out.println("\n=== VUELOS POR DESTINO (MAP) ===");
        for (Map.Entry<String, Integer> entry : vuelosPorDestino.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // RETO ADICIONAL
    public void consultarRutasDisponibles(String origen, String destino) {
        System.out.println("\n--- Vuelos " + origen + " -> " + destino + " ---");
        boolean encontrado = false;
        for (Vuelo v : repoVuelos.obtenerTodos()) {
            if (v.getOrigen().getCiudad().equalsIgnoreCase(origen) && v.getDestino().getCiudad().equalsIgnoreCase(destino)) {
                int disponibles = v.getCapacidad() - v.getPasajeros().size();
                System.out.println(v.getCodigo());
                System.out.println("Estado: " + v.getEstado());
                System.out.println("Disponibles: " + disponibles + " asientos");
                System.out.printf("Ocupación: %d/%d Porcentaje: %.1f%%\n", 
                        v.getPasajeros().size(), v.getCapacidad(), v.obtenerPorcentajeOcupacion());
                System.out.println("--------------------------------");
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No hay vuelos en esa ruta.");
    }

    // --- Métodos Auxiliares de Búsqueda ---
    private Aeropuerto buscarAeropuerto(String codigo) {
        for (Aeropuerto a : repoAeropuertos.obtenerTodos()) {
            if (a.getCodigo().equalsIgnoreCase(codigo)) return a;
        }
        return null;
    }

    private Persona buscarPersona(String documento) {
        for (Persona p : repoPersonas.obtenerTodos()) {
            if (p.getDocumento().equals(documento)) return p;
        }
        return null;
    }

    private Vuelo buscarVuelo(String codigo) {
        for (Vuelo v : repoVuelos.obtenerTodos()) {
            if (v.getCodigo().equalsIgnoreCase(codigo)) return v;
        }
        return null;
    }

    private void imprimirInfoVuelo(Vuelo v) {
        System.out.println("--------------------------------");
        System.out.println("Vuelo: " + v.getCodigo());
        System.out.println("Origen: " + v.getOrigen().getCiudad());
        System.out.println("Destino: " + v.getDestino().getCiudad());
        System.out.println("Capacidad: " + v.getCapacidad());
        System.out.println("Pasajeros: " + v.getPasajeros().size());
        System.out.println("Estado: " + v.getEstado());
        System.out.printf("Porcentaje Ocupación: %.1f%%\n", v.obtenerPorcentajeOcupacion());
    }
}