import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\nMenú:");
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
                    //caso2-parrots_mod
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

    public static void calcularDatos() {
        Scanner scanner = new Scanner(System.in);
        // Solicitar el número de marcos de página
        System.out.print("Ingrese el número de marcos de página: ");
        int marcos = scanner.nextInt();
        
        // Solicitar el nombre del archivo de referencias
        String nombreArchivo = "referencias.txt";
        // Inicializar ManejadorMemoria con el número de marcos
        ManejadorMemoria manejadorMemoria = new ManejadorMemoria(marcos);

        try {
            // Leer las referencias del archivo
            File file = new File(nombreArchivo);
            Scanner fileScanner = new Scanner(file);
            
            // Saltar las primeras líneas del encabezado
            while (fileScanner.hasNextLine()) {
                String linea = fileScanner.nextLine().trim();
                
                // Saltar las líneas de encabezado (aquellas que comienzan con 'P=', 'NF=', 'NC=', 'NR=', 'NP=')
                if (linea.startsWith("P=") || linea.startsWith("NF=") || linea.startsWith("NC=") ||
                    linea.startsWith("NR=") || linea.startsWith("NP=")) {
                    continue;
                }
                
                // Ignorar líneas vacías
                if (linea.isEmpty()) {
                    continue;
                }

                // Procesar las referencias (que empiezan con "Imagen" o "Mensaje")
                String[] partes = linea.split(",");

                // Verificar que la línea tenga al menos dos partes (nombre y número de página)
                if (partes.length < 2) {
                    System.out.println("Línea no válida: " + linea);
                    continue; // Saltar esta línea si no está bien formateada
                }

                // La página virtual es la segunda parte de la referencia
                int paginaVirtual = Integer.parseInt(partes[1]);

                // Para simplificar, supondremos que la referencia no es modificada (solo lectura/escritura)
                boolean modificada = partes[3].equals("W"); // Si la última parte es "W", está modificada

                // Acceder a la página (registrará un hit o un miss)
                manejadorMemoria.accederPagina(paginaVirtual, modificada);
            }

            // Cerrar el archivo después de procesar todas las referencias
            fileScanner.close();

            // Imprimir estadísticas al final
            manejadorMemoria.imprimirEstadisticas();

        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado.");
            e.printStackTrace();
        }
    }
    

    public static void esconderMensaje() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la imagen a modificar (Solo pon el nombre sin la extensión): ");
        String imagen = "Archivos/" + scanner.nextLine() + ".bmp";

        System.out.print("Ingrese el nombre del archivo con el mensaje (Sin extensión): ");
        String archivo = "Archivos/" + scanner.nextLine() + ".txt";

        File file = new File(archivo);
        String mensaje = "";
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                mensaje += fileScanner.nextLine() + "\n";
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado.");
            e.printStackTrace();
        }

        Imagen img = new Imagen(imagen);
        img.esconder(mensaje.toCharArray(), mensaje.length());
        img.escribirImagen(imagen);

        System.out.println("Mensaje escondido en la imagen.");

    }

    public static void recuperarMensaje() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la imagen a recuperar (Solo pon el nombre sin la extensión): ");
        String imagen = "Archivos/" + scanner.nextLine() + ".bmp";

        Imagen img = new Imagen(imagen);
        char[] mensajeRecuperado = new char[img.leerLongitud()];
        img.recuperar(mensajeRecuperado, mensajeRecuperado.length);
        
        System.out.println("\nMensaje recuperado: \n" + new String(mensajeRecuperado));
    }
}
