/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JuegoTexto;

import civitas.CivitasJuego;
import civitas.Casilla;
import civitas.Jugador;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;
import civitas.TituloPropiedad;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Juan Francisco Soto Poza
 */
public class VistaTextual {
    CivitasJuego juegoModel; 
    private Scanner in;
    private int iPropiedad;
    private int iGestion;
    private static String separador = "=====================";

    VistaTextual () {
      in = new Scanner (System.in);
    }
    
    void mostrarEstado(String estado) {
      System.out.println (estado);
    }

    public void pausa() {
      System.out.print ("Pulsa una tecla");
      in.nextLine();
    }
    public void actualizarVista(){
        System.out.print("El jugador actual es: " + juegoModel.getJugadorActual().toString());
        Casilla c = juegoModel.getCasillaActual();
        System.out.print("La casilla actual es: " + c.toString());
        
    }
    public Respuestas comprar(){
        int opcion = menu ("Decide si comprar la casilla a la que ha llegado",
                    new ArrayList<> (Arrays.asList("Sí","No")));
        return (Respuestas.values()[opcion]);
    }
    
    public void gestionar(){
        ArrayList<String> lista = new ArrayList<String>();
        Jugador jugadorActual = juegoModel.getJugadorActual();
        ArrayList<TituloPropiedad> propiedades = jugadorActual.getPropiedades();
        
        for (TituloPropiedad a : propiedades){
           lista.add(a.getNombre());
            
        }
        iGestion = menu("Elige la gestión imboliaria", lista);
        iPropiedad = menu ("Índice propiedad jugador sobre el que se realiza la gestión",
                            new ArrayList<> (Arrays.asList("Vender","Hipotecar", "Cancelar Hipoteca",
                            "Construir Casa", "Construir Hotel", "Terminar")));
        
        
    }
    public int getGestion(){
        return iGestion;
    }
    public int getPropiedad(){
        return iPropiedad;
    }
    int leeEntero (int max, String msg1, String msg2) {
        Boolean ok;
        String cadena;
        int numero = -1;
        do {
          System.out.print (msg1);
          cadena = in.nextLine();
          try {  
            numero = Integer.parseInt(cadena);
            ok = true;
          } catch (NumberFormatException e) { // No se ha introducido un entero
            System.out.println (msg2);
            ok = false;  
          }
          if (ok && (numero < 0 || numero >= max)) {
            System.out.println (msg2);
            ok = false;
          }
        } while (!ok);

        return numero;
    }
    
    int menu(String titulo, ArrayList<String> lista){
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
        
    }
    void mostrarEventos(){
        while(Diario.getInstance().eventosPendientes()){
            System.out.print("Los eventos pendietentes del diario son: "+ Diario.getInstance().leerEvento());
            
        }
       
    }
    public void mostrarSiguienteOperacion(OperacionesJuego operacion){
        System.out.print("La siguiente operación que se va a realizar será: "+ operacion.toString());
    }

    public SalidasCarcel salirCarcel(){
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
    new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
    }
    public void setCivitasJuego(CivitasJuego civitas){
        juegoModel=civitas;
        this.actualizarVista();

    }
    
}
