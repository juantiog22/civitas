

package JuegoTexto;


import JuegoTexto.VistaTextual;
import civitas.CivitasJuego;
import civitas.Diario;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;

/**
 *
 * @author Juan Francisco Soto Poza
 */
public class Controlador {
    
    private CivitasJuego juego;
    private VistaTextual vista;
    
    public Controlador (CivitasJuego juego, VistaTextual vista) {
        this.juego = juego;
        this.vista = vista;
    }
    
    public void juega() {
        vista.setCivitasJuego(juego);
        while (!juego.finalDelJuego()) {
            vista.actualizarVista();
            System.out.println("Se debe realizar una pausa");
            vista.pausa();
            
            OperacionesJuego siguiente = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(siguiente);
            if (siguiente != OperacionesJuego.PASAR_TURNO) {
                vista.mostrarEventos();
            }
            
            boolean fin = juego.finalDelJuego();
            if (fin == false) {
                if (null != siguiente) switch (siguiente) {
                    case COMPRAR:
                        Respuestas respuesta = vista.comprar();
                        if (respuesta == Respuestas.SI) {
                            juego.comprar();
                            juego.siguientePasoCompletado(OperacionesJuego.COMPRAR);
                        }   break;
                    case GESTIONAR:
                        vista.gestionar();
                        int gestion = vista.getGestion();
                        int propiedad = vista.getPropiedad();
                        GestionesInmobiliarias tipo = GestionesInmobiliarias.values()[gestion];
                        OperacionInmobiliaria operacion = new OperacionInmobiliaria(tipo, propiedad);
                        System.out.println(operacion.getNumPropiedad()+ "es la propiedad");
                        switch(tipo) {
                            case VENDER:
                                juego.vender(operacion.getNumPropiedad());
                                break;
                            case HIPOTECAR:
                                juego.hipotecar(operacion.getNumPropiedad());
                                break;
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(operacion.getNumPropiedad());
                                break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(operacion.getNumPropiedad());
                                break;
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(operacion.getNumPropiedad());
                                break;
                            case TERMINAR:
                                juego.siguientePasoCompletado(OperacionesJuego.GESTIONAR);
                                break;
                        }   break;
                    case SALIR_CARCEL:
                        SalidasCarcel salida = vista.salirCarcel();
                        if (salida == SalidasCarcel.PAGANDO) {
                            juego.salirCarcelPagando();
                        }
                        else {
                            juego.salirCarcelTirando();
                        }   juego.siguientePasoCompletado(OperacionesJuego.SALIR_CARCEL);
                        break;
                }
            }
  
        }
        juego.ranking();
    }
}

