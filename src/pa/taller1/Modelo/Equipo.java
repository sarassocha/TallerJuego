package pa.taller1.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un equipo participante en la competencia.
 */
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreProyecto;
    private String nombreEquipo;
    private List<Jugador> jugadores;

    public Equipo(String nombreProyecto, String nombreEquipo) {
        if (nombreProyecto == null || nombreProyecto.isEmpty()) {
            throw new IllegalArgumentException("Tiene que haber un nombre en el proyecto");
        }
        if (nombreEquipo == null || nombreEquipo.isEmpty()) {
            throw new IllegalArgumentException("El equipo tiene que tener un nombre");
        }
        this.nombreProyecto = nombreProyecto;
        this.nombreEquipo = nombreEquipo;
        this.jugadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("Tienen que haber jugadores");
        }
        jugadores.add(jugador);
    }

    public int getPuntajeTotal() {
        return jugadores.stream().mapToInt(Jugador::getPuntaje).sum();
    }

    public int getIntentosTotales() {
        return jugadores.stream().mapToInt(Jugador::getIntentos).sum();
    }

    public int getIntentosExitososTotales() {
        return jugadores.stream().mapToInt(Jugador::getIntentosExitosos).sum();
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }
}