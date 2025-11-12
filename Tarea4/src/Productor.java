import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Productor implements Runnable {

    private final BlockingQueue<Integer> cola;
    private final Random random = new Random();

    public Productor(BlockingQueue<Integer> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            // Producir 10 números aleatorios
            for (int i = 1; i <= 10; i++) {
                int numero = random.nextInt(100) + 1; // entre 1 y 100
                cola.put(numero); // Si la cola está llena, espera (bloquea)
                System.out.println(Thread.currentThread().getName() +
                        " → generó número: " + numero);
                Thread.sleep(500); // Simula tiempo de producción
            }
            // Señal de finalización (valor especial)
            cola.put(-1);
            System.out.println(Thread.currentThread().getName() + " ha terminado la producción.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Productor interrumpido.");
        }
    }
}
