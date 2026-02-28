package pa.taller1.Controlador;

import pa.taller1.Modelo.*;

import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Motor real del juego.
 * Contiene TODA la lógica del flujo.
 */
public class ControlJuego {

    private List<Equipo> equipos;

    private int indiceEquipoActual;
    private int indiceJugadorActual;

    private int tiempoPorEquipo;
    private int tiempoPorJugador;
    private int tiempoRestanteJugador;

    private boolean juegoTerminado;

    private final int TIEMPO_POR_LANZAMIENTO = 3;

    private Random random;

    public ControlJuego() {
        random = new Random();
    }

    /**
     * Inicializa el juego con tiempo definido por el usuario.
     */
    public void inicializarJuego(List<Equipo> equipos, int tiempoEquipo) {

        this.equipos = equipos;

        this.tiempoPorEquipo = tiempoEquipo;
        this.tiempoPorJugador = tiempoEquipo / 3;
        this.tiempoRestanteJugador = tiempoPorJugador;

        this.indiceEquipoActual = 0;
        this.indiceJugadorActual = 0;

        this.juegoTerminado = false;
    }

    /**
     * Ejecuta un intento y descuenta 3 segundos.
     */
    public String ejecutarIntento() {

        if (juegoTerminado) {
            return "";
        }

        Jugador jugador = getJugadorActual();

        boolean exito = random.nextBoolean();
        TipoEmbocada tipo;

        if (!exito) {
            tipo = TipoEmbocada.FALLA;
        } else {
            TipoEmbocada[] valores = TipoEmbocada.values();
            int indice = random.nextInt(valores.length - 1);
            tipo = valores[indice];
        }

        jugador.registrarIntento(tipo);

        descontarTiempo();

        if (tipo == TipoEmbocada.FALLA) {
            return "FALLA (-3 seg)";
        } else {
            return tipo.getNombre() + " (+" + tipo.getPuntaje() + " pts, -3 seg)";
        }
    }

    /**
     * Descuenta tiempo por lanzamiento.
     */
    private void descontarTiempo() {

        tiempoRestanteJugador -= TIEMPO_POR_LANZAMIENTO;

        if (tiempoRestanteJugador <= 0) {
            cambiarTurno();
        }
    }

    /**
     * Cambia jugador o equipo según corresponda.
     */
    private void cambiarTurno() {

        indiceJugadorActual++;

        if (indiceJugadorActual >= 3) {

            indiceJugadorActual = 0;
            indiceEquipoActual++;

            if (indiceEquipoActual >= equipos.size()) {
                juegoTerminado = true;
                return;
            }
        }

        tiempoRestanteJugador = tiempoPorJugador;
    }

    /**
     * Ahora usa SOLO la variable booleana.
     */
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    private Equipo getEquipoActual() {
        return equipos.get(indiceEquipoActual);
    }

    private Jugador getJugadorActual() {
        return getEquipoActual().getJugadores().get(indiceJugadorActual);
    }

    public String getNombreEquipoActual() {
        if (juegoTerminado) return "";
        return getEquipoActual().getNombreEquipo();
    }

    public String getNombreJugadorActual() {
        if (juegoTerminado) return "";
        return getJugadorActual().getNombre();
    }

    public int getTiempoRestanteJugador() {
        return tiempoRestanteJugador;
    }

    public int getIntentosActuales() {
        if (juegoTerminado) return 0;
        return getJugadorActual().getIntentos();
    }

    /**
     * Determina ganador por puntaje y desempata por intentos exitosos.
     */
    public Equipo obtenerGanador() {

        Equipo ganador = equipos.get(0);

        for (Equipo e : equipos) {

            if (e.getPuntajeTotal() > ganador.getPuntajeTotal()) {
                ganador = e;
            } else if (e.getPuntajeTotal() == ganador.getPuntajeTotal()) {
                if (e.getIntentosExitososTotales() >
                        ganador.getIntentosExitososTotales()) {
                    ganador = e;
                }
            }
        }

        return ganador;
    }

    /**
     * Devuelve estructura simple para la vista.
     */
    public Map<String, List<String>> obtenerDatosSimples() {

        Map<String, List<String>> datos = new HashMap<>();

        for (Equipo equipo : equipos) {

            List<String> jugadores = new ArrayList<>();

            for (Jugador jugador : equipo.getJugadores()) {
                jugadores.add(jugador.getNombre());
            }

            datos.put(equipo.getNombreEquipo(), jugadores);
        }

        return datos;
    }
}