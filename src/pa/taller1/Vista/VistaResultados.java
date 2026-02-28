package pa.taller1.Vista;

import pa.taller1.Modelo.Equipo;

import javax.swing.*;
import java.awt.*;

/**
 * Vista encargada de mostrar los resultados finales.
 */
public class VistaResultados extends JFrame {

    public void mostrarGanador(Equipo ganador, int victorias) {

        setTitle("Resultados Finales");
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblGanador = new JLabel("Equipo Ganador: " + ganador.getNombreEquipo());
        JLabel lblPuntaje = new JLabel("Puntaje: " + ganador.getPuntajeTotal());
        JLabel lblVictorias = new JLabel("Ha ganado " + victorias + " veces.");

        lblGanador.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblVictorias.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblGanador);
        panel.add(lblPuntaje);
        panel.add(lblVictorias);

        add(panel);
        setVisible(true);
    }
}