import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * @author Alvaro Lozano
 *
 */

public class Collaboration {

    public static void main(String[] args) {
        String outputFile = "numbers.txt";

        // Número de instancias a lanzar por defecto
        int numProcesses = 10;

        /*
         * EXTRA PARA OBTENER EL 10:
         * Si el usuario pasa un número por argumentos,
         * ese número será la cantidad de procesos a ejecutar.
         * Ejemplo:
         *     java Collaboration 20
         * lanzará 20 instancias del programa "RandomGenerator".
         */
        if (args.length > 0) {
            try {
                numProcesses = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Argumento no válido, usando el valor por defecto (10 procesos).");
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            for (int i = 0; i < numProcesses; i++) {

                Process process = new ProcessBuilder("java", "RandomGenerator")
                        .redirectErrorStream(true)
                        .start();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream())
                );

                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }

                process.waitFor();
            }

            System.out.println("Fichero generado correctamente: " + outputFile + "\nAutor: Alvaro Lozano");
            System.out.println("Procesos ejecutados: " + numProcesses);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

