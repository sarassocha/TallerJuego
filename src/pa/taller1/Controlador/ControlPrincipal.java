package pa.taller1.Controlador;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import pa.taller1.Modelo.Equipo;

/**
 * Orquestador principal.
 * NO conoce la Vista directamente.
 * Solo conoce otros controladores.
 */
public class ControlPrincipal {

    private ControlVista controlVista;
    private ControlJuego controlJuego;
    private ControlPersistencia controlPersistencia;

    public ControlPrincipal() {

        controlJuego = new ControlJuego();
        controlPersistencia = new ControlPersistencia();

        controlVista = new ControlVista(this);
    }

    public void iniciarAplicacion() {
        controlVista.iniciarVista();
    }

    /**
     * Valida y carga equipos desde archivo properties.
     * Lanza excepción si hay error, sin inicializar juego.
     */
    public List<Equipo> validarYCargarEquipos(String ruta) throws IOException {
        return controlPersistencia.cargarDesdeProperties(ruta);
    }

    /**
     * Inicializa el juego con los equipos y tiempo.
     */
    public void inicializarJuegoConEquipos(List<Equipo> equipos, int tiempoEquipo) {
        controlJuego.inicializarJuego(equipos, tiempoEquipo);
    }

    public Map<String, List<String>> obtenerDatosEquipos() {
        return controlJuego.obtenerDatosSimples();
    }

    public String ejecutarIntento() {
        return controlJuego.ejecutarIntento();
    }

    public boolean isJuegoTerminado() {
        return controlJuego.isJuegoTerminado();
    }

    public String getNombreEquipoActual() {
        return controlJuego.getNombreEquipoActual();
    }

    public String getNombreJugadorActual() {
        return controlJuego.getNombreJugadorActual();
    }

    public int getTiempoRestanteJugador() {
        return controlJuego.getTiempoRestanteJugador();
    }

    public int getIntentosActuales() {
        return controlJuego.getIntentosActuales();
    }

    /**
     * Determina ganador y lo guarda en RandomAccessFile.
     */
    public void finalizarJuego() {

        try {
            Equipo ganador = controlJuego.obtenerGanador();

            int clave = controlPersistencia.obtenerSiguienteClave();

            controlPersistencia.guardarResultado(clave, ganador);

            int victorias
                    = controlPersistencia.contarVictorias(
                            ganador.getNombreEquipo());

            controlVista.mostrarResultadoFinal(ganador, victorias);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Equipo obtenerGanador() {
        return controlJuego.obtenerGanador();
    }
}