import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class SalaVideojuegos {


    //Creamos un semáforo con 4 permisos, que representa
    //Cada permiso permite a un jugador usar una máquina.
    private static final int Spaces = 4;
    private static final Semaphore salaDisponible = new Semaphore(Spaces);

    //Hilo principal
    public static void main(String[] args) {
        // Se crean 10 hilos, uno por cada jugador que intenta acceder a la sala.
        for (int i = 1; i <= 10; i++) {
            Thread jugador = new Thread(new Jugador("Jugador " + i));
            jugador.start(); // Se inicia el hilo jugador, ejecutando el método run()
        }

    }


    private static class Jugador implements Runnable {

        private final String nombre;

        //Constructor
        public Jugador(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {

            try {

                //1º Cada jugador intenta adquirir un permiso del semáforo.
                // Si no hay permisos disponibles, el hilo se bloquea hasta que otro lo libere
                System.out.println(nombre + " está intentando entrar en la sala.");
                salaDisponible.acquire();// Intentar adquirir permiso para entrar

                //2º Una vez adquirido el permiso, el jugador entra en la sala.
                System.out.println(nombre + " ha entrado en la sala.");

                //3º Simulamos el tiempo de juego con un retardo aleatorio entre 2 y 5 segundos
                int tiempoEnSala = ThreadLocalRandom.current().nextInt(2000, 5000);
                Thread.sleep(tiempoEnSala);

                //4º Al terminar, el jugador abandona la sala.
                System.out.println(nombre + " ha salido de la sala despues de " + (tiempoEnSala / 1000.0));

            } catch (InterruptedException e) {
                // Si el hilo es interrumpido mientras espera o juega, se interrumpe de forma controlada.
                Thread.currentThread().interrupt();
                System.out.println(nombre + " fue interrumpido");
            } finally {
                // Muy importante: se libera el permiso del semáforo.
                // Esto permite que otro jugador pueda entrar.
                // Mejora posible: solo liberar si se llegó a adquirir el permiso
                salaDisponible.release();

                System.out.println("El jugador "+ nombre+" termino la sala definitivamente");
            }
        }
    }
}
