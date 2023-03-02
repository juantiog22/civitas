package civitas;


import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan Francisco Soto Poza
 */
public class Dado {
    private Random random;
    private int ultimoResultado;
    private boolean debug; 
    
    private static final Dado instance = new Dado();
    private static final int SalidaCarcel = 5; 
    
    private Dado()
    {
        random = new Random();
        ultimoResultado = -1;
        debug = false;
    }
    
    static public Dado getInstance() 
    {
        return instance;
    }
    
    int tirar()
    {
        int numero = 0;
        return numero = (int) (Math.random() *6) + 1;
        
        
    }
    
    boolean salgoDeLaCarcel()
    {
        boolean sale = false;
        if(tirar() == SalidaCarcel){
            sale = true;
        }
        return sale;
    }
    
    public int quienEmpieza(int n)
    {
        
        
        return random.nextInt(n);
        
    }
    
    public void setDebug(Boolean d)
    {
     debug = d;   
     Diario.getInstance().ocurreEvento("Se modifica el atributo debug");
    }
    
    int getUltimoResultado()
    {
        return ultimoResultado;
    }
    
    

}
