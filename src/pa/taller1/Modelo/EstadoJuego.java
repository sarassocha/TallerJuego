package pa.taller1.Modelo;

/**
 * Objeto de transferencia de datos con el estado actual del juego.
 * Encapsula toda la información que la Vista necesita en un único objeto.
 */
public class EstadoJuego {

    private String nombreEquipo;
    private String nombreJugador;
    private int tiempoRestante;
    private int intentos;
    private boolean juegoTerminado;

    /**
     * Constructor de EstadoJuego
     *
     * @param nombreEquipo Nombre del equipo actual
     * @param nombreJugador Nombre del jugador actual
     * @param tiempoRestante Tiempo restante del jugador
     * @param intentos Intentos del jugador actual
     * @param juegoTerminado Si el juego ha terminado
     */
    public EstadoJuego(String nombreEquipo, String nombreJugador, 
                       int tiempoRestante, int intentos, boolean juegoTerminado) {
        this.nombreEquipo = nombreEquipo;
        this.nombreJugador = nombreJugador;
        this.tiempoRestante = tiempoRestante;
        this.intentos = intentos;
        this.juegoTerminado = juegoTerminado;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public int getIntentos() {
        return intentos;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}
