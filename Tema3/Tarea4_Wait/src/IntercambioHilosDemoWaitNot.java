public class IntercambioHilosDemoWaitNot {

    public static void main(String[] args) {

        // 1️⃣ Crear un objeto compartido entre productor y consumidor
        Monitor monitor = new Monitor();

        // 2️⃣ Crear las tareas
        Thread productor = new Thread(new Productor(monitor), "Productor");
        Thread consumidor = new Thread(new Consumidor(monitor), "Consumidor");

        // 3️⃣ Iniciar los hilos
        productor.start();
        consumidor.start();
    }

    /**
     * 🔸 Clase interna que representa el recurso compartido entre los hilos.
     * Contiene un dato y una bandera para controlar el turno.
     */
    static class Monitor {
        private int dato;
        private boolean disponible = false; // Controla si hay un dato listo

        public synchronized void producir(int valor) {
            try {
                // Si ya hay un dato disponible, el productor debe esperar
                while (disponible) {
                    wait();
                }
                // Producir el dato
                dato = valor;
                disponible = true;
                System.out.println(Thread.currentThread().getName() +
                        " → produjo: " + valor);
                notify(); // Avisar al consumidor de que hay un nuevo dato
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public synchronized int consumir() {
            int valor = 0;
            try {
                // Si no hay dato disponible, el consumidor debe esperar
                while (!disponible) {
                    wait();
                }
                // Consumir el dato
                valor = dato;
                disponible = false;
                System.out.println(Thread.currentThread().getName() +
                        " → consumió: " + valor);
                notify(); // Avisar al productor de que puede generar otro
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return valor;
        }
    }
}

