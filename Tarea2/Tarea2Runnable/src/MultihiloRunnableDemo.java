public class MultihiloRunnableDemo implements Runnable{
 //El código que pongamos aquí dentro es EXACTAMENTE  lo que el nuevo
    //hilo ejecutará cuando lo iniciemos. Es el "trabajo" del hilo.

    @Override
    public void run() {

      try{
          //Thread.currentThread() es un métod estático que nos da una
          //referencia al hilo que está 'actualmente'
          System.out.println(Thread.currentThread().getId()+" es id del hilo.");
      } catch(Exception e){
          System.out.println("Error: Se capturo una excepcion en el hilo");
      }
    }
}

