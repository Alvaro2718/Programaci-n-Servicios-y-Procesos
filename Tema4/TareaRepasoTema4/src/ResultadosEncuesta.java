import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Esta clase almacena todas las respuestas de la encuesta por zonas.
// Es usada por varios hilos simultáneamente, por lo que sus métodos
// están sincronizados para evitar problemas de concurrencia.
public class ResultadosEncuesta {

    // Mapa donde la clave es el nombre de la zona ("zona1", "zona2", etc.)
    // y el valor es una lista con las respuestas dadas en esa zona.
    private final Map<String, List<String>> datos = new HashMap<>();

    // Método sincronizado para que solo un hilo pueda modificar los datos a la vez.
    // Añade una respuesta a la lista de la zona especificada.
    public synchronized void agregarRespuesta(String zona, String respuesta){
        // computeIfAbsent(): si la zona NO existe, crea una nueva lista automáticamente.
        // Luego añade la respuesta a esa lista.
        datos.computeIfAbsent(zona, z -> new ArrayList<>()).add(respuesta);
    }

    // Método sincronizado: genera un resumen de respuestas de una zona específica.
    public synchronized String getResumenZona(String zona){
        // Se obtiene la lista de respuestas de esa zona.
        List<String> respuesta = datos.get(zona);

        // Si no existe la zona o está vacía, devolvemos un mensaje informativo.
        if(respuesta == null || respuesta.isEmpty()){
            return "No hay respuestas para la zona " + zona;
        }

        // Se crea un texto resumen.
        StringBuilder sb = new StringBuilder("Resumen zona " + zona + ":\n");
        int i = 1;

        // Se recorren todas las respuestas y se añaden al resumen numeradas.
        for (String r : respuesta) {
            sb.append(i++).append(". ").append(r).append("\n");
        }

        // Se devuelve el resumen final.
        return sb.toString();
    }

    // Método sincronizado: genera un resumen de TODAS las zonas registradas.
    public synchronized String getResumenGlobal(){
        // Si no hay ninguna zona registrada, se informa.
        if (datos.isEmpty()) return "No hay resultados registrados.";

        // Construimos el resumen global.
        StringBuilder sb = new StringBuilder("Resumen global:\n");

        // Recorremos todas las zonas existentes y llamamos al método de resumen de zona.
        for (String zona : datos.keySet()) {
            sb.append(getResumenZona(zona)).append("\n");
        }

        // Devolvemos el resumen completo.
        return sb.toString();
    }
}
