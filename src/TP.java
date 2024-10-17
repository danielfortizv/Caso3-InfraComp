import java.util.LinkedHashMap;

public class TP {
    private LinkedHashMap<Integer, Pagina> tablaPaginas;
    private int marcos;

    public TP(int marcos) {
        this.tablaPaginas = new LinkedHashMap<>(marcos, 0.75f, true);
        this.marcos = marcos;
    }

    // Verifica si la página está en la tabla
    public synchronized boolean contienePagina(int idPagina) {
        return tablaPaginas.containsKey(idPagina);
    }

    // Agrega una nueva página, aplicando reemplazo si los marcos están llenos
    public synchronized void agregarPagina(Pagina pagina) {
        if (tablaPaginas.size() == marcos) {
            reemplazarPagina();
        }
        tablaPaginas.put(pagina.getIdPagina(), pagina);
    }

    // Algoritmo de reemplazo basado en envejecimiento: Encuentra y reemplaza la página más envejecida
    public synchronized void reemplazarPagina() {
        Pagina paginaARemover = null;
        long minContador = Long.MAX_VALUE;

        // Busca la página con el contador de envejecimiento más bajo
        for (Pagina p : tablaPaginas.values()) {
            if (p.getContadorEnvejecimiento() < minContador) {
                minContador = p.getContadorEnvejecimiento();
                paginaARemover = p;
            }
        }

        if (paginaARemover != null) {
            System.out.println("Reemplazando página " + paginaARemover.getIdPagina());
            tablaPaginas.remove(paginaARemover.getIdPagina());
        }
    }

    // Actualiza la referencia de la página y marca que ha sido referenciada
    public synchronized void actualizarReferencia(int idPagina, boolean modificada) {
        Pagina pagina = tablaPaginas.get(idPagina);
        if (pagina != null) {
            pagina.setReferenciada(true); // Marcar como referenciada
            if (modificada) {
                pagina.setModificada(true); // Si fue modificada, marcarla también
            }
        }
    }

    // Método para envejecer todas las páginas: divide el contador de envejecimiento por 2
    public synchronized void envejecerPaginas() {
        for (Pagina p : tablaPaginas.values()) {
            p.envejecer();
        }
    }

    // Resetea las referencias (equivalente a borrar el bit de referencia)
    public synchronized void resetReferencias() {
        for (Pagina p: tablaPaginas.values()) {
            p.resetReferencia();
        }
    }

    // Verifica si los marcos están llenos
    public synchronized boolean estaLleno() {
        return tablaPaginas.size() == marcos;
    }
}
