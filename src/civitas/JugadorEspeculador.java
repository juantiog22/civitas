/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author jdrgj
 */
public class JugadorEspeculador extends Jugador {
    
    private static int FactorEspeculador = 2;
    private float fianza;
    private boolean especulador;
    
    JugadorEspeculador(Jugador jugador, float fianza) {
        super(jugador);
        this.fianza = fianza;
        especulador = true;
        for (int i = 0; i < super.getPropiedades().size(); i++) {
            super.getPropiedades().get(i).actualizaPropietarioPorConversion(this);
        }
    }
    
    @Override 
    boolean encarcelar(int numCasillaCarcel) {
        boolean solucion = false;
        if (!super.encarcelado) {
            if (!super.tieneSalvoconducto()) {
                if (fianza >= super.getSaldo()) {
                    solucion = true;
                }
                else {
                    super.paga(fianza);
                    Diario.getInstance().ocurreEvento("El jugador " + super.getNombre() + " se libra de la carcel pagando una fianza.");
                }
            }
            else {
                solucion = false;
                super.perderSalvoconducto();
                Diario.getInstance().ocurreEvento("El jugador " + super.getNombre() + " se libra de la carcel usando el salvoconducto");
            }
        }
        return solucion;
    }
    
    @Override
    int getCasasMax() {
        return (super.getCasasMax() * FactorEspeculador);
    }
    
    @Override
    int getHotelesMax() {
        return (super.getHotelesMax() * FactorEspeculador);
    }
    
    @Override
    boolean pagaImpuesto(float cantidad) {
        boolean solucion = false;
        if (!encarcelado) {
            float impuesto = cantidad / 2;
            solucion = this.paga(impuesto);
            Diario.getInstance().ocurreEvento("El jugador " + super.getNombre() + " paga " + impuesto + " euros en forma de impuesto");
        }
        return solucion;
    }
    
    @Override
    public String toString() {
        return "Jugador { nombre = " + super.getNombre() + ", encarcelado = " + Boolean.toString(super.isEncarcelado())
                + ", numCasillaActual = " + super.getNumCasillaActual() + ", puedeComprar = " + super.getPuedeComprar() + 
                ", saldo = " + super.getSaldo() + ", especulador = " + Boolean.toString(especulador) + "}";
    }
}