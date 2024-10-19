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
        // Obtener datos por consola
        System.out.print("Ingrese el tamaño de página (en bytes): ");
        int tamanoPagina = scanner.nextInt();
        System.out.print("Ingrese el nombre del archivo de imagen (Solo pon el nombre sin la extensión): ");
        String nombreArchivo = "Archivos/" + scanner.next() + ".bmp";
        
        try {
            // Leer la imagen
            Imagen imagen = new Imagen(nombreArchivo);
            int filas = imagen.alto;
            int columnas = imagen.ancho;
            int largo = imagen.leerLongitud();
            int totalBytesImagen = filas * columnas * 3; // Tamaño en bytes de la imagen
            int totalBytesMensaje = largo;
            int totalPaginas = (int) Math.ceil((double) (totalBytesImagen + totalBytesMensaje) / tamanoPagina);
            int referenciasTotales = 16 + (17 * largo);  // NR ya está calculado correctamente, no lo cambiamos
            
            // Generar el archivo de referencias
            FileWriter writer = new FileWriter("referencias.txt");
    
            // Escribir los datos iniciales en el archivo
            writer.write("P=" + tamanoPagina + "\n");
            writer.write("NF=" + filas + "\n");
            writer.write("NC=" + columnas + "\n");
            writer.write("NR=" + referenciasTotales + "\n");
            writer.write("NP=" + totalPaginas + "\n");
            
            // Variables para controlar las referencias y el mensaje
            int referenciaIndex = 0; // Índice de referencia para desplazamiento dentro de la página
            int mensajeIndex = 0; // Índice para el vector de mensaje
            boolean alternar = false; // Controla la alternancia entre imagen y mensaje
            int secuenciaImagen = 0; // Para contar hasta 16 referencias de imagen
            int referenciasGeneradas = 0; // Contador de referencias generadas
            
            // Suposición: El vector de mensaje usa una página distinta a la imagen
            int mensajePaginaBase = (int) Math.ceil((double) totalBytesImagen / tamanoPagina);
    
            // Bucle para procesar toda la imagen y alternar con el mensaje
            outerLoop:
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    // Procesamos los tres colores del píxel: R, G, B
                    String[] colores = {"R", "G", "B"};
                    for (int k = 0; k < 3; k++) {
                        if (referenciasGeneradas >= referenciasTotales) {
                            break outerLoop; // Detener cuando se alcancen las referencias necesarias
                        }
                        if (secuenciaImagen < 15) {
                            int paginaVirtual = referenciaIndex / tamanoPagina;
                            int desplazamiento = referenciaIndex % tamanoPagina;
                            writer.write("Imagen[" + i + "][" + j + "]." + colores[k] + "," + paginaVirtual + "," + desplazamiento + ",R\n");
                            referenciaIndex++;
                            secuenciaImagen++;
                            referenciasGeneradas++;
                        } else {
                            // Alternar entre imagen y mensaje
                            if (alternar) {
                                // Escribir una referencia al mensaje
                                writer.write("Mensaje[" + mensajeIndex + "]," + mensajePaginaBase + "," + (mensajeIndex % tamanoPagina) + ",W\n");
                                mensajeIndex++;
                                alternar = false; // Alternar de nuevo a imagen
                            } else {
                                // Escribir una referencia a la imagen
                                int paginaVirtual = referenciaIndex / tamanoPagina;
                                int desplazamiento = referenciaIndex % tamanoPagina;
                                writer.write("Imagen[" + i + "][" + j + "]." + colores[k] + "," + paginaVirtual + "," + desplazamiento + ",R\n");
                                referenciaIndex++;
                                alternar = true; // Alternar de nuevo a mensaje
                            }
                            referenciasGeneradas++;
                        }
                    }
                }
            }
    
            writer.close();
            System.out.println("Referencias generadas en 'referencias.txt'");
        } catch (IOException e) {
            System.out.println("Error al generar el archivo de referencias.");
            e.printStackTrace();
        }
    }
    

}
