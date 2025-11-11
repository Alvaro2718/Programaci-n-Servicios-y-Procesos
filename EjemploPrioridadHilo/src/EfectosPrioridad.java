public class EfectosPrioridad {

    public static void main(String[] args) {

        // Se crean hilos con todas las prioridades posibles (de 1 a 10)
        for (int i = Thread.MIN_PRIORITY; i <= Thread.MAX_PRIORITY; i++) {
            int prioridad = i;

            // Cada hilo realiza una tarea simple: incrementar un contador
            Thread hilo = new Thread(() -> {
                long contador = 0;
                for (int j = 0; j < 1_000_000; j++) {
                    contador++;
                }

                // Al terminar, muestra su prioridad
                System.out.println("Hilo con prioridad " + prioridad + " completado.");
            });

            // Se asigna la prioridad correspondiente al hilo
            hilo.setPriority(prioridad);

            // Se inicia el hilo
            hilo.start();
        }
    }
}
