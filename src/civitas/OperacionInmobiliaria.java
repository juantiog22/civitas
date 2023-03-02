package civitas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan Francisco Soto Poza
 */
public class OperacionInmobiliaria {
    private int numPropiedad;
    private GestionesInmobiliarias gestion;
    
    public GestionesInmobiliarias getGestion(){
        return gestion;
    }
    public int getNumPropiedad(){
        return numPropiedad;   
    }
    public OperacionInmobiliaria(GestionesInmobiliarias gest, int ip){
        gest = this.getGestion();
        ip = this.getNumPropiedad();
    }
}
