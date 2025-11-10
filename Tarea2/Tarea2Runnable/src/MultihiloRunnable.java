public class MultihiloRunnable {

    public static void main(String [] args){


        for (int i=1; i<= 8; i++){
            //Paso 1: Creamos el objeto de nuestra clase Runnable
            //Esto NO es el hilo, es solo la TAREA ("el trabajo") qu queremos ejecutar
            MultihiloRunnableDemo tarea = new MultihiloRunnableDemo();

            //Paso 2: Creamos el objeto Thread
            //Le pasamos nuestro 'tarea' (el objeto Runnable) a su contructor
            //Le estamos diciendo al trabajador: "Cuando te inicies, ejecuta esta tarea"
            Thread thread = new Thread(tarea);

            //Paso 3: Iniciamos el hilo
            thread.start();
        }
    }
}