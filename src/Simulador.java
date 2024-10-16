import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


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

    
    public static void generarReferencias() {
        Scanner scanner = new Scanner(System.in);
        
        // Obtener datos del usuario
        System.out.print("Ingrese el tamaño de página (en bytes): ");
        int tamanoPagina = scanner.nextInt();
        
        System.out.print("Ingrese el nombre del archivo de imagen (BMP): ");
        String nombreArchivo = scanner.next();

        try {
            // Leer la imagen
            Imagen imagen = new Imagen(nombreArchivo); // Clase Imagen para leer el archivo BMP
            
            int filas = imagen.getAlto();    // Obtener el alto de la imagen en píxeles
            int columnas = imagen.getAncho(); // Obtener el ancho de la imagen en píxeles
            int tamanoTotalImagen = filas * columnas * 3; // Total de bytes de la imagen (3 bytes por píxel en formato RGB)
            
            // Calcular el número de páginas necesarias para la imagen
            int totalPaginas = (int) Math.ceil((double) tamanoTotalImagen / tamanoPagina);
            
            // Calcular el número total de referencias
            int totalReferencias = filas * columnas * 3; // Cada píxel tiene 3 colores (R, G, B)
            
            // Generar el archivo de referencias
            FileWriter writer = new FileWriter("referencias.txt");

            // Escribir los datos iniciales en el archivo
            writer.write("Datos archivo\n");
            writer.write("Comentario\n");
            writer.write("P=" + tamanoPagina + "\n");
            writer.write("NF=" + filas + "\n");
            writer.write("NC=" + columnas + "\n");
            writer.write("NR=" + totalReferencias + "\n");
            writer.write("NP=" + totalPaginas + "\n");

            // Escribir las referencias para la matriz de imagen
            int referenciaIndex = 0; // Índice de referencia para desplazamiento dentro de la página
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    // Para cada color (R, G, B) de cada píxel
                    String[] colores = {"R", "G", "B"};
                    for (int k = 0; k < 3; k++) {  // 0 = R, 1 = G, 2 = B
                        int paginaVirtual = referenciaIndex / tamanoPagina; // Calcular la página virtual
                        int desplazamiento = referenciaIndex % tamanoPagina; // Calcular el desplazamiento dentro de la página
                        writer.write("Imagen[" + i + "][" + j + "]." + colores[k] + "," + paginaVirtual + "," + desplazamiento + ",R\n");
                        referenciaIndex++;
                    }
                }
            }

            // Escribir las referencias para el vector de mensaje (simulación)
            // Suponiendo que el vector de mensaje es almacenado después de la imagen
            int vectorInicioPagina = referenciaIndex / tamanoPagina;
            for (int l = 0; l < 10; l++) { // Simulamos un mensaje con 10 caracteres
                int paginaVirtual = (referenciaIndex / tamanoPagina);
                int desplazamiento = (referenciaIndex % tamanoPagina);
                writer.write("Mensaje[" + l + "]," + paginaVirtual + "," + desplazamiento + ",W\n");
                referenciaIndex++;
            }

            writer.close();
            System.out.println("Referencias generadas en 'referencias.txt'");

        } catch (IOException e) {
            System.out.println("Error al generar el archivo de referencias.");
            e.printStackTrace();
        }
    }

}
