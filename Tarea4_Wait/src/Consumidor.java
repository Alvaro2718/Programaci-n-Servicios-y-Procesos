public class Consumidor implements Runnable {

    private final IntercambioHilosDemoWaitNot.Monitor monitor;

    public Consumidor(IntercambioHilosDemoWaitNot.Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int numero = monitor.consumir();

                if (numero == -1) { // Señal de fin
                    System.out.println(Thread.currentThread().getName() +
                            " → recibió señal de fin. Terminando...");
                    break;
                }

                Thread.sleep(700); // Simula tiempo de consumo
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumidor interrumpido.");
        }
    }
}


/**
 * Comunicación con wait() y notify()
 *
 * Ambos hilos comparten el objeto Monitor, que controla el acceso a la variable dato.
 *
 * Los métodos producir() y consumir() están sincronizados, lo que significa que solo un hilo puede ejecutarlos a la vez.
 *
 * Si el productor intenta producir cuando ya hay un dato (disponible == true), llama a wait(), quedando bloqueado.
 *
 * Cuando el consumidor consume el dato, llama a notify(), despertando al productor.
 *
 * Y viceversa.
 *
 */
