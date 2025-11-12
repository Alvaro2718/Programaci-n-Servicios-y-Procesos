public class MiTareaNumeros implements Runnable {

    @Override
    public void run() {
        try {
            // Contar del 1 al 5
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " → número: " + i);
                Thread.sleep(500); // Simula trabajo (el hilo entra en estado TIMED_WAITING)
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


/**
 * Thread.currentThread().getName() identifica qué hilo está activo en ese momento,
 * permitiendo ver qué hace cada uno en tiempo real.
 */
