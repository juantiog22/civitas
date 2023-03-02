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
public class CasillaImpuesto extends Casilla {
    
    private float importe;
    
    CasillaImpuesto(float cantidad, String nombre) {
        // Para crear la casilla de tipo impuesto
        super(nombre);
        importe = cantidad;
    }
    
    @Override
    public void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        if (super.jugadorCorrecto(iactual, todos)) {
            super.informe(iactual, todos);
            todos.get(iactual).pagaImpuesto(importe);
        }
    }
    
    @Override 
    public String toString() {
        return "Casilla{ nombre = " + super.getNombre() + ", importe = " + importe + "}";
    }
}