    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTexto;

/**
 *
 * @author Juan Francisco Soto Poza
 */
import civitas.CivitasJuego;
import civitas.Dado;
import java.util.ArrayList;

public class Prueba {
    private VistaTextual vista;
    private CivitasJuego juego;
    private Controlador controlador;
    
    public static void main(String []args){
        
        VistaTextual vista = new VistaTextual();
        
        
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Juan");
        nombres.add("Pedro");
        nombres.add("María");
        CivitasJuego juego = new CivitasJuego(nombres);
        
        Dado.getInstance().setDebug(true);
        Controlador controlador = new Controlador(juego,vista);
        


        controlador.juega();
    }
    
    
    
}
