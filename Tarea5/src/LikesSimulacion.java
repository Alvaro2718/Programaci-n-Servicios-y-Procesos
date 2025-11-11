import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Alvaro Lozano
 * @since 29/10/2025
 */

public class LikesSimulacion {

    // Variable compartida entre todos los hilos (contador de likes)
    // AtomicInteger garantiza que las operaciones sean seguras en entornos multihilo
    static AtomicInteger contadorLikes = new AtomicInteger(0);

    // Clase interna que representa a un usuario que da likes
    public static class Usuario extends Thread {

        private final String nombre;

        public Usuario(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            // Cada usuario da 100 likes
            for (int i = 0; i < 100; i++) {
                // incrementAndGet() incrementa el contador de forma atómica y devuelve el nuevo valor
                int likesActuales = contadorLikes.incrementAndGet();

                System.out.println(nombre + " dio un like. Total likes: " + likesActuales);

                // Simula un pequeño tiempo aleatorio entre likes
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(nombre + " fue interrumpido");
                }
            }
        }
    }

    public static void main(String[] args) {

        // Crear 3 hilos (3 usuarios)
        Thread usuario1 = new Thread(new Usuario("Usuario 1"));
        Thread usuario2 = new Thread(new Usuario("Usuario 2"));
        Thread usuario3 = new Thread(new Usuario("Usuario 3"));

        // Iniciar los hilos
        usuario1.start();
        usuario2.start();
        usuario3.start();

        // Esperar a que todos los hilos terminen
        try {
            usuario1.join();
            usuario2.join();
            usuario3.join();
        } catch (InterruptedException e) {
            System.out.println("Error al esperar la finalización de los hilos.");
        }

        // Mostrar el resultado final del contador
        System.out.println("Total final de likes: " + contadorLikes.get());
    }
}
