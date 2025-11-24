import java.time.LocalTime;

public class EstadoHilosDemo {

    public static void main(String[] args) {

        // 1º Creamos los objetos Runnable (las tareas que harán los hilos)
        Runnable tarea1 = new MiTareaNumeros();
        Runnable tarea2 = new MITareaLetras();

        // 2º Creamos los objetos Thread que ejecutarán esas tareas
        Thread hilo1 = new Thread(tarea1, "Hilo-Numeros");
        Thread hilo2 = new Thread(tarea2, "Hilo-Letras");

        // Estado inicial: NEW (todavía no se ha iniciado)
        System.out.println("Estado inicial de " + hilo1.getName() + ": " + hilo1.getState());
        System.out.println("Estado inicial de " + hilo2.getName() + ": " + hilo2.getState());

        // 3º Iniciamos los hilos
        hilo1.start();
        hilo2.start();

        // Estado después de start(): puede ser RUNNABLE
        System.out.println("Despues de start(), estado de " + hilo1.getName() + ": " + hilo1.getState());
        System.out.println("Despues de start(), estado de " + hilo2.getName() + ": " + hilo2.getState());

        // 4º Monitorear mientras los hilos están activos
        int contador = 1;
        while (hilo1.isAlive() || hilo2.isAlive()) {

            // BLOQUE EXTRA PARA CONSEGUIR EL 10:
            // Se añade la hora actual y un contador de chequeos para mostrar más detalles del ciclo de vida
            System.out.println("[" + LocalTime.now() + "] Chequeo #" + contador + "  Estado de "
                    + hilo1.getName() + ": " + hilo1.getState());
            System.out.println("[" + LocalTime.now() + "] Chequeo #" + contador + " Estado de "
                    + hilo2.getName() + ": " + hilo2.getState());
            contador++;

            try {
                Thread.sleep(300); // Pequeña pausa para observar los cambios
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Estado final: TERMINATED (cuando el hilo ha finalizado)
        System.out.println("Estado final de " + hilo1.getName() + ": " + hilo1.getState());
        System.out.println("Estado final de " + hilo2.getName() + ": " + hilo2.getState());

        System.out.println("\nTodos los hilos han completado su ejecución.\nEl autor de esta tarea es: Alvaro Lozano");
    }
}
