public class MultihiloRunnableDemo implements Runnable {



    @Override
    public void run() {
        try {
            // Thread.currentThread() devuelve el hilo que está ejecutando este código
            System.out.println(Thread.currentThread().getId() + " es id del hilo.");
        } catch (Exception e) {
            System.out.println("Error: Se capturó una excepción en el hilo");
        }
    }
}
