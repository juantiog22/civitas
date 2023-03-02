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
public class SorpresaPorCasaHotel extends Sorpresa {
    
    private int valor;
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            todos.get(actual).modificarSaldo(valor*todos.get(actual).cantidadCasasHoteles());
        }
    } 
    
    SorpresaPorCasaHotel(int valor, String texto) {
        super(texto);
        this.valor = valor;
    }
    
    @Override
    public String toString() {
        return "Sorpresa { " + "texto = " + super.getTexto() + ", valor = " + Integer.toString(valor) + "}";
    }
}
