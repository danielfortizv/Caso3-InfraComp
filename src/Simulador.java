import java.util.ArrayList;
import java.util.Random;


public class Simulador {
    private ManejadorMemoria manejadorMemoria;
    private ArrayList<Integer> referencias;

    public Simulador(int marcos, ArrayList<Integer> referencias) {
        this.manejadorMemoria = new ManejadorMemoria(marcos);
        this.referencias = referencias;
    }

    public void iniciarSimulador() {
        for (int i = 0; i < referencias.size(); i++) {
            // Simula si la página fue modificada o no
            boolean modificada = new Random().nextBoolean();
            
            // Accede a la página usando el índice correcto
            manejadorMemoria.accederPagina(referencias.get(i), modificada);

            // Ejecuta el algoritmo de envejecimiento cada 10 accesos
            if (i % 10 == 0) {
                manejadorMemoria.correrAlgoritmo();
            }

            // Simula una pausa de 1 milisegundo por acceso
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Imprime las estadísticas después de la simulación
        manejadorMemoria.imprimirEstadisticas();
    }
}
