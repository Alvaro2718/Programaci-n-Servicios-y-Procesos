public class MultihiloThread {

    public static void main(String[] args) {

        for(int i=1; i<=8;i++){

            new Thread(new MultihiloThreadDemo()).start();
            System.out.println("El hilo " + i + " ha sido creado correctamente");
        }
    }
}
/**
 * public class MultihiloThread{
 *
 *     public static void main
 *
 *     for(int i=0; i < n; i++){
 *
 *           //Paso 1 = crea el objeto de la clase MultihiloThreadDemo
 *          MultihilothreadDemo thread = new MultithreadDemo();
 *
 *          //Paso 2 = Inicia el hilo
 *          thread.start()
 *     }
 *
 *
 *
 * }
 *
 *
 */