package pa.taller1.Controlador;

import pa.taller1.Vista.VistaPrincipal;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Intermediario entre Vista y ControlPrincipal.
 * La Vista SOLO conoce esta clase.
 */
public class ControlVista {

    private VistaPrincipal vista;
    private ControlPrincipal controlPrincipal;

    public ControlVista(ControlPrincipal controlPrincipal) {

        this.controlPrincipal = controlPrincipal;
        this.vista = new VistaPrincipal();

        vista.setControlVista(this);
    }

    public void iniciarVista() {
        vista.launchApp();
    }

    /**
     * Carga archivo properties usando JFileChooser.
     * Solicita tiempo por equipo antes de iniciar.
     */
    public void cargarArchivo() {

        JFileChooser fileChooser = new JFileChooser();
        int opcion = fileChooser.showOpenDialog(null);

        if (opcion == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();

            int tiempoEquipo = solicitarTiempoPorEquipo();

            controlPrincipal.cargarEquiposDesdeProperties(
                    file.getAbsolutePath(),
                    tiempoEquipo
            );

            Map<String, List<String>> datos =
                    controlPrincipal.obtenerDatosEquipos();

            vista.mostrarEquipos(datos);

            actualizarVista();
        }
    }

    /**
     * Solicita y valida el tiempo total por equipo.
     * - Debe ser número entero
     * - Mayor que 0
     * - Mínimo 9 segundos (3 jugadores × 3 segundos)
     */
    private int solicitarTiempoPorEquipo() {

        while (true) {

            String input = JOptionPane.showInputDialog(
                    null,
                    "Ingrese el tiempo total por equipo (mínimo 9 segundos).\n" +
                    "Cada lanzamiento tarda aproximadamente 3 segundos.",
                    "Tiempo por equipo",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Debe ingresar un tiempo válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }

            try {
                int tiempo = Integer.parseInt(input);

                if (tiempo <= 0) {
                    JOptionPane.showMessageDialog(
                            null,
                            "El tiempo debe ser mayor que 0.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    continue;
                }

                if (tiempo < 9) {
                    JOptionPane.showMessageDialog(
                            null,
                            "El tiempo mínimo permitido es 9 segundos.\n" +
                            "3 jugadores × 3 segundos por intento.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    continue;
                }

                return tiempo;

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Debe ingresar un número entero válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Ejecuta intento desde botón Embocar.
     */
    public void intentarEmbocar() {

        if (controlPrincipal.isJuegoTerminado()) {
            return;
        }

        String resultado = controlPrincipal.ejecutarIntento();
        vista.mostrarResultado(resultado);

        if (controlPrincipal.isJuegoTerminado()) {
            vista.finalizarJuego();
            controlPrincipal.finalizarJuego();
            return; 
        }

        actualizarVista();
    }

    /**
     * Muestra mensaje final con ganador y número de victorias históricas.
     */
    public void mostrarResultadoFinal(pa.taller1.Modelo.Equipo ganador,
                                      int victorias) {

        JOptionPane.showMessageDialog(null,
                "Equipo ganador: " + ganador.getNombreEquipo()
                + "\nPuntaje: " + ganador.getPuntajeTotal()
                + "\nHa ganado " + victorias + " veces en total.");

        System.exit(0);
    }

    /**
     * Actualiza información visible en pantalla.
     */
    private void actualizarVista() {

        vista.actualizarDatos(
                controlPrincipal.getNombreEquipoActual(),
                controlPrincipal.getNombreJugadorActual(),
                controlPrincipal.getTiempoRestanteJugador(),
                controlPrincipal.getIntentosActuales()
        );
    }
}