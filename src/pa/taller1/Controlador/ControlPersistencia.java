package pa.taller1.Controlador;

import pa.taller1.Modelo.*;

import java.io.IOException;
import java.util.List;

/**
 * Controlador encargado de gestionar persistencia.
 */
public class ControlPersistencia {

    private CargadorEquiposProperties cargador = new CargadorEquiposProperties();
    private PersistenciaSerializacion serializacion = new PersistenciaSerializacion();
    private PersistenciaResultados persistenciaResultados = new PersistenciaResultados();

    public List<Equipo> cargarDesdeProperties(String ruta) throws IOException {
        return cargador.cargarEquipos(ruta);
    }

    public void serializarEquipos(List<Equipo> equipos) throws IOException {
        serializacion.guardarEquipos(equipos);
    }

    public List<Equipo> deserializarEquipos() throws IOException, ClassNotFoundException {
        return serializacion.cargarEquipos();
    }

    public void guardarResultado(int clave, Equipo equipo) throws IOException {
        persistenciaResultados.guardarResultado(clave, equipo);
    }

    public int contarVictorias(String nombreEquipo) throws IOException {
        return persistenciaResultados.contarVictorias(nombreEquipo);
    }

    public int obtenerSiguienteClave() throws IOException {
        try {
            return persistenciaResultados.obtenerUltimaClave() + 1;
        } catch (Exception e) {
            return 1;
        }
    }
}