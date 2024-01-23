package buscaminasnb; 

public class Casilla {
    private int postFila, posColumna, numMinasAlredeor;
    private boolean mina, abierta;

    public Casilla(int postFila, int posColumna) {
        this.postFila = postFila;
        this.posColumna = posColumna;
    }

    public int getPostFila() {
        return postFila;
    }

    public void setPostFila(int postFila) {
        this.postFila = postFila;
    }

    public int getPosColumna() {
        return posColumna;
    }

    public void setPosColumna(int posColumna) {
        this.posColumna = posColumna;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public int getNumMinasAlrededor() {
        return numMinasAlredeor;
    }

    public void setnumMinasAlredeor(int numMinasAlredeor) {
        this.numMinasAlredeor = numMinasAlredeor;
    }

    public void incrementarNumeroMinasAlrededor() {
        this.numMinasAlredeor++;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }
}
