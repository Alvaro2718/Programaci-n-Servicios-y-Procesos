import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class IntercambioHilosDemoCola {

    public static void main(String[] args) {

        // 1º Crear una cola compartida con capacidad para 5 elementos
        BlockingQueue<Integer> cola = new ArrayBlockingQueue<>(5);

        // 2º Crear las tareas: Productor y Consumidor
        Runnable productor = new Productor(cola);
        Runnable consumidor = new Consumidor(cola);

        // 3º Crear e iniciar los hilos
        Thread hiloProductor = new Thread(productor, "Hilo-Productor");
        Thread hiloConsumidor = new Thread(consumidor, "Hilo-Consumidor");
        System.out.println("El autor es: Alvaro Lozano");

        hiloProductor.start();
        hiloConsumidor.start();
    }
}
