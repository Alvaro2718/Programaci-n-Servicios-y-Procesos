import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarreraPriodidades {

    public static void main(String[] args) {
        // Lista sincronizada para registrar orden de llegada
        List<String> ordenLlegada = Collections.synchronizedList(new ArrayList<>());

        // Crear corredores
        Corredor c1 = new Corredor("Corredor 1", ordenLlegada);
        Corredor c2 = new Corredor("Corredor 2", ordenLlegada);
        Corredor c3 = new Corredor("Corredor 3", ordenLlegada);
        Corredor c4 = new Corredor("Corredor 4", ordenLlegada);
        Corredor c5 = new Corredor("Corredor 5", ordenLlegada);

        // Crear hilos
        Thread t1 = new Thread(c1, "Hilo-C1");
        Thread t2 = new Thread(c2, "Hilo-C2");
        Thread t3 = new Thread(c3, "Hilo-C3");
        Thread t4 = new Thread(c4, "Hilo-C4");
        Thread t5 = new Thread(c5, "Hilo-C5");

        // Asignar prioridades
        t1.setPriority(Thread.MIN_PRIORITY);  // Prioridad baja
        t2.setPriority(Thread.NORM_PRIORITY); // Prioridad normal
        t3.setPriority(Thread.MAX_PRIORITY);  // Prioridad alta
        t4.setPriority(Thread.NORM_PRIORITY); // Prioridad normal
        t5.setPriority(Thread.MIN_PRIORITY);  // Prioridad baja

        // Iniciar hilos
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Esperar a que todos los corredores terminen
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main interrumpido mientras esperaba a los hilos.");
        }

        // Mostrar orden de llegada
        System.out.println("\n--- Orden de llegada ---");
        for (int i = 0; i < ordenLlegada.size(); i++) {
            System.out.println((i + 1) + "º - " + ordenLlegada.get(i) + " Author Alvaro Lozano");
        }
    }
}
