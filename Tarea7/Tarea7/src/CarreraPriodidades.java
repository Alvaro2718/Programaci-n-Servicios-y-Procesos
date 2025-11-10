import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Definimos la clase principal que simulará la carrera
public class CarreraPriodidades {

    // CarreraPrioridades.java

    public static void main(String[] args) {
        //1º Lista sincronizada para almacenar el orden de llegada
        List<String> ordenLlegada = Collections.synchronizedList(new ArrayList<>());

        //2º Crear corredores (Runnable)
        Corredor c1 = new Corredor("Corredor 1", ordenLlegada);
        Corredor c2 = new Corredor("Corredor 2", ordenLlegada);
        Corredor c3 = new Corredor("Corredor 3", ordenLlegada);
        Corredor c4 = new Corredor("Corredor 4", ordenLlegada);
        Corredor c5 = new Corredor("Corredor 5", ordenLlegada);

        //3º Crear hilos y asignar nombres + prioridades (ANTES de start)
        Thread t1 = new Thread(c1, "Hilo-C1");
        Thread t2 = new Thread(c2, "Hilo-C2");
        Thread t3 = new Thread(c3, "Hilo-C3");
        Thread t4 = new Thread(c4, "Hilo-C4");
        Thread t5 = new Thread(c5, "Hilo-C5");


        // Asignamos prioridades
        t1.setPriority(Thread.MIN_PRIORITY);  // Corredor 1
        t2.setPriority(Thread.NORM_PRIORITY); // Corredor 2
        t3.setPriority(Thread.MAX_PRIORITY);  // Corredor 3
        t4.setPriority(Thread.NORM_PRIORITY); // Corredor 4
        t5.setPriority(Thread.MIN_PRIORITY);  // Corredor 5

        //4º Iniciar carrera
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        //5º Esperar a que todos terminen
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

        //6º Imprimir orden de llegada
        System.out.println("\n--- Orden de llegada ---");
        for (int i = 0; i < ordenLlegada.size(); i++) {
            System.out.println((i + 1) + "º - " + ordenLlegada.get(i) + " Author Alvaro Lozano");
        }
    }
}

