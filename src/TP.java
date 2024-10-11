import java.util.LinkedHashMap;
//Implementa el mecanismo de reemplazo usando el algoritmo NRU al buscar páginas con el bit de referencia en falso.

public class TP {
    private LinkedHashMap<Integer, Pagina> tablaPaginas;
    private int marcos;

    public TP(int marcos) {
        this.tablaPaginas = new LinkedHashMap<>(marcos, 0.75f, true);
        this.marcos = marcos;
    }

    public boolean contienePagina(int idPagina) {
        return tablaPaginas.containsKey(idPagina);
    }

    public void agregarPagina(Pagina pagina) {
        if (tablaPaginas.size() == marcos) {
            reemplazarPagina(pagina);
        }
        tablaPaginas.put(pagina.getIdPagina(), pagina);
    }

    public Pagina getPagina(int idPagina) {
        return tablaPaginas.get(idPagina);
    }

    // Algoritmo NRU: Buscar la página con el bit de referencia en falso y reemplazar
    private void reemplazarPagina(Pagina nuevaPagina) {
        Integer paginaARemover = null;
        for (Pagina p : tablaPaginas.values()) {
            if (!p.esReferenciada()) {
                paginaARemover = p.getIdPagina();
                System.out.println("Reemplazando página " + paginaARemover + " por página " + nuevaPagina.getIdPagina());
                break;
            }
        }
        if (paginaARemover != null) {
            tablaPaginas.remove(paginaARemover);
        }
    }
    

    public void actualizarReferencia(int idPagina, boolean modificada) {
        Pagina pagina = tablaPaginas.get(idPagina);
        if (pagina != null) {
            pagina.setReferenciada(true);
            if (modificada) {
                pagina.setModificada(true);
            }
        }
    }

    public void resetReferencias() {
        for (Pagina p: tablaPaginas.values()) {
            p.resetReferencia();
        }
    }
    
}

