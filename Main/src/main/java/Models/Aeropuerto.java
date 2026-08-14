/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author camper
 */
public class Aeropuerto {
    private String codigo;
    private String nombre;
    private String ciudad;
    private String pais;

    public Aeropuerto(String codigo, String nombre, String ciudad, String pais) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCiudad() { return ciudad; }
    public String getPais() { return pais; }
}