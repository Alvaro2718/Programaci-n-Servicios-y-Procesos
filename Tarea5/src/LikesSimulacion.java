import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Alvaro Lozano
 * @since 29/1072025
 */

public class LikesSimulacion {

    //Declara un AtomicInteger llamado contadorLikes con un valor inicial de 0
     static AtomicInteger contadorLikes = new AtomicInteger(0);


    //Crea una clase interna Usuario que extienda Thread.

    //Cada instancia de esta clase representará un usuario que da 100 likes

    //En el metodo run() de la clase Usuario, implementa un bucle que incremente el contador de likes
    //usando contadorLikes.incrementAndGet()

    public static class Usuario extends Thread {

        private final String nombre;

        public Usuario(String nombre) {

            this.nombre = nombre;

        }

        @Override
        public void run(){
            for (int i = 0; i < 100; i++) {

               int likesActuales = contadorLikes.incrementAndGet();
               System.out.println(nombre + " " +" dio un like . Total like " + likesActuales);

                //Simula un tiempo aleatorio entre likes
                try{
                    Thread.sleep((long) (Math.random() * 100));
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                    System.out.println(nombre + " fue interrumpido");
                }
            }

        }
    }



    public static void main(String[] args) {

        //Crea los hilos
        Thread usuario1 = new Thread(new Usuario("Usuario 1"));
        Thread usuario2 = new Thread(new Usuario("Usuario 2"));
        Thread usuario3 = new Thread(new Usuario("Usuario 3"));


        //Iniciar hilos
        usuario1.start();
        usuario2.start();
        usuario3.start();

        //Esperar a que los hilos terminen con join
        try{
            usuario1.join();
            usuario2.join();
            usuario3.join();

        }catch(InterruptedException e){
            System.out.println("Error al esperar la finalización de los hilos.");
        }

        //Mostrar el resultado final
        System.out.println("Total final de likes: " + contadorLikes.get());

    }
}
