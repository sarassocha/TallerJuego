package pa.taller1.Modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Clase encargada de cargar equipos desde un archivo .properties
 */
public class CargadorEquiposProperties {

    public List<Equipo> cargarEquipos(String ruta) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(ruta));

        int cantidadEquipos = Integer.parseInt(props.getProperty("equipos"));
        List<Equipo> equipos = new ArrayList<>();

        for (int i = 1; i <= cantidadEquipos; i++) {

            String nombreProyecto = props.getProperty("equipo" + i + ".nombreProyecto");
            String nombreEquipo = props.getProperty("equipo" + i + ".nombreEquipo");

            Equipo equipo = new Equipo(nombreProyecto, nombreEquipo);

            // Cargar dinámicamente todos los jugadores del equipo
            int j = 1;
            while (props.getProperty("equipo" + i + ".jugador" + j + ".codigo") != null) {
                String codigo = props.getProperty("equipo" + i + ".jugador" + j + ".codigo");
                String nombre = props.getProperty("equipo" + i + ".jugador" + j + ".nombre");

                equipo.agregarJugador(new Jugador(codigo, nombre));
                j++;
            }

            equipos.add(equipo);
        }

        return equipos;
    }
}