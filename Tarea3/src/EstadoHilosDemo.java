public class EstadoHilosDemo {

    public static void main(String[] args) {

        // 1️⃣ Creamos los objetos Runnable (las tareas que harán los hilos)
        Runnable tarea1 = new MiTareaNumeros();
        Runnable tarea2 = new MITareaLetras();

        // 2️⃣ Creamos los objetos Thread que ejecutarán esas tareas
        Thread hilo1 = new Thread(tarea1, "Hilo-Numeros");
        Thread hilo2 = new Thread(tarea2, "Hilo-Letras");

        // 🔹 Estado inicial: NEW (todavía no se ha iniciado)
        System.out.println("Estado inicial de " + hilo1.getName() + ": " + hilo1.getState());
        System.out.println("Estado inicial de " + hilo2.getName() + ": " + hilo2.getState());

        // 3️⃣ Iniciamos los hilos
        hilo1.start();
        hilo2.start();

        // 🔹 Estado después de start(): puede ser RUNNABLE
        System.out.println("Después de start(), estado de " + hilo1.getName() + ": " + hilo1.getState());
        System.out.println("Después de start(), estado de " + hilo2.getName() + ": " + hilo2.getState());

        // 4️⃣ Monitorear mientras los hilos están activos
        while (hilo1.isAlive() || hilo2.isAlive()) {
            System.out.println("Estado actual de " + hilo1.getName() + ": " + hilo1.getState());
            System.out.println("Estado actual de " + hilo2.getName() + ": " + hilo2.getState());
            try {
                Thread.sleep(300); // Pequeña pausa para observar los cambios
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // 🔹 Estado final: TERMINATED (cuando el hilo ha finalizado)
        System.out.println("Estado final de " + hilo1.getName() + ": " + hilo1.getState());
        System.out.println("Estado final de " + hilo2.getName() + ": " + hilo2.getState());

        System.out.println("\nTodos los hilos han completado su ejecución.");
    }
}

