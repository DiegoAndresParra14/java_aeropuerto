package Models;

import Models.Aeropuerto;
import Models.EstadoVuelo;
import Models.Pasajero;
import exceptions.VueloLlenoException;
import java.util.ArrayList;
import java.util.List;

public class Vuelo {
    private String codigo;
    private Aeropuerto origen;
    private Aeropuerto destino;
    private int capacidad;
    private EstadoVuelo estado;
    private List<Pasajero> pasajeros;

    public Vuelo(String codigo, Aeropuerto origen, Aeropuerto destino, int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.estado = EstadoVuelo.PROGRAMADO; // Estado por defecto
        this.pasajeros = new ArrayList<>();
    }

    public String getCodigo() { return codigo; }
    public Aeropuerto getOrigen() { return origen; }
    public Aeropuerto getDestino() { return destino; }
    public int getCapacidad() { return capacidad; }
    public EstadoVuelo getEstado() { return estado; }
    public void setEstado(EstadoVuelo estado) { this.estado = estado; }
    public List<Pasajero> getPasajeros() { return pasajeros; }

    // Lógica encapsulada para agregar un pasajero y lanzar excepción si está lleno
    public void reservarAsiento(Pasajero pasajero) throws VueloLlenoException {
        if (pasajeros.size() >= capacidad) {
            throw new VueloLlenoException("No existen asientos disponibles para el vuelo " + codigo);
        }
        pasajeros.add(pasajero);
    }
    
    // Método para el Reto Adicional
    public double obtenerPorcentajeOcupacion() {
        if (capacidad == 0) return 0.0;
        return ((double) pasajeros.size() / capacidad) * 100;
    }
}
