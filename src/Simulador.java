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
        Thread threadActualizacion = new Thread(() -> {
            for (int i = 0; i < referencias.size(); i++) {
                boolean modificada = new Random().nextBoolean();
                
                // Accede a la página usando el índice correcto
                manejadorMemoria.accederPagina(referencias.get(i), modificada);

                try {
                    // Simula una pausa de 1 milisegundo por acceso
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadEnvejecimiento = new Thread(() -> {
            //bucle infinito para que se ejecute mientras el programa esté en ejecución
            while (true) {
                try {
                    // Aplica el algoritmo de envejecimiento cada 2 milisegundos
                    manejadorMemoria.correrAlgoritmo();
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        
        threadActualizacion.start(); //recorre las referencias de páginas y simular el acceso a cada una de ellas.
        threadEnvejecimiento.start(); //aplica el algoritmo de envejecimiento

        try {
            // Esperar a que el thread de actualización termine
            threadActualizacion.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Imprime las estadísticas después de la simulación
        manejadorMemoria.imprimirEstadisticas();
    }
}
