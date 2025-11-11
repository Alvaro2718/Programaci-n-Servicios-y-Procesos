
public class EjemploThreadGroupBreve {

    // Objeto usado como monitor para sincronizar los hilos
    private static final Object monitor = new Object();
    // Variable compartida para indicar si hay una tarea lista
    private static boolean tareaDisponible = false;

    public static void main(String[] args) {

        // Crear un grupo de hilos con nombre "GrupoEjemplo"
        ThreadGroup grupo = new ThreadGroup("GrupoEjemplo");

        // Crear e iniciar 3 hilos dentro del grupo
        for (int i = 1; i <= 3; i++) {
            new Thread(grupo, new Tarea(), "Hilo " + i).start();
        }

        // Simular que el hilo principal prepara la tarea
        try {
            Thread.sleep(500); // Espera medio segundo antes de notificar
            synchronized (monitor) { //crea una sección crítica, donde solo un hilo puede intentar entrar al bloque
                tareaDisponible = true; // Marca la tarea como disponible
                monitor.notifyAll(); // Despierta a todos los hilos que esperaban
                System.out.println("Hilo principal: Tarea disponible, notificación enviada.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Clase interna que representa la tarea de cada hilo
    static class Tarea implements Runnable {
        @Override
        public void run() {
            synchronized (monitor) {
                // Mientras no haya tarea disponible, el hilo espera
                while (!tareaDisponible) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " espera la tarea...");
                        monitor.wait(); // El hilo se bloquea hasta ser notificado
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                // Cuando la tarea está disponible, el hilo continúa
                System.out.println(Thread.currentThread().getName() + " ha comenzado su tarea.");
            }
        }
    }
}
