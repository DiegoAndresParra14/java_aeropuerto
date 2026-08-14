/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author camper
 */
public abstract class Persona {
    private String documento;
    private String nombre;

    public Persona(String documento, String nombre) {
        this.documento = documento;
        this.nombre = nombre;
    }

    public String getDocumento() { return documento; }
    public String getNombre() { return nombre; }

    // Método abstracto para aplicar polimorfismo
    public abstract String obtenerDescripcion();
}
