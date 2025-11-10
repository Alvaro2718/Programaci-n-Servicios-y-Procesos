public class EfectosPrioridad {
    public static void main(String[] args) {
        for (int i = Thread.MIN_PRIORITY; i <=
                Thread.MAX_PRIORITY; i++) {
            int prioridad = i;
            Thread hilo = new Thread(() -> {
                long contador = 0;
                for (int j = 0; j < 1_000_000; j++) {
                    contador++;
                }
                System.out.println("Hilo con prioridad " +
                        prioridad + " completado.");
            });
            hilo.setPriority(prioridad);
            hilo.start();
        }
    }
}
