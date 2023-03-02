package civitas;


import civitas.Casilla;
import civitas.Jugador;
import civitas.TituloPropiedad;
import civitas.MazoSorpresas;
import civitas.Sorpresa;
import civitas.Tablero;
import civitas.EstadosJuego;
import civitas.GestorEstados;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Juan Francisco Soto Poza
 */
public class CivitasJuego {
    
    private int indiceJugadorActual;
    private EstadosJuego estado;
    private MazoSorpresas mazo;
    private Tablero tablero;
    private GestorEstados gestorEstados;
    private ArrayList<Jugador> jugadores;
    
    public CivitasJuego(ArrayList<String> nombres) {
        jugadores = new ArrayList<>();
        Jugador nombre;
        for (int i = 0; i<nombres.size();i++) {
            nombre = new Jugador(nombres.get(i));
            jugadores.add(i, nombre);
        }
        gestorEstados = new GestorEstados();
        estado = gestorEstados.estadoInicial();
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
        
        mazo = new MazoSorpresas();
        this.inicializarMazoSorpresas(tablero);
        this.inicializarTablero(mazo);
    }
    
    
    private void avanzaJugador() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorSalida(jugadores.get(indiceJugadorActual));
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        contabilizarPasosPorSalida(jugadores.get(indiceJugadorActual));
    }
    
    
    public boolean cancelarHipoteca(int ip) {
        Jugador jugadorActual = getJugadorActual();
        return jugadorActual.cancelarHipoteca(ip);
    }
    
    
    public boolean comprar() {
        boolean solucion = false;
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        CasillaCalle calle = (CasillaCalle) casilla;
        TituloPropiedad titulo = calle.getTituloPropiedad();
        if (titulo.tienePropietario()) {
            System.out.println("Error al comprar, esta casilla ya tiene propietario");
            solucion = false;
        }
        else {
            solucion = jugadorActual.comprar(titulo);
        }
        return solucion;
    }
    
    
    public boolean construirCasa(int ip) {
        Boolean solucion = jugadores.get(indiceJugadorActual).construirCasa(ip);
        return solucion;
    }   
    
    public boolean construirHotel(int ip) {
        Boolean solucion = jugadores.get(indiceJugadorActual).construirHotel(ip);
        return solucion;    
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual) {
        if (tablero.getPorSalida()>0) {
            jugadorActual.pasaPorSalida();
        }
    }
    
    public boolean finalDelJuego() {
        boolean finalJuego = false;
        for (int i = 0; i < jugadores.size() && finalJuego == false; i++) {
            if (jugadores.get(i).enBancarrota() == true) {
                finalJuego = true;
            }
        }
        return finalJuego;
    }
    
    public Casilla getCasillaActual() {
        int casillaActual = jugadores.get(indiceJugadorActual).getNumCasillaActual();
        return tablero.getCasilla(casillaActual);
    }
    
    public Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }
    
    public boolean hipotecar (int ip) {
        Boolean solucion = jugadores.get(indiceJugadorActual).hipotecar(ip);
        return solucion;
    }
    
    public String infoJugadorTexto() {
        return jugadores.get(indiceJugadorActual).toString();
    }
    
    private void inicializarMazoSorpresas(Tablero tablero) {
        mazo.alMazo(new SorpresaIrCarcel(tablero));
        mazo.alMazo(new SorpresaIrACasilla(tablero, 15, "Te toca ir a la casilla 15."));
        mazo.alMazo(new SorpresaSalirCarcel(mazo));
        mazo.alMazo(new SorpresaIrACasilla(tablero, 4, "Te toca ir a la casilla 4."));
        mazo.alMazo(new SorpresaPagarCobrar(250, "¡Felicidades! Te ha tocado la bonoloto, ganas 250 euros."));
        mazo.alMazo(new SorpresaPagarCobrar(-50, "Llega una carta de Hacienda, pierdes 50 euros."));
        mazo.alMazo(new SorpresaPorJugador(75, "Cada jugador tiene que darte 75 euros."));
        mazo.alMazo(new SorpresaPorJugador(-25, "Mala suerte, debes pagar 25 euros a cada jugador."));
        mazo.alMazo(new SorpresaPorCasaHotel(100, "Que suerte, ganas 100 euros por cada propiedad."));
        mazo.alMazo(new SorpresaPorCasaHotel(-50, "Oh vaya, debes pagar 50 euros por cada propiedad."));
        mazo.alMazo(new SorpresaEspeculador(200));
    }
    
    private void inicializarTablero(MazoSorpresas mazo) {
        tablero = new Tablero(1);
        // Casilla 0 es la salida
        // Hay un total de 12 casilla de tipo CALLE en el juego
        TituloPropiedad calle1 = new TituloPropiedad("Calle Gustavo", 70f, 1.1f, 200, 300, 200);
        TituloPropiedad calle2 = new TituloPropiedad("Calle Maikel", 150, 1.1f, 375, 500, 400);
        TituloPropiedad calle3 = new TituloPropiedad("Calle Rodolfo", 175, 1.1f, 650, 800, 700);
        TituloPropiedad calle4 = new TituloPropiedad("Calle Alfredo", 200, 1.1f, 700, 900, 800);
        TituloPropiedad calle5 = new TituloPropiedad("Calle Musolini", 220, 1.1f, 800, 950, 860);
        TituloPropiedad calle6 = new TituloPropiedad("Calle Damilleo", 270, 1.1f, 950, 1050, 1000);
        TituloPropiedad calle7 = new TituloPropiedad("Calle Johny", 300, 1.1f, 1100, 1400, 1150);
        TituloPropiedad calle8 = new TituloPropiedad("Calle Boris", 350, 1.1f, 1200, 1500, 1250);
        TituloPropiedad calle9 = new TituloPropiedad("Calle Sistema", 400, 1.1f, 1240, 1550, 1200);
        TituloPropiedad calle10 = new TituloPropiedad("Calle Fulanito", 410, 1.1f, 1300, 1600, 1250);
        TituloPropiedad calle11 = new TituloPropiedad("Calle Trabuco", 460, 1.1f, 1350, 1650, 1300);
        TituloPropiedad calle12 = new TituloPropiedad("Calle Ebibare", 500, 1.1f, 1400, 1700, 1350);
        
        System.out.println("Inicializando tablero: ");
        // Las añadimos al tablero
        tablero.añadeCasilla(new CasillaCalle(calle1));
        tablero.añadeCasilla(new CasillaCalle(calle2));
        tablero.añadeCasilla(new CasillaCalle(calle3));
        tablero.añadeCasilla(new CasillaCalle(calle4));
        tablero.añadeCasilla(new CasillaCalle(calle5));
        tablero.añadeCasilla(new CasillaCalle(calle6));
        tablero.añadeCasilla(new CasillaCalle(calle7));
        tablero.añadeCasilla(new CasillaCalle(calle8));
        tablero.añadeCasilla(new CasillaCalle(calle9));
        tablero.añadeCasilla(new CasillaCalle(calle10));
        tablero.añadeCasilla(new CasillaCalle(calle11));
        tablero.añadeCasilla(new CasillaCalle(calle12));
        
        // Añadimos 3 casilla de tipo sorpresa
        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa"));
        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa"));
        tablero.añadeCasilla(new CasillaSorpresa(mazo, "Sorpresa"));
        
        // Añadimos la casilla tipo impuesto
        tablero.añadeCasilla(new CasillaImpuesto(100, "Impuesto"));
        
        // Añadimos la casilla tipo juez
        tablero.añadeCasilla(new CasillaJuez(tablero.getCarcel(), "Juez"));
        
        // Añadimos la casilla tipo parking 
        tablero.añadeCasilla(new Casilla("Parking"));

    }
    
    private void pasarTurno() {
        indiceJugadorActual = (indiceJugadorActual+1) % jugadores.size();
    }
    
    public ArrayList<Jugador> ranking() {
        ArrayList<Jugador> rank = jugadores;
        Collections.sort(rank, Collections.reverseOrder());
        return rank;
    }
    
    public boolean salirCarcelPagando() {
        Jugador jugadorActual = getJugadorActual();
        return jugadorActual.salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando() {
        Jugador jugadorActual = getJugadorActual();
        return jugadorActual.salirCarcelTirando();
    }
    
    
    public OperacionesJuego siguientePaso() {
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
        if (operacion == OperacionesJuego.PASAR_TURNO) {
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        else if (operacion == OperacionesJuego.AVANZAR) {
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        return operacion;
    }
    
    
    public void siguientePasoCompletado(OperacionesJuego operacion) {
        gestorEstados.siguienteEstado(getJugadorActual(), estado, operacion);
    }
    
    public boolean vender(int ip) {
        Jugador jugadorActual = getJugadorActual();
        return jugadorActual.vender(ip);
    }
   
}
