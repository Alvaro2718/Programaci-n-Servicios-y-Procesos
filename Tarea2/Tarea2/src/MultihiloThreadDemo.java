public class MultihiloThreadDemo  extends Thread{

    @Override
    public void run() {

        try{
            System.out.println(Thread.currentThread().getId()+ " se esta ejecutando.");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
