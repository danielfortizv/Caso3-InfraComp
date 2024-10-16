import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("Menú:");
            System.out.println("1. Generar referencias");
            System.out.println("2. Calcular fallas de página, hits y tiempos");
            System.out.println("3. Esconder mensaje en imagen");
            System.out.println("4. Recuperar mensaje de imagen");
            System.out.println("5. Salir");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    Simulador.generarReferencias();
                    break;
                case 2:
                    calcularDatos();
                    break;
                case 3:
                    esconderMensaje();
                    break;
                case 4:
                    recuperarMensaje();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        scanner.close();
    }

    public static void generarReferencias() {  
    }

    public static void calcularDatos() {
        Scanner scanner = new Scanner(System.in);
    
        // Pedir el tamaño de página (en bytes)
        System.out.print("Ingrese el tamaño de la página (en bytes): ");
        int tamanoPagina = scanner.nextInt();
        
        // Pedir el número de caracteres (tamaño del mensaje)
        System.out.print("Ingrese el tamaño del mensaje (en caracteres): ");
        int tamanoMensaje = scanner.nextInt();
        
        // Pedir el número de marcos de página
        System.out.print("Ingrese el número de marcos de página: ");
        int marcos = scanner.nextInt();
        
        // Pedir el número total de referencias
        System.out.print("Ingrese el número total de referencias: ");
        int totalReferencias = scanner.nextInt();
    
        // Calcular cuántas páginas necesitamos para almacenar el mensaje
        int totalPaginas = (int) Math.ceil((double) tamanoMensaje / tamanoPagina);
    
        // Inicializar ManejadorMemoria con el número de marcos
        ManejadorMemoria manejadorMemoria = new ManejadorMemoria(marcos);
    
        // Simulación de acceso a las páginas
        for (int i = 0; i < totalReferencias; i++) {
            // Determinar a qué página pertenece esta referencia
            int idPagina = (i % totalPaginas);  // Simulamos que accedemos a las páginas
    
            // Determinar si esta página ya está cargada en los marcos (hit) o si debe cargarse (miss)
            boolean modificada = false;  // En este caso, no simulamos modificaciones
            manejadorMemoria.accederPagina(idPagina, modificada);
        }
    
        // Imprimir las estadísticas al final de la simulación
        manejadorMemoria.imprimirEstadisticas();
    }
    

    public static void esconderMensaje() {
    }

    public static void recuperarMensaje() {
    }
}
