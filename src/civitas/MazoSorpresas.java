package civitas;


import civitas.Diario;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Juan Francisco Soto Poza
 */


public class MazoSorpresas {
        private ArrayList <Sorpresa> sorpresas;
        private ArrayList <Sorpresa> cartasEspeciales;
        private boolean barajada;
        private int usadas;
        private boolean debug;
        private Sorpresa ultimaSorpresa;
        
        private void init()
        {
            sorpresas = new ArrayList<>();
            cartasEspeciales = new ArrayList<>();
            usadas = 0;
            barajada = false;
        }
        
        //Constructor con parámetro
        MazoSorpresas(boolean d)
        {
            init();
            debug = d;
            if(debug){
                Diario.getInstance().ocurreEvento("El modo debug está activado");
            }
        }
        //Constructor sin parámetro
        MazoSorpresas()
        {
         init();
         debug = false;
        }
        
        public void alMazo(Sorpresa s)
        {
            if(!barajada){
                sorpresas.add(s);
            }
        }
        
        public void barajar() {
            Collections.shuffle(sorpresas);
        }

        Sorpresa siguiente()
        {
          
          if(!barajada || usadas == sorpresas.size()){
              
              if(!debug){
                  barajar();
                  usadas = 0;
                  barajada = true;
              }
              usadas++;
              
            ultimaSorpresa = sorpresas.get(0);
              
              sorpresas.remove(0);
              sorpresas.add(ultimaSorpresa);    
              
          }  
          return ultimaSorpresa;
        }
        
        	
        void inhabilitarCartaEspecial(Sorpresa sorpresa)
        {
            if(sorpresas.contains(sorpresa)){
                sorpresas.remove(sorpresa);
                cartasEspeciales.add(sorpresa);
                Diario.getInstance().ocurreEvento("Se inhabilita la carta especial");
            }
        }
        
        void habilitarCartaEspecial(Sorpresa sorpresa)
        {
            if(cartasEspeciales.contains(sorpresa)){
                cartasEspeciales.remove(sorpresa);
                sorpresas.add(sorpresa);
                Diario.getInstance().ocurreEvento("Se habilita la carta especial");
            }
        }

    void inhabilitarCartaEspecial(MazoSorpresas mazo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void habilitarCartaEspecial(MazoSorpresas mazo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}