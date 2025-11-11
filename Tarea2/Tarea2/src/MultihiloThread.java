public class MultihiloThread {
    public static void main(String[] args) {

        // Crear y arrancar 8 hilos
        for (int i = 1; i <= 8; i++) {
            MultihiloThreadDemo hilo = new MultihiloThreadDemo(); // crear instancia del hilo
            hilo.start(); // iniciar hilo
            System.out.println("El hilo " + i + " ha sido creado correctamente");
        }
    }
}
