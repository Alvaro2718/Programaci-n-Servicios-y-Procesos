import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class IntercambioHilosDemoCola {

    public static void main(String[] args) {

        // 1️⃣ Crear una cola compartida con capacidad para 5 elementos
        BlockingQueue<Integer> cola = new ArrayBlockingQueue<>(5);

        // 2️⃣ Crear las tareas: Productor y Consumidor
        Runnable productor = new Productor(cola);
        Runnable consumidor = new Consumidor(cola);

        // 3️⃣ Crear e iniciar los hilos
        Thread hiloProductor = new Thread(productor, "Hilo-Productor");
        Thread hiloConsumidor = new Thread(consumidor, "Hilo-Consumidor");

        hiloProductor.start();
        hiloConsumidor.start();
    }
}
