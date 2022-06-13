package interfazGrafica;

import metodos.Metodos;
import interfazAnalizador.PanelAnalizador;
import interfazCrear.PanelCrear;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

//Ventana
public class Ventana extends JFrame implements Runnable {

    //Variables
    public static int ANCHO, ALTO;
    private JLabel hora;
    private DateTimeFormatter Hora;

    public static Color color = new Color(204, 255, 255);
    public static final String RUTA = "src/imagenes/";

    //Constructor
    public Ventana() throws IOException {

        //Obtener tamaño de la pantalla
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();

        //Tamaño de ventna
        ANCHO = d.width - 150;
        ALTO = d.height - 100;

        //Tamaño maximo
        Dimension tm = new Dimension(ANCHO, ALTO);
        this.setSize(tm);

        //Ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Analizador lexico");
        this.setLocationRelativeTo(null);

        //Icono
        String Ruta = RUTA + "Icono.png";
        Image img = Metodos.crearImagen(Ruta, 30, 30);
        this.setIconImage(img);

        //Componentes
        crearComponentes();

    }

    //Componetes
    private void crearComponentes() throws IOException {
        //Panel principal
        Font Fuente = new Font("Avenir Next LT Pro", 0, 20);
        JTabbedPane PanelPrincipal = Metodos.crearJTaPanel(ANCHO, ALTO, 0, 0, color, Color.BLACK, Fuente);
        this.add(PanelPrincipal);

        //Panel de Crear
        String Ruta = RUTA + "Crear.png";
        Image img = Metodos.crearImagen(Ruta, 30, 30);
        ImageIcon imgA = new ImageIcon(img);
        PanelCrear JTpanelCrear = new PanelCrear(color, this);
        PanelPrincipal.addTab("Crear", imgA, JTpanelCrear);

        //Panel analizar
        Ruta = RUTA + "Analizar.png";
        img = Metodos.crearImagen(Ruta, 30, 30);
        imgA = new ImageIcon(img);
        JDesktopPane JTpanelAnalizador = new PanelAnalizador(color, this);
        PanelPrincipal.addTab("Analizar", imgA, JTpanelAnalizador);

        //Hora
        Hora = DateTimeFormatter.ofPattern("hh:mm:ss a");
        hora = new JLabel(Hora.format(LocalDateTime.now()));
        hora.setSize(130, 27);
        hora.setLocation(ANCHO - hora.getWidth() - 15, 5);
        hora.setForeground(Color.BLACK);
        hora.setFont(Fuente);
        this.add(hora);

        //Fecha
        JLabel fecha = new JLabel(optenerFecha());
        fecha.setSize(180, 27);
        fecha.setLocation(hora.getX() - fecha.getWidth(), 5);
        fecha.setForeground(Color.BLACK);
        fecha.setFont(Fuente);
        this.add(fecha);
    }

    //Fecha
    public String optenerFecha() {

        //OBTENER FECHA
        DateTimeFormatter Fecha = DateTimeFormatter.ofPattern("yyyy");
        String año = Fecha.format(LocalDateTime.now());
        Fecha = DateTimeFormatter.ofPattern("MM");
        String mes = Fecha.format(LocalDateTime.now());
        Fecha = DateTimeFormatter.ofPattern("dd");
        String dia = Fecha.format(LocalDateTime.now());

        return  dia + " de " + mes + " del " + año;
    }

    //Actualizar fecha
    private void actualizarHora() {
        try {
            hora.setText(Hora.format(LocalDateTime.now()));
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error inesperado -->" + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override//Relog
    public void run() {
        while (true) {
            actualizarHora();
        }
    }

}
