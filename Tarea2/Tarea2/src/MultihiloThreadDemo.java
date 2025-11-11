public class MultihiloThreadDemo extends Thread {

    @Override
    public void run() {
        try {
            // Muestra el ID del hilo actual cuando se está ejecutando
            System.out.println(Thread.currentThread().getId() + " se está ejecutando.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
