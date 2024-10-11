public class ManejadorMemoria {
    private TP tp;
    private int hitsTotales;
    private int missesTotales;

    public ManejadorMemoria(int marcos) {
        this.tp = new TP(marcos);
        this.hitsTotales = 0;
        this.missesTotales = 0;
    }

    public void accederPagina(int idPagina, boolean modificada) {
        if (tp.contienePagina(idPagina)) {
            hitsTotales++;
            tp.actualizarReferencia(idPagina, modificada);
            System.out.println("Hit: Accediendo a la página" + idPagina);
        } else {
            missesTotales++;
            Pagina nuevaPagina = new Pagina(idPagina);
            tp.agregarPagina(nuevaPagina);
            System.out.println("Miss: Fallo de página. Cargando página" + idPagina);
            
        }
    }

    public void imprimirEstadisticas() {
        System.out.println("Hits: " + hitsTotales);
        System.out.println("Misses: " + missesTotales);
    }

    //Resetea los bits R para el envejecimiento de las páginas
    public void correrAlgoritmo() {
        tp.resetReferencias();
    }
    
}
