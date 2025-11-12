import java.util.concurrent.BlockingQueue;

public class Consumidor implements Runnable {

    private final BlockingQueue<Integer> cola;

    public Consumidor(BlockingQueue<Integer> cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Toma el siguiente número (bloquea si la cola está vacía)
                int numero = cola.take();

                // Señal de fin (-1)
                if (numero == -1) {
                    System.out.println(Thread.currentThread().getName() +
                            " → recibió señal de fin. Terminando...");
                    break;
                }

                System.out.println(Thread.currentThread().getName() +
                        " → procesó número: " + numero);

                Thread.sleep(800); // Simula tiempo de consumo
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumidor interrumpido.");
        }
    }
}

