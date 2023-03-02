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
public class SorpresaPorJugador extends Sorpresa {
    
    private int valor;
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            SorpresaPagarCobrar sorpresa1 = new SorpresaPagarCobrar(valor*-1, "PAGARCOBRAR");
            SorpresaPagarCobrar sorpresa2 = new SorpresaPagarCobrar(valor*(todos.size()-1), "PAGARCOBRAR");
            for (int i = 0; i < actual; i++) {
                sorpresa1.aplicarAJugador(i, todos);
            }
            for (int i = actual + 1; i < todos.size(); i++) {
                sorpresa1.aplicarAJugador(i, todos);
            }
            sorpresa2.aplicarAJugador(actual, todos);
        }   
    }
    
    SorpresaPorJugador(int valor, String texto) {
        // Para el resto de sorpresas
        super(texto);
        this.valor = valor;
    }
    
    @Override
    public String toString() {
        return "Sorpresa { " + "texto = " + super.getTexto() + ", valor = " + Integer.toString(valor) + "}";
    } 
}
