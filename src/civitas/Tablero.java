package civitas;


import civitas.Casilla;
import java.util.ArrayList;

/**
 *
 * @author Juan Francisco Soto Poza
 * Práctica 1 
 */
public class Tablero {
    
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;
    
    Tablero(int numCasillaCarcel) {
        if (numCasillaCarcel >= 1) {
            this.numCasillaCarcel = numCasillaCarcel;
        }
        else this.numCasillaCarcel = 1;
        
        casillas = new ArrayList();
        Casilla Salida = new Casilla("Salida");
        casillas.add(Salida);
        
        porSalida = 0;
        
        tieneJuez = false;
    }
    
    private boolean correcto() {
        boolean solucion = false;
        if (casillas.size() >= numCasillaCarcel && tieneJuez == true) {
            solucion = true;
        }
        return solucion;
    }
    
    private boolean correcto(int numCasilla) {
        if (correcto()==true && casillas.size()> numCasilla)
            return true;
        else {
            return false;
        }
    }
    
    int getCarcel() {
        return numCasillaCarcel;
    }
    
    int getPorSalida() {
        if (porSalida > 0) {
            int aux = porSalida;
            porSalida = porSalida-1;
            return aux;
        }
        else return porSalida;
    }
    
    void añadeCasilla (Casilla casilla) {
        if (casillas.size() == numCasillaCarcel) {
            Casilla carcel = new Casilla ("Cárcel");
            casillas.add(carcel);
        }
        if (casilla instanceof CasillaJuez) {
            añadeJuez();
        }
        else {
            casillas.add(casilla);
        }
    }
    
    void añadeJuez() {
        if (tieneJuez==false) {
            Casilla Juez = new CasillaJuez(numCasillaCarcel, "Juez");
            casillas.add(Juez);
            tieneJuez=true;
        }
    }
    
    Casilla getCasilla(int numCasilla) {
        if (correcto(numCasilla)) {
            return casillas.get(numCasilla);
        }
        else {
            return null;
        }
    }
    
    int nuevaPosicion(int actual, int tirada) {
        if (correcto()) {
            int aux=actual+tirada;
            if (aux>casillas.size()) {
                aux = aux%casillas.size();
                porSalida++;
            }
            return aux;
        }
        else {
            return -1;
        }
    }
    
    int calcularTirada (int origen, int destino) {
        int aux;
        if (origen<destino) {
            aux = origen - destino;
        }
        else {
            aux = casillas.size() - origen + destino;
        }
        return aux;
    }
}