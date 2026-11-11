import Models.Aeropuerto;
import Models.Pasajero;
import Models.Vuelo;
import Services.SistemaVuelos;
import exceptions.VueloLlenoException;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class AirportRegressionTest {
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
    public static void main(String[] args) throws Exception {
        Aeropuerto origen = new Aeropuerto("A", "A", "A", "CO");
        Aeropuerto destino = new Aeropuerto("B", "B", "B", "CO");
        for (int invalid : new int[]{0, -1}) {
            try { new Vuelo("BAD", origen, destino, invalid); throw new AssertionError("Aceptó capacidad inválida"); }
            catch (IllegalArgumentException expected) { }
        }
        Vuelo vuelo = new Vuelo("TEST", origen, destino, 1);
        vuelo.reservarAsiento(new Pasajero("TEST1", "Prueba", 20));
        check(vuelo.obtenerPorcentajeOcupacion() == 100.0, "Ocupación incorrecta");
        try { vuelo.reservarAsiento(new Pasajero("TEST2", "Prueba", 21)); throw new AssertionError("Aceptó sobrecupo"); }
        catch (VueloLlenoException expected) { }
        check(vuelo.getPasajeros().size() == 1, "Alteró pasajeros tras rechazar reserva");

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            SistemaVuelos sistema = new SistemaVuelos();
            sistema.registrarVuelo("BAD", "BOG", "MDE", 0);
            sistema.reservarVuelo("1001", "BAD");
            check(output.toString(StandardCharsets.UTF_8).contains("Vuelo no encontrado"), "Registró capacidad cero");
            for (String input : new String[]{"2\nTEST\nNombre\nabc\n11\n", "3\nTEST\nBOG\nMDE\nabc\n11\n", "8\nAV101\nabc\n11\n", "", "2\n"}) {
                output.reset();
                System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
                com.mycompany.main.Main.main(new String[0]);
                if (input.contains("abc")) {
                    String result = output.toString(StandardCharsets.UTF_8);
                    check(result.contains("número entero válido"), "No informó la entrada inválida");
                    check(result.contains("Gracias"), "El menú no se recuperó");
                }
            }
        } finally { System.setIn(originalIn); System.setOut(originalOut); }
        System.out.println("PASS: capacidad, sobrecupo, ocupación, entradas inválidas y fin de entrada");
    }
}
