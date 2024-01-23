package buscaminasnb;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class TableroBuscaminas {
    Casilla[][] casillas;
    int numFilas, numColumnas, numMinas, numCasillasAbiertas;
    private Consumer<List<Casilla>> eventoPartidaPerdida, eventoPartidaGanada;
    private Consumer<Casilla> eventoCasillaAbierta;
    //boolean juegoTerminado;

    public TableroBuscaminas(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        inicializarCasillas();
    }

    public void inicializarCasillas() {
        casillas = new Casilla[numFilas][numColumnas];

        for (int i = 0; i < casillas.length; i++) for (int j = 0; j < casillas[i].length; j++) {
            casillas[i][j] = new Casilla(i, j);
            casillas[i][j].setPostFila(i);
            casillas[i][j].setPosColumna(j);
        }
        generarMinas();
    }

    private void generarMinas() {
        int minasGeneradas = 0;

        while (minasGeneradas != numMinas) {
            int fila = (int) (Math.random() * casillas.length);
            int columna = (int) (Math.random() * casillas[0].length);

            if (!casillas[fila][columna].isMina()) {
                casillas[fila][columna].setMina(true);
                minasGeneradas++;
            }
        }
        actualizarNumMinasAlrededor();
    }

    public void imprimirTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j <casillas[i].length; j++) {
                System.out.print(casillas[i][j].isMina() ? "*" : "O");
            }
            System.out.print("\n");
        }
    }

    private void imprimirPistas() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].getNumMinasAlrededor());
            }
            System.out.print("\n");
        }
    }

    public void actualizarNumMinasAlrededor() {
        for(int i = 0; i <casillas.length; i++) for (int j = 0; j < casillas[i].length; j++) {
            if (casillas[i][j].isMina()) {
                List<Casilla> casillasAlrededor = obtenerCasillasAlrededor(i, j);
                casillasAlrededor.forEach(casilla -> casilla.incrementarNumeroMinasAlrededor());
            }
        }
    }

    private List<Casilla> obtenerCasillasAlrededor(int postFila, int postColumna) {
        List<Casilla> listaCasillas = new LinkedList<>();

        for (int i = 0; i < 8; i++) {
            int fila = postFila, columna = postColumna;

            switch (i) {
                case 0: fila --; break; // Arriba
                case 1: fila --; columna++; break; // Arriba derecha
                case 2: columna++; break; // Derecha
                case 3: columna++; fila++; break; // Derecha abajo
                case 4: fila++; break; // Abajo
                case 5: fila++; columna--; break; // Abajo izquierda
                case 6: columna--; break; // Izquierda
                case 7: fila--; columna--; break; // Izquierda arriba
            }
            if(fila >= 0 && fila < this.casillas.length && columna >= 0 && columna < this.casillas[0].length) {
                listaCasillas.add(this.casillas[fila][columna]);
            }
        }
        return listaCasillas;
    }

    List<Casilla> obtenerCasillasConMinas() {
        List<Casilla> casillasConMinas = new LinkedList<>();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if(casillas[i][j].isMina()) {
                    casillasConMinas.add(casillas[i][j]);
                }
            }
        }
        return casillasConMinas;
    }

    public void seleccionarCasilla(int posFila, int posColumna) {
        eventoCasillaAbierta.accept(this.casillas[posFila][posColumna]);

        if(this.casillas[posFila][posColumna].isMina()) {
            eventoPartidaPerdida.accept(obtenerCasillasConMinas());
        }
        else if (this.casillas[posFila][posColumna].getNumMinasAlrededor() == 0) {
            marcarCasillaAbierta(posFila, posColumna);
            List<Casilla> casillasAlrededor = obtenerCasillasAlrededor(posFila, posColumna);

            for (Casilla casilla : casillasAlrededor) {
                if (!casilla.isAbierta()) {
                    seleccionarCasilla(casilla.getPostFila(), casilla.getPosColumna());
                }
            }
        } else {
            marcarCasillaAbierta(posFila, posColumna);
        }
        if(partidaGanada()) {
            eventoPartidaGanada.accept(obtenerCasillasConMinas());
        }
    }

    public void setEventoPartidaPerdida(Consumer<List<Casilla>> eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }

    public void setEventoCasillaAbierta(Consumer<Casilla> eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    boolean partidaGanada() {
        return numCasillasAbiertas >= (numFilas * numColumnas) - numMinas;
    }

    void marcarCasillaAbierta(int posFila, int posColumna) {
        if(!this.casillas[posFila][posColumna].isAbierta()) {
            numCasillasAbiertas++;
            this.casillas[posFila][posColumna].setAbierta(true);
        }
    }

    public void setEventoPartidaGanada(Consumer<List<Casilla>> eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }

    public static void main(String[] args) {
        TableroBuscaminas tablero = new TableroBuscaminas(5, 5, 5);
        tablero.imprimirTablero();
        System.out.println("----------");
        tablero.imprimirPistas();
    }
}