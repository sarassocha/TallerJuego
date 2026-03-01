package pa.taller1.Controlador;

import pa.taller1.Modelo.*;

import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Controlador del juego.
 * Contiene TODA la lógica del flujo del juego.
 */
public class ControlJuego {

    private Juego juego;
    private Random random;

    private final int TIEMPO_POR_LANZAMIENTO = 3;

    public ControlJuego() {
        random = new Random();
    }

    /**
     * Inicializa el juego con tiempo definido por el usuario.
     */
    public void inicializarJuego(List<Equipo> equipos, int tiempoEquipo) {
        this.juego = new Juego(equipos, tiempoEquipo);
    }

    /**
     * Ejecuta un intento y descuenta 3 segundos.
     */
    public String ejecutarIntento() {

        if (juego.isJuegoTerminado()) {
            return "";
        }

        Jugador jugador = juego.getJugadorActual();

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
     * Descuenta tiempo por lanzamiento y cambia turno si es necesario.
     */
    private void descontarTiempo() {
        int tiempoRestante = juego.getTiempoRestanteJugador() - TIEMPO_POR_LANZAMIENTO;
        juego.setTiempoRestanteJugador(tiempoRestante);

        if (tiempoRestante <= 0) {
            cambiarTurno();
        }
    }

    /**
     * Cambia al siguiente jugador o equipo.
     */
    private void cambiarTurno() {
        int indiceJugador = juego.getIndiceJugadorActual() + 1;

        if (indiceJugador >= 3) {
            indiceJugador = 0;
            int indiceEquipo = juego.getIndiceEquipoActual() + 1;
            juego.setIndiceEquipoActual(indiceEquipo);

            if (indiceEquipo >= juego.getEquipos().size()) {
                juego.setJuegoTerminado(true);
                return;
            }
        }

        juego.setIndiceJugadorActual(indiceJugador);
        juego.setTiempoRestanteJugador(juego.getTiempoPorJugador());
    }

    /**
     * Ahora usa la variable booleana del juego.
     */
    public boolean isJuegoTerminado() {
        return juego.isJuegoTerminado();
    }

    /**
     * Obtiene el estado actual del juego en un único objeto.
     * Encapsula toda la información que la Vista necesita.
     *
     * @return EstadoJuego con información actualizada
     */
    public EstadoJuego obtenerEstadoActual() {
        return juego.obtenerEstado();
    }

    /**
     * Determina ganador por puntaje y desempata por intentos exitosos.
     */
    public Equipo obtenerGanador() {

        Equipo ganador = juego.getEquipos().get(0);

        for (Equipo e : juego.getEquipos()) {

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

        for (Equipo equipo : juego.getEquipos()) {

            List<String> jugadores = new ArrayList<>();

            for (Jugador jugador : equipo.getJugadores()) {
                jugadores.add(jugador.getNombre());
            }

            datos.put(equipo.getNombreEquipo(), jugadores);
        }

        return datos;
    }
}
