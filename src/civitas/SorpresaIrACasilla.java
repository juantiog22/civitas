/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author jdrgj
 */
public class SorpresaIrACasilla extends Sorpresa {
    
    private int valor;
    private Tablero tablero;
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            int casillaActual = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(casillaActual, valor);
            int nuevaPosicion = tablero.nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(nuevaPosicion);
            tablero.getCasilla(valor).recibeJugador(actual, todos);
        }
    }
    
    SorpresaIrACasilla(Tablero tablero, int valor, String texto) {
        // Para construir la sorpresa de tipo ir a casilla
        super(texto);
        this.tablero = tablero;
        this.valor = valor;
    }
    
    @Override
    public String toString() {
        return "Sorpresa { " + "texto = " + super.getTexto() + ", valor = " + Integer.toString(valor) + "}";
    }
    
}