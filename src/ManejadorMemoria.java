public class ManejadorMemoria {
    private TP tp;
    private int hitsTotales;
    private int missesTotales;
    private int tiempoTotal; // Para sumar el tiempo total de acceso

    private static final int TIEMPO_HIT = 25; // Tiempo de acceso por hit en nanosegundos
    private static final int TIEMPO_MISS = 10000; // Tiempo de acceso por fallo en nanosegundos

    public ManejadorMemoria(int marcos) {
        this.tp = new TP(marcos);
        this.hitsTotales = 0;
        this.missesTotales = 0;
        this.tiempoTotal = 0; // Inicializar el tiempo total
    }

    public void accederPagina(int idPagina, boolean modificada) {
        if (tp.contienePagina(idPagina)) {
            hitsTotales++;
            tp.actualizarReferencia(idPagina, modificada);
            tiempoTotal += TIEMPO_HIT; // Sumamos el tiempo de hit
            System.out.println("Hit: Accediendo a la página " + idPagina);
        } else {
            missesTotales++;
            tiempoTotal += TIEMPO_MISS; // Sumamos el tiempo de fallo de página

            // Si no hay espacio en los marcos, aplicamos el algoritmo de reemplazo (envejecimiento)
            if (tp.estaLleno()) {
                tp.reemplazarPagina(); // Delega el reemplazo a la clase TP
            }

            // Cargar la nueva página en los marcos
            Pagina nuevaPagina = new Pagina(idPagina);
            tp.agregarPagina(nuevaPagina);
            System.out.println("Miss: Fallo de página. Cargando página " + idPagina);
        }
    }

    public void imprimirEstadisticas() {
        System.out.println("Hits: " + hitsTotales);
        System.out.println("Misses: " + missesTotales);
        System.out.println("Tiempo total de acceso: " + tiempoTotal + " ns");
    }

    // Resetea los bits R para el envejecimiento de las páginas
    public void correrAlgoritmo() {
        tp.resetReferencias(); // Restablece los bits de referencia
    }

    // Envejece las páginas en los marcos (debería llamarse periódicamente)
    public void envejecerPaginas() {
        tp.envejecerPaginas(); // Actualiza los contadores de envejecimiento
    }
}
