package civitas;


import civitas.Diario;
import civitas.Dado;
import civitas.Sorpresa;
import civitas.TituloPropiedad;
import java.util.ArrayList;
/**
 *
 * @author Juan Francisco Soto Poza
 */

       
public class Jugador implements Comparable<Jugador> {
    
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected boolean encarcelado;
    protected static int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static float PasoPorSalida = 1000;
    protected static float PrecioLibertad = 200;
    protected boolean puedeComprar;
    private float saldo;
    private static float SaldoInicial = 7500;
    private SorpresaSalirCarcel salvoconducto = null;
    private ArrayList<TituloPropiedad> propiedades;
    
    Jugador (String nombre) {
        this.nombre = nombre;
        encarcelado = false;
        numCasillaActual = 0;
        puedeComprar = true;
        propiedades = new ArrayList<>();
        salvoconducto = null;
        saldo = 7500;
    }
    
    protected Jugador (Jugador otro) {
        encarcelado = otro.encarcelado;
        nombre = otro.nombre;
        numCasillaActual = otro.numCasillaActual;
        puedeComprar = otro.puedeComprar;
        saldo = otro.saldo;
        salvoconducto = otro.salvoconducto;
        propiedades = otro.propiedades;
    }
    
    
    boolean cancelarHipoteca(int ip) {
        boolean result = false;
        if (encarcelado == true) {
            return result;
        }
        if (existeLaPropiedad(ip) == true) {
            TituloPropiedad propiedad = propiedades.get(ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            boolean puedoGastar = puedoGastar(cantidad);
            if (puedoGastar == true) {
                result = propiedad.cancelarHipoteca(this);
                if (result == true) {
                    Diario.getInstance().ocurreEvento("El jugador " +nombre+ "cancela la hipoteca de la propiedad " + ip);
                }
            }
        }
        return result;
    }
    
    
    int cantidadCasasHoteles() {
        int cantidad = 0;
        for (int i = 0; i < propiedades.size(); i++) {
            cantidad = cantidad + propiedades.get(i).cantidadCasasHoteles();
        }
        return cantidad;
    }
    
    @Override
    public int compareTo(Jugador otro) {
        return Float.compare(saldo, otro.getSaldo());
    }
    
    
    boolean comprar(TituloPropiedad titulo) {
        boolean result = false;
        if (encarcelado) {
            return result;
        }
        if (puedeComprar == true) {
            float precio = titulo.getPrecioCompra();
            if (puedoGastar(precio) == true) {
                result = titulo.comprar(this);
                if (result == true) {
                    propiedades.add(titulo);
                    Diario.getInstance().ocurreEvento("El jugador " +this+ "compra la propiedad " +titulo.toString());
                    puedeComprar = false;
                }
            }
        }
        return result;
    }
    
    
    
    boolean construirCasa(int ip) {
        boolean result = false;
        if (encarcelado == true) {
            return result;
        }
        else {
            boolean existe = existeLaPropiedad(ip);
            if (existe == true) {
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean puedoEdificarCasa = puedoEdificarCasa(propiedad);
                float precio = propiedad.getPrecioEdificar();
                if (puedoGastar(precio) == true && propiedad.getNumCasas()< getCasasMax()) {
                    puedoEdificarCasa = true;
                }
                if (puedoEdificarCasa == true) {
                    result = propiedad.construirCasa(this);
                }
                if (result == true) {
                    Diario.getInstance().ocurreEvento("El jugador " +nombre+ "construye una casa en la propiedad " + ip);
                }
            }
        }
        return result;
    }
    
    
    
    boolean construirHotel(int ip) {
        boolean result = false;
            if (encarcelado == true) {
                return result;
            }
            else {
                boolean existe = existeLaPropiedad(ip);
                if (existe == true) {
                    TituloPropiedad propiedad = propiedades.get(ip);
                    boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);
                    float precio = propiedad.getPrecioEdificar();
                    if (puedoGastar(precio) == true && propiedad.getNumHoteles()< getHotelesMax() && propiedad.getNumCasas() >= getCasasPorHotel()) {
                        puedoEdificarHotel = true;
                    }
                    if (puedoEdificarHotel == true) {
                        result = propiedad.construirHotel(this);
                    }
                    if (result == true) {
                        Diario.getInstance().ocurreEvento("El jugador " +nombre+ "construye un hotel en la propiedad " + ip);
                    }
                }
            }
        return result;    
    }
    
    
    protected boolean debeSerEncarcelado() {
        if (encarcelado)
            return false;
        else if (this.tieneSalvoconducto()==false) {
            return true;
        }
        else {
            this.perderSalvoconducto();
            Diario.getInstance().ocurreEvento("El jugador se libra de la cárcel");
            return false;
        }
    }
    
    boolean enBancarrota() {
        return saldo <= 0;
    }
    
    boolean encarcelar (int numCasillaCarcel) {
        if (this.debeSerEncarcelado()) {
            this.moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento("Se ha encarcelado al jugador.");
        }
        return encarcelado;
    }
    
    private boolean existeLaPropiedad(int ip) {
        boolean existe = false;
        if (ip>=0 && ip<propiedades.size())
            existe = true;
        return existe;
    }
    
    int getCasasMax() {
        return CasasMax;
    }
    
    int getCasasPorHotel() {
        return CasasPorHotel;
    }
    
    int getHotelesMax() {
        return HotelesMax;
    }
    
    protected String getNombre() {
        return nombre;
    }
    
    int getNumCasillaActual() {
        return numCasillaActual;
    }
    
    private float getPrecioLibertad() {
        return PrecioLibertad;
    }
    
    private float getPremioPorSalida() {
        return PasoPorSalida;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }
    
    boolean getPuedeComprar() {
        return puedeComprar;
    }
    
    protected float getSaldo() {
        return saldo;
    }
    
    Sorpresa getSalvoconducto() {
        return salvoconducto;
    }
    
    
    boolean hipotecar (int ip) {
        boolean result = false;
        if (encarcelado == true) {
            return result;
        }
        if (existeLaPropiedad(ip) == true) {
            TituloPropiedad propiedad = propiedades.get(ip);
            result = propiedad.hipotecar(this);
        }
        if (result == true) {
            Diario.getInstance().ocurreEvento("El jugador "+nombre+"hipoteca la propiedad " +ip);
        }
        return result;
    }
    
    
    public boolean isEncarcelado() {
        return encarcelado;
    }
    
    boolean modificarSaldo(float cantidad) {
        saldo += cantidad;
        Diario.getInstance().ocurreEvento("Saldo aumentado.");
        return true;
    }
    
    boolean moverACasilla(int numCasilla) {
        if (encarcelado) {
            return false;
        }
        else {
            this.numCasillaActual = numCasilla;
            this.puedeComprar = false;
            Diario.getInstance().ocurreEvento("El jugador se ha movido de casilla.");
            return true;
        }
    }
    
    boolean obtenerSalvoconducto(Sorpresa sorpresa) {
        boolean solucion = false;
        if (!encarcelado) {
            salvoconducto = (SorpresaSalirCarcel) sorpresa;
            solucion = true;
        }
        return solucion;
    }
    
    boolean paga(float cantidad) {
        this.modificarSaldo(cantidad*-1);
        return true;
    }
    
    boolean pagaAlquiler(float cantidad) {
        if (encarcelado) {
            return false;
        }
        else {
            this.paga(cantidad);
            Diario.getInstance().ocurreEvento("El jugador " + nombre + " paga " + cantidad + "euros por alquiler.");
            return true;
        }
    }
    
    boolean pagaImpuesto(float cantidad) {
        if (encarcelado) {
            return false;
        }
        else {
            this.paga(cantidad);
            Diario.getInstance().ocurreEvento("El jugador " + nombre + " paga " + cantidad + "euros por impuesto.");
            return true;
        }
    }
    
    boolean pasaPorSalida() {
        modificarSaldo(PasoPorSalida);
        Diario.getInstance().ocurreEvento("El jugador ha pasado por salida.");
        return true;
    }
    
    protected void perderSalvoconducto() {
        salvoconducto.usada();
        salvoconducto = null;
    }
    
    boolean puedeComprarCasilla() {
        if (encarcelado) {
            puedeComprar = false;
        }
        else {
            puedeComprar = true;
        }
        return puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando() {
        return this.saldo >= PrecioLibertad;
    }
    
    private boolean puedoEdificarCasa(TituloPropiedad propiedad) {
        boolean solucion = false;
        float precio = propiedad.getPrecioEdificar();
        if (puedoGastar(precio) && propiedad.getNumCasas() < getCasasMax()) {
            solucion = true;
        }
        return solucion;
    }
    
    private boolean puedoEdificarHotel(TituloPropiedad propiedad) {
        boolean solucion = false;
        float precio = propiedad.getPrecioEdificar();
        if (puedoGastar(precio)) {
            if (propiedad.getNumHoteles() < getHotelesMax()) {
                if (propiedad.getNumCasas() >= getCasasPorHotel()) {
                    solucion = true;
                }
            }
        }
        return solucion;
    }
    
    private boolean puedoGastar(float precio) {
        if (encarcelado) {
            return false;
        }
        else 
            return this.saldo >= precio;
    }
    
    boolean recibe(float cantidad) {
        if (encarcelado) {
            return false;
        }
        else {
            this.modificarSaldo(cantidad);
            return true;
        }
    }
    
    boolean salirCarcelPagando() {
        if (encarcelado && this.puedeSalirCarcelPagando()) {
            this.paga(PrecioLibertad);
            encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador ha salido de la cárcel pagando.");
            return true;
        }
        else
            return false;
    }
    
    boolean salirCarcelTirando() {
        if (Dado.getInstance().salgoDeLaCarcel()) {
            encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador ha salido de la cárcel tirando.");
        }
        else
            encarcelado = true;
        return encarcelado;
    }
    
    boolean tieneAlgoQueGestionar() {
        if (this.propiedades.isEmpty()) {
            return false;
        }
        else
            return true;
    }
    
    boolean tieneSalvoconducto() {
        if (salvoconducto == null) {
            return false;
        }
        else
            return true;
    }
    
    @Override
    public String toString() {
        String mensaje = " ";
        String todas = "";
        String aux;
        
        for (int i=0; i < propiedades.size(); i++) {
            aux = propiedades.get(i).getNombre();
            todas = todas + " ";
            todas = todas + aux;
        }
        mensaje = "\n*********************\n" + getNombre() + "\nEncarcelado: " + encarcelado + "\nPuede comprar: " + puedeComprar
                + "\nSaldo: " + saldo + "\nNúmero de propiedades: " + propiedades.size() + "\nPropiedades: " + todas + "\nNúmero de casilla actual: " + numCasillaActual;
        return mensaje;
    }
    
    boolean vender (int ip) {
        if (encarcelado) {
            return false;
        }
        else if (this.existeLaPropiedad(ip)) {
            propiedades.get(ip).vender(this);
            propiedades.remove(ip);
            Diario.getInstance().ocurreEvento("Se ha vendido la propiedad.");
            return true;
        }
        else
            return false;
    } 
}
