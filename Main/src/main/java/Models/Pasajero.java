/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author camper
 */
public class Pasajero extends Persona {
    private int edad;

    public Pasajero(String documento, String nombre, int edad) {
        super(documento, nombre); // Llama al constructor de Persona
        this.edad = edad;
    }

    public int getEdad() { return edad; }

    @Override
    public String obtenerDescripcion() {
        return "Pasajero: " + getNombre() + " - " + edad + " años (Doc: " + getDocumento() + ")";
    }
}
