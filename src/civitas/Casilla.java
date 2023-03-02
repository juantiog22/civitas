package civitas;


import java.util.ArrayList;
/**
 *
 * @author Juan Francisco Soto Poza
 */
public class Casilla {
    
    private String nombre;
    
    Casilla(String nombre) {
        // Este constructor es para crear la casilla de tipo descanso
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    
    void informe(int iactual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Ha caído en la casilla el jugador "+todos.get(iactual).getNombre()+ nombre);
    }
    
    public boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos) {
        boolean correcto = false;
        if (iactual <= todos.size())
            correcto = true;
        return correcto;
    }
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        this.informe(iactual, todos);
    }
    
    public String toString() {
        return "Casilla {nombre =" + nombre + "}";
    }
}