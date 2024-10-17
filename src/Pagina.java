public class Pagina {
    private int idPagina;
    private boolean referenciada; 
    private boolean modificada;
    private long contadorEnvejecimiento; // Contador de envejecimiento

    public Pagina(int idPagina) {
        this.idPagina = idPagina;
        this.referenciada = false;
        this.modificada = false;
        this.contadorEnvejecimiento = 0;
    }

    public int getIdPagina() {
        return idPagina;
    }

    public boolean esReferenciada() {
        return referenciada;
    }

    public void setReferenciada(boolean referenciada) {
        this.referenciada = referenciada;
    }

    public boolean esModificada() {
        return modificada;
    }

    public void setModificada(boolean modificada) {
        this.modificada = modificada;
    }

    public long getContadorEnvejecimiento() {
        return contadorEnvejecimiento;
    }

    // Método para envejecer la página
    public void envejecer() {
        contadorEnvejecimiento = contadorEnvejecimiento >> 1; // Dividir el contador por 2
        if (referenciada) {
            contadorEnvejecimiento |= (1L << 31); // Añadir 1 en el bit más alto si está referenciada
        }
        referenciada = false; // Resetear el bit de referencia después de envejecer
    }

    // Método para resetear la referencia
    public void resetReferencia() {
        this.referenciada = false;
    }
}
