import java.util.*;
import java.util.List;

public class Corredor implements Runnable {

    private final String nombre;
    private final List<String> metaCompartida;
    public static final int NUM_ETAPAS = 10;

    public Corredor(String nombre, List<String> metaCompartida) {
        this.nombre = nombre;
        this.metaCompartida = metaCompartida;
    }

    @Override
    public void run() {
        for (int i = 1; i <= NUM_ETAPAS; i++) {
            // Cada etapa se avanza con un pequeño retardo
            System.out.println(nombre + " ha avanzado a la etapa " + i + ".");
            try {
                Thread.sleep(100); // Simula tiempo de avance
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nombre + " fue interrumpido.");
                return;
            }
        }

        // Registrar llegada en la lista sincronizada
        synchronized (metaCompartida) {
            metaCompartida.add(nombre);
        }
        System.out.println(nombre + " ha cruzado la meta.");
    }
}
