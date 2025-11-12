import java.util.Random;

public class Productor implements Runnable {

    private final IntercambioHilosDemoWaitNot.Monitor monitor;
    private final Random random = new Random();

    public Productor(IntercambioHilosDemoWaitNot.Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            // Generar 10 números aleatorios
            for (int i = 1; i <= 10; i++) {
                int numero = random.nextInt(100) + 1;
                monitor.producir(numero);
                Thread.sleep(500); // Simula tiempo de producción
            }
            // Señal de fin (por convención usamos -1)
            monitor.producir(-1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Productor interrumpido.");
        }
    }
}
