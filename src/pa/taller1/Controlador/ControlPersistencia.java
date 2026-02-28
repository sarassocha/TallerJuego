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
        java.util.Properties props = cargador.leerPropiedades(ruta);

        // cantidad de equipos
        String equiposStr = props.getProperty("equipos");
        if (equiposStr == null || equiposStr.isEmpty()) {
            throw new IOException("Propiedad 'equipos' no encontrada.");
        }

        int cantidadEquipos;
        try {
            cantidadEquipos = Integer.parseInt(equiposStr);
        } catch (NumberFormatException e) {
            throw new IOException("Formato inválido para número de equipos.", e);
        }
        if (cantidadEquipos <= 0) {
            throw new IOException("La cantidad de equipos debe ser mayor a cero.");
        }

        java.util.List<Equipo> lista = new java.util.ArrayList<>();

        for (int i = 1; i <= cantidadEquipos; i++) {
            String nombreProyecto = props.getProperty("equipo" + i + ".nombreProyecto");
            String nombreEquipo = props.getProperty("equipo" + i + ".nombreEquipo");

            // los constructores de Equipo validan datos
            Equipo equipo = new Equipo(nombreProyecto, nombreEquipo);

            int j = 1;
            while (true) {
                String codigo = props.getProperty("equipo" + i + ".jugador" + j + ".codigo");
                if (codigo == null) break;
                String nombre = props.getProperty("equipo" + i + ".jugador" + j + ".nombre");
                Jugador jugador = new Jugador(codigo, nombre); // valida internamente
                equipo.agregarJugador(jugador);
                j++;
            }

            if (equipo.getJugadores().isEmpty()) {
                throw new IOException("Equipo " + i + " no tiene jugadores definidos.");
            }

            lista.add(equipo);
        }

        return lista;
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