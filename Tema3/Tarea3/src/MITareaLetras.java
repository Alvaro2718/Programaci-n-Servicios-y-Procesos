public class MITareaLetras implements Runnable {

    @Override
    public void run() {

        try {
            // Imprimir letras de 'A' a 'E'
            for (char c = 'A'; c <= 'E'; c++) {
                System.out.println(Thread.currentThread().getName() + " → letra: " + c);
                Thread.sleep(700); // Simula trabajo distinto
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
