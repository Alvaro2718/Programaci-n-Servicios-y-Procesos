public class MultihiloRunnable {

    public static void main(String[] args) {

        for (int i = 1; i <= 8; i++) {
            // Paso 1: Crear el objeto de nuestra clase Runnable
            // (representa la tarea a ejecutar)
            MultihiloRunnableDemo tarea = new MultihiloRunnableDemo();

            // Paso 2: Crear el hilo, pasando la tarea como parámetro
            Thread thread = new Thread(tarea);

            // Paso 3: Iniciar el hilo (esto ejecuta run() en paralelo)
            thread.start();

            System.out.println("El hilo " + i + " ha sido creado correctamente");
        }
    }
}

/**
 * Explicación breve:
 *
 * MultihiloRunnableDemo implementa Runnable, por lo que define el método run(), que contiene el trabajo del hilo.
 *
 * En el main, se crea un objeto Thread al que se le pasa la tarea (Runnable) como parámetro.
 *
 * Al llamar a start(), el hilo se inicia y ejecuta el método run() en paralelo.
 *
 * Cada hilo muestra su ID único (Thread.currentThread().getId()), confirmando la ejecución concurrente.
 */