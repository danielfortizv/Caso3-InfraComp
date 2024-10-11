public class Pagina {
    
    private int idPagina;
    private boolean referenciada; 
    private boolean modificada;
    private long ultimoAccesso;

    public Pagina(int idPagina) {
        this.idPagina = idPagina;
        this.referenciada = false;
        this.modificada = false;
        this.ultimoAccesso = System.currentTimeMillis();
    }

    public int getIdPagina() {
        return idPagina;
    }

   public boolean esReferenciada () {
        return referenciada;
    }

    public void setReferenciada(boolean referenciada) {
        this.referenciada = referenciada;
        this.ultimoAccesso = System.currentTimeMillis();
    }

    public boolean esModificada() {
        return modificada;
    }

    public void setModificada(boolean modificada) {
        this.modificada = modificada;
    }

    public long getUltimoAcesso() {
        return ultimoAccesso;
    }

    public void resetReferencia() {
        this.referenciada = false; // Reinica R (bit de referencia)
    }
}
