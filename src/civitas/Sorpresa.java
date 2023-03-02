package civitas;




import civitas.MazoSorpresas;
import civitas.Jugador;
import civitas.Diario;
import civitas.Casilla;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan Francisco Soto Poza
 */
public abstract class Sorpresa {
    
    private String texto;
    
    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
    void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Se está aplicando una sorpresa al jugador " + todos.get(actual).getNombre() + ",\nLa sorpresa es la siguiente " + toString());
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        boolean correcto = false;
        if (actual < todos.size() && actual >= 0)
            correcto = true;
        return correcto;
    }
    
    String getTexto() {
        return texto;
    }
    
    Sorpresa (String texto) {
        this.texto = texto;
    }
    
    
    @Override
    public String toString() {
        return "Sorpresa{" + "texto = " + texto + "}";
    }
}
