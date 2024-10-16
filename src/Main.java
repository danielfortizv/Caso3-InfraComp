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
                    generarReferencias();
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
    
        // Pedir el número de marcos de página
        System.out.print("Ingrese el número de marcos de página: ");
        int marcos = scanner.nextInt();
        
        // Pedir el número total de referencias
        System.out.print("Ingrese el número total de referencias: ");
        int totalReferencias = scanner.nextInt();
    
        // Inicializar ManejadorMemoria con el número de marcos
        ManejadorMemoria manejadorMemoria = new ManejadorMemoria(marcos);
    
        // Simulación de acceso a las páginas
        for (int i = 0; i < totalReferencias; i++) {
            // Simulamos que estamos accediendo a una página con ID i
            int idPagina = i % 100;  // Por ejemplo, IDs de páginas entre 0 y 99
            boolean modificada = new Random().nextBoolean(); // Simulación aleatoria de si la página fue modificada
    
            // Acceder a la página y manejar hit o miss
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
