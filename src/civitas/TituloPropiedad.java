package civitas;


import civitas.Jugador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan Francisco Soto Poza
 */
public class TituloPropiedad {
    private Jugador propietario;
    private float alquilerBase;
    private static float factorInteresesHipoteca;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    
    void actualizaPropietarioPorConversion(Jugador jugador){
        propietario = jugador;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        boolean result = false;
        if(hipotecado) {
            if(esEsteElPropietario(jugador)){
                propietario.paga(getImporteCancelarHipoteca());
                hipotecado = false;
                result = true;
            }
        }
        return result;
        
    }
    
    int cantidadCasasHoteles(){
       return numCasas + numHoteles; 
    }
    
    boolean comprar(Jugador jugador){
        boolean result = false;
        if(!tienePropietario()){
            propietario = jugador;
            result = true;
        }
        jugador.paga(precioCompra);
        return result;
    }
    
    boolean construirCasa(Jugador jugador){
        boolean result= false;
        if(esEsteElPropietario(jugador)){
            jugador.paga(precioEdificar);
            numCasas++;
            result = true;
        }
        return result;
        
    }
    
    boolean construirHotel(Jugador jugador){
        boolean result = false;
        if(esEsteElPropietario(jugador)){
            jugador.paga(precioEdificar);
            numHoteles++;
            result = true;
        }
        return result;
    }
    
    boolean derruirCasas(int n, Jugador jugador){
        boolean derruidas = false;
        if(this.esEsteElPropietario(jugador) && this.numCasas >= n){
            numCasas = numCasas - n;
            derruidas = true;
        }
        return derruidas;
        
    }
    
    private boolean esEsteElPropietario(Jugador jugador){
        boolean cierto = false;
        if(this.getPropietario()== jugador){
            cierto = true;
        }
        return cierto;
    }
    
    public boolean getHipotecado(){
        return this.hipotecado;
    }
    
    float getImporteCancelarHipoteca(){
        return this.hipotecaBase * this.factorInteresesHipoteca;
    }
    
    private float getImporteHipoteca(){
       return this.hipotecaBase;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    int getNumCasas(){
        return this.numCasas;
    }
    
    int getNumHoteles(){
        return this.numHoteles;
    }
    
    private float getPrecioAlquiler(){
        if(this.hipotecado || this.propietarioEncarcelado()){
            this.alquilerBase = 0;
        }
        return this.alquilerBase;
    }
    
    float getPrecioCompra(){
        return this.precioCompra;
    }
    
    float getPrecioEdificar(){
        return this.precioEdificar;
    }
    
    private float getPrecioVenta(){
        return this.getPrecioCompra() + this.getPrecioEdificar()*this.factorRevalorizacion;
    }
    
    Jugador getPropietario(){
        return this.propietario;
    }
    
    boolean hipotecar(Jugador jugador){
        boolean salida = false;
        if(!hipotecado && this.esEsteElPropietario(jugador)){
            jugador.recibe(this.getImporteHipoteca());
            hipotecado = true;
            salida = true;
        
        }
        return salida;
    }
    
    private boolean propietarioEncarcelado(){
       boolean encarcelado =false;
       if(this.propietario.isEncarcelado()){
           encarcelado =true;
       }
       return encarcelado;
    }
    
    boolean tienePropietario(){
            boolean tienePropietario = false;
            if(propietario != null){
                tienePropietario = true;
            }
        return tienePropietario;
            
    }
    
    TituloPropiedad(String nom,float ab,float fr, float hb, float pc, float pe){
        this.propietario = null;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.hipotecado = false;
        
        this.nombre = nom;
        this.alquilerBase = ab;
        this.factorRevalorizacion = fr;
        this.hipotecaBase = hb;
        this.precioCompra = pc; 
        this.precioEdificar = pe;
        
        
    }
    
    @Override
    public String toString(){
        String s= "Título Propiedad {Nombre =" + nombre;
        if(propietario != null) {
            s +=  "Propietario=" + propietario.getNombre();
        }
               s += ", Número de casas=" + numCasas+ ", Número de hoteles" + numHoteles + ",Hipotecado= "+ hipotecado 
                + ", Alquiler Base" + alquilerBase + ", Factor de Revalorizacion" + factorRevalorizacion + ", Factor de intereses de hipoteca" + factorInteresesHipoteca
                 + ", Precio compra" + precioCompra + ", Precio Edificar" + precioEdificar;

        s+= '}';
        return s;
        
    }
    
    void tramitarAlquiler(Jugador jugador){
       this.tienePropietario();
       if( !this.esEsteElPropietario(jugador)){
           float precio = this.getPrecioAlquiler();
           jugador.pagaAlquiler(precio);
           propietario.recibe(precio);
       }
       
    }
    
    boolean vender(Jugador jugador){
        boolean vender = false;
        if(jugador == this.propietario && !this.hipotecado){
            this.propietario.recibe(this.getPrecioVenta());
            
            this.numCasas = this.numHoteles = 0;
            vender = true;
        }
        return vender;
    }
    
    
    
}
