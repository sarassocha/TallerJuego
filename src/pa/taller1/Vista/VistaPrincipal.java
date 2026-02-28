package pa.taller1.Vista;

import pa.taller1.Controlador.ControlVista;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vista principal del juego.
 * SOLO contiene lógica visual.
 * No conoce ninguna clase del modelo.
 */
public class VistaPrincipal extends JFrame {

    private ControlVista controlVista;

    private JPanel panelEquipos;
    private JLabel lblEquipoActual;
    private JLabel lblJugadorActual;
    private JLabel lblTiempo;
    private JLabel lblResultado;
    private JLabel lblIntentos;

    private JButton btnEmbocar;
    private JButton btnCargar;

    // Mapa visual usando solo Strings
    private Map<String, JPanel> mapaEquiposVisual;
    private Map<String, Map<String, JLabel>> mapaJugadoresVisual;

    public VistaPrincipal() {

        setTitle("Juego del Balero");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mapaEquiposVisual = new HashMap<>();
        mapaJugadoresVisual = new HashMap<>();

        setLayout(new BorderLayout());

        crearTop();
        crearCentro();
        crearDerecha();
        crearAbajo();
    }

    /**
     * Inyección del controlador de vista.
     */
    public void setControlVista(ControlVista controlVista) {
        this.controlVista = controlVista;
    }

    public void launchApp() {
        setVisible(true);
    }

    private void crearTop() {
        JPanel top = new JPanel();
        btnCargar = new JButton("Cargar Equipos");
        btnCargar.addActionListener(e -> controlVista.cargarArchivo());
        top.add(btnCargar);
        add(top, BorderLayout.NORTH);
    }

    private void crearCentro() {
        panelEquipos = new JPanel();
        panelEquipos.setLayout(new GridLayout(0, 3, 20, 20));
        add(panelEquipos, BorderLayout.CENTER);
    }

    private void crearDerecha() {

        JPanel derecha = new JPanel();
        derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));

        lblEquipoActual = new JLabel("Equipo Actual:");
        lblJugadorActual = new JLabel("Jugador Actual:");
        lblTiempo = new JLabel("Tiempo:");
        lblResultado = new JLabel("Resultado:");
        lblIntentos = new JLabel("Intentos:");

        derecha.add(lblEquipoActual);
        derecha.add(lblJugadorActual);
        derecha.add(lblTiempo);
        derecha.add(lblResultado);
        derecha.add(lblIntentos);

        add(derecha, BorderLayout.EAST);
    }

    private void crearAbajo() {
        JPanel abajo = new JPanel();
        btnEmbocar = new JButton("Embocar");
        btnEmbocar.setEnabled(false);
        btnEmbocar.addActionListener(e -> controlVista.intentarEmbocar());
        abajo.add(btnEmbocar);
        add(abajo, BorderLayout.SOUTH);
    }

    /**
     * Recibe los datos planos de los equipos y jugadores.
     */
    public void mostrarEquipos(Map<String, List<String>> equipos) {

        panelEquipos.removeAll();
        mapaEquiposVisual.clear();
        mapaJugadoresVisual.clear();

        for (String nombreEquipo : equipos.keySet()) {

            JPanel cajaEquipo = new JPanel();
            cajaEquipo.setLayout(new BoxLayout(cajaEquipo, BoxLayout.Y_AXIS));
            cajaEquipo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cajaEquipo.setOpaque(true);

            JLabel nombre = new JLabel(nombreEquipo);
            nombre.setFont(new Font("Arial", Font.BOLD, 14));
            cajaEquipo.add(nombre);

            Map<String, JLabel> jugadoresMapa = new HashMap<>();

            for (String jugador : equipos.get(nombreEquipo)) {

                JLabel lblJugador = new JLabel(jugador);
                lblJugador.setOpaque(true);
                lblJugador.setBackground(Color.LIGHT_GRAY);

                jugadoresMapa.put(jugador, lblJugador);
                cajaEquipo.add(lblJugador);
            }

            mapaJugadoresVisual.put(nombreEquipo, jugadoresMapa);
            mapaEquiposVisual.put(nombreEquipo, cajaEquipo);

            panelEquipos.add(cajaEquipo);
        }

        btnEmbocar.setEnabled(true);

        panelEquipos.revalidate();
        panelEquipos.repaint();
    }

    public void finalizarJuego() {
        btnEmbocar.setEnabled(false);
    }

    /**
     * Actualiza información del turno actual.
     */
    public void actualizarDatos(String nombreEquipo,
            String nombreJugador,
            int tiempo,
            int intentos) {

        lblEquipoActual.setText("Equipo: " + nombreEquipo);
        lblJugadorActual.setText("Jugador: " + nombreJugador);
        lblTiempo.setText("Tiempo restante: " + tiempo);
        lblIntentos.setText("Intentos: " + intentos);

        resaltarEquipoYJugador(nombreEquipo, nombreJugador);
    }

    private void resaltarEquipoYJugador(String equipoActual,
            String jugadorActual) {

        for (String equipo : mapaEquiposVisual.keySet()) {

            JPanel panelEquipo = mapaEquiposVisual.get(equipo);

            if (equipo.equals(equipoActual)) {
                panelEquipo.setBackground(new Color(220, 240, 255)); // azul claro
            } else {
                panelEquipo.setBackground(Color.WHITE);
            }

            Map<String, JLabel> jugadores
                    = mapaJugadoresVisual.get(equipo);

            for (String jugador : jugadores.keySet()) {

                JLabel lblJugador = jugadores.get(jugador);

                if (equipo.equals(equipoActual)
                        && jugador.equals(jugadorActual)) {

                    lblJugador.setBackground(new Color(100, 149, 237)); // azul fuerte
                    lblJugador.setForeground(Color.WHITE);
                    lblJugador.setFont(
                            new Font("Arial", Font.BOLD, 12));

                } else {

                    lblJugador.setBackground(Color.LIGHT_GRAY);
                    lblJugador.setForeground(Color.BLACK);
                    lblJugador.setFont(
                            new Font("Arial", Font.PLAIN, 12));
                }
            }
        }
    }

    public void mostrarResultado(String mensaje) {
        lblResultado.setText("Resultado: " + mensaje);
    }

    private void sombrearEquipos(String equipoActual) {

        for (Map.Entry<String, JPanel> entry : mapaEquiposVisual.entrySet()) {

            if (entry.getKey().equals(equipoActual)) {
                entry.getValue().setBackground(Color.WHITE);
            } else {
                entry.getValue().setBackground(Color.LIGHT_GRAY);
            }
        }
    }
}