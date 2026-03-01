package pa.taller1.Modelo;

import java.util.List;

/**
 * Modelo del juego.
 * Contiene SOLO datos del juego, sin lógica de negocio.
 * La lógica está en ControlJuego.
 */
public class Juego {

    private List<Equipo> equipos;

    private int indiceEquipoActual;
    private int indiceJugadorActual;

    private int tiempoPorEquipo;
    private int tiempoPorJugador;
    private int tiempoRestanteJugador;

    private boolean juegoTerminado;

    /**
     * Constructor del juego
     *
     * @param equipos Lista de equipos
     * @param tiempoEquipo Tiempo total por equipo en segundos
     */
    public Juego(List<Equipo> equipos, int tiempoEquipo) {
        this.equipos = equipos;
        this.tiempoPorEquipo = tiempoEquipo;
        this.tiempoPorJugador = tiempoEquipo / 3;
        this.tiempoRestanteJugador = tiempoPorJugador;

        this.indiceEquipoActual = 0;
        this.indiceJugadorActual = 0;
        this.juegoTerminado = false;
    }

    // Getters
    
    /**
     * Obtiene la lista de equipos
     *
     * @return Lista de equipos del juego
     */
    public List<Equipo> getEquipos() {
        return equipos;
    }

    /**
     * Obtiene el índice del equipo actual
     *
     * @return Índice del equipo actual
     */
    public int getIndiceEquipoActual() {
        return indiceEquipoActual;
    }

    /**
     * Obtiene el índice del jugador actual
     *
     * @return Índice del jugador actual
     */
    public int getIndiceJugadorActual() {
        return indiceJugadorActual;
    }

    /**
     * Obtiene el tiempo restante del jugador actual
     *
     * @return Tiempo restante en segundos
     */
    public int getTiempoRestanteJugador() {
        return tiempoRestanteJugador;
    }

    /**
     * Obtiene el tiempo por jugador
     *
     * @return Tiempo asignado por jugador
     */
    public int getTiempoPorJugador() {
        return tiempoPorJugador;
    }

    /**
     * Verifica si el juego ha terminado
     *
     * @return true si el juego terminó, false en caso contrario
     */
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    /**
     * Obtiene el equipo actual
     *
     * @return Equipo actual
     */
    public Equipo getEquipoActual() {
        return equipos.get(indiceEquipoActual);
    }

    /**
     * Obtiene el jugador actual
     *
     * @return Jugador actual
     */
    public Jugador getJugadorActual() {
        return getEquipoActual().getJugadores().get(indiceJugadorActual);
    }

    // Setters para que ControlJuego maneje la lógica
    
    /**
     * Establece el índice del jugador actual
     *
     * @param indice Nuevo índice de jugador
     */
    public void setIndiceJugadorActual(int indice) {
        this.indiceJugadorActual = indice;
    }

    /**
     * Establece el índice del equipo actual
     *
     * @param indice Nuevo índice de equipo
     */
    public void setIndiceEquipoActual(int indice) {
        this.indiceEquipoActual = indice;
    }

    /**
     * Establece el tiempo restante del jugador
     *
     * @param tiempo Nuevo valor de tiempo restante
     */
    public void setTiempoRestanteJugador(int tiempo) {
        this.tiempoRestanteJugador = tiempo;
    }

    /**
     * Establece el estado de finalización del juego
     *
     * @param terminado true si el juego terminó
     */
    public void setJuegoTerminado(boolean terminado) {
        this.juegoTerminado = terminado;
    }

    /**
     * Obtiene el estado actual del juego
     *
     * @return EstadoJuego con la información actual del juego
     */
    public EstadoJuego obtenerEstado() {
        String nombreEquipo = juegoTerminado ? "" : getEquipoActual().getNombreEquipo();
        String nombreJugador = juegoTerminado ? "" : getJugadorActual().getNombre();
        int intentos = juegoTerminado ? 0 : getJugadorActual().getIntentos();

        return new EstadoJuego(nombreEquipo, nombreJugador, 
                               tiempoRestanteJugador, intentos, juegoTerminado);
    }
}

