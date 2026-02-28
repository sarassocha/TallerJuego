package pa.taller1.Controlador;

import pa.taller1.Modelo.Equipo;

/**
 * Controlador encargado de operaciones relacionadas con equipos.
 */
public class ControlEquipo {

    public int obtenerPuntaje(Equipo equipo) {
        return equipo.getPuntajeTotal();
    }
}