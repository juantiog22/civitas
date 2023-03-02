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
public class SorpresaSalirCarcel extends Sorpresa {
    
    private MazoSorpresas mazo;
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            super.informe(actual, todos);
            boolean solucion = false;
            for (int i=0; i<todos.size(); i++) {
                if (todos.get(i).tieneSalvoconducto() == true) {
                    solucion = true;
                }
            }
            if (solucion = false) {
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo();
            }
        }
    }
    
    void salirDelMazo() {
        mazo.inhabilitarCartaEspecial(this);
    }
    
    SorpresaSalirCarcel(MazoSorpresas mazo) {
        // Para construir la sorpresa que nos permite salir de la carcel
        super("Puedes salir en libertad de la carcel.");
        this.mazo = mazo;
    }
    
    void usada() {
        mazo.inhabilitarCartaEspecial(this);
    }
    
    @Override
    public String toString() {
        return "Sorpresa { " + "texto = " + super.getTexto() + "}";
    }
}