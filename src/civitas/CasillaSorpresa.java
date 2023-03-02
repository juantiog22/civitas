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
public class CasillaSorpresa extends Casilla {
    
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;
    
    CasillaSorpresa(MazoSorpresas mazo, String nombre) {
        // Para crear la casilla tipo sorpresa
        super(nombre);
        this.mazo = mazo;
    }
    
    @Override
    public void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        if (super.jugadorCorrecto(iactual, todos)) {
            sorpresa = mazo.siguiente();
            super.informe(iactual, todos);
            sorpresa.aplicarAJugador(iactual, todos);
        }
    }
    
    @Override 
    public String toString() {
        return "Casilla { nombre = " + super.getNombre() + "}";
    }
}

