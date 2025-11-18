import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * EJERCICIO DE SEMÁFOROS PARA CONTROLAR ACCESO
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * EJERCICIO DE SAMAFOROS
 */
public class SalaVideojuegos {

    // Número de máquinas disponibles en la sala (permits del semáforo)
    private static final int Spaces = 4;

    // Semáforo para controlar acceso a la sala de videojuegos
    private static final Semaphore salaDisponible = new Semaphore(Spaces);

    // Hilo principal
    public static void main(String[] args) {
        // Crear 10 jugadores (hilos) que intentan entrar en la sala
        for (int i = 1; i <= 10; i++) {
            Thread jugador = new Thread(new Jugador("Jugador " + i));
            jugador.start(); // Inicia el hilo jugador
        }
    }

    // Clase interna que representa un jugador
    private static class Jugador implements Runnable {

        private final String nombre;

        public Jugador(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public void run() {
            try {
                // Intento de entrada: bloquea si no hay permisos disponibles
                System.out.println(nombre + " está intentando entrar en la sala.");
                salaDisponible.acquire(); // Adquiere permiso del semáforo

                // Entrada permitida
                System.out.println(nombre + " ha entrado en la sala.");

                // Simula el tiempo de juego (aleatorio entre 2 y 5 segundos)
                int tiempoEnSala = ThreadLocalRandom.current().nextInt(2000, 5000);
                Thread.sleep(tiempoEnSala);

                // Fin del tiempo de juego
                System.out.println(nombre + " ha salido de la sala después de " + (tiempoEnSala / 1000.0) + " segundos");

            } catch (InterruptedException e) {
                // Control de interrupciones del hilo
                Thread.currentThread().interrupt();
                System.out.println(nombre + " fue interrumpido");
            } finally {
                // Liberar el permiso para que otro jugador pueda entrar
                salaDisponible.release();
                System.out.println("El jugador " + nombre + " terminó la sala definitivamente");
            }
        }
    }
}


/**
 * Explicación breve
 *
 * 1º'Semáforo (Semaphore)'
 *
 * Representa un recurso limitado: aquí 4 máquinas de videojuegos.
 *
 * acquire() bloquea al hilo si no hay permisos disponibles.
 *
 * release() libera el permiso para que otro hilo pueda usarlo.
 *
 * 2º Simulación de jugadores
 *
 * Se crean 10 hilos, cada uno representando un jugador.
 *
 * Solo 4 jugadores pueden estar en la sala al mismo tiempo.
 *
 * Cada jugador permanece un tiempo aleatorio jugando (Thread.sleep()).
 *
 * 3º Ejecución concurrente
 *
 * Los hilos que intentan entrar cuando no hay permisos se bloquean hasta que uno salga.
 *
 * Esto garantiza que nunca haya más de 4 jugadores dentro al mismo tiempo.
 *
 * 4º Salida y control
 *
 * finally asegura que el permiso se libere aunque ocurra una excepción.
 *
 * La salida será intercalada de forma no determinista, pero nunca habrá más de 4 jugadores simultáneos.
 */
