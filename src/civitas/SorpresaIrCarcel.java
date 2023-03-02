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
public class SorpresaIrCarcel extends Sorpresa {
    
    private Tablero tablero;
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
    SorpresaIrCarcel(Tablero tablero) {
        // Para construir la sorpresa de tipo irCarcel
        super("Debes de ir a la carcel");
        this.tablero = tablero;
    }
    
    @Override
    public String toString() {
        return "Sorpresa { " + "texto = " + super.getTexto() + "}";
    }
    
}
