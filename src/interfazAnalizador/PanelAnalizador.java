package interfazAnalizador;

import metodos.Metodos;
import interfazGrafica.Ventana;
import static interfazGrafica.Ventana.RUTA;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

//Panel crear
public class PanelAnalizador extends JDesktopPane implements
        ActionListener, MouseWheelListener, KeyListener {

    //Componentes del panel
    private JTextArea AreaAnalisis;
    private JScrollPane SCrollResultado, ScrollAnalisis;
    private JComboBox Analizador;
    private JButton BoAnalizar, BOBorrarAn;
    private JButton BoOptener, BoBorrarTa;
    private JButton BoRapido, BoPause, BotonDestruir;
    private JSlider Barra;

    //Tabla
    private JTable Tabla;
    private Recorrido Recorrido;
    private boolean Pausado;
    private int NoFila, AnalizadorEscogido;

    //Letra
    private final String TIPO_DE_LETRA = "Avenir Next LT Pro";
    private final int ESTILO_DE_LETRA = 0;
    private int TaLetra, TaLetraAux;
    private JLabel Titulo;

    //Imagenes
    private ImageIcon Pause, Play;

    //Tecla
    private final int CONTROL = KeyEvent.VK_CONTROL;
    private boolean PresControl = false;

    //Ventana
    private JFrame ventana;

    //Constructor
    public PanelAnalizador(Color color, JFrame ventana) throws IOException {

        //Ventana
        this.AnalizadorEscogido = 0;
        this.ventana = ventana;
        this.Pausado = false;
        NoFila = 0;

        //Panel
        this.setLayout(null);
        this.setBackground(color);

        //Creamos componentes
        crearComponentes(Ventana.ANCHO, Ventana.ALTO);
    }

    //Crear componentes
    private void crearComponentes(int AnchoMax, int AltoMax) throws IOException {

        //Componentes
        Font Fuente = new Font(TIPO_DE_LETRA, ESTILO_DE_LETRA, AltoMax / 20);
        Color coLetra = Color.BLACK;
        Color coAreas = Color.WHITE;
        Color coBotones = Ventana.color;

        // Tamaño minimo
        Dimension minimDimension = new Dimension(100, 19);

        //Posiciones
        int x = 5;
        int y = 5;

        //Titulo Entrada
        Titulo = Metodos.crearEtiqueta(
                "Entrada", AnchoMax / 4, AltoMax / 19, x, y, Fuente, coLetra);
        Titulo.setMinimumSize(minimDimension);
        this.add(Titulo);

        Fuente = new Font(TIPO_DE_LETRA, ESTILO_DE_LETRA, Fuente.getSize() / 2);

        //Escojer analizador
        Analizador = new JComboBox();
        Analizador.addItem("Analizador");
        Analizador.addItem("Analizador 1 (JFlex)");
        Analizador.addItem("Analizador 2 (JFlex)");
        Analizador.addItem("Analizador 3 (Cup)");
        Analizador.setBounds(Titulo.getWidth() - 100, y * 3, 170, 20);
        Analizador.setForeground(coLetra);
        Analizador.setBackground(coAreas);
        Analizador.setFont(Fuente);
        this.add(Analizador);

        //Evento
        Analizador.addItemListener((ItemEvent itemEvent) -> {
            String opc = (String) Analizador.getSelectedItem();

            switch (opc) {
                case "Analizador" ->
                    AnalizadorEscogido = 0;
                case "Analizador 1 (JFlex)" ->
                    AnalizadorEscogido = 1;
                case "Analizador 2 (JFlex)" ->
                    AnalizadorEscogido = 2;
                case "Analizador 3 (Cup)" ->
                    AnalizadorEscogido = 3;
                default ->
                    AnalizadorEscogido = 0;
            }
        });

        //Area de entrada
        y += Titulo.getHeight() + 5;
        AreaAnalisis = Metodos.crearArea(Fuente, coAreas, coLetra);
        AreaAnalisis.addMouseWheelListener(this);
        AreaAnalisis.addKeyListener(this);
        AreaAnalisis.setFocusable(true);
        ScrollAnalisis = Metodos.crearScroll(AreaAnalisis,
                AnchoMax / 3, (AltoMax / 2) + (AltoMax / 5), x, y);
        this.add(ScrollAnalisis);

        //Evento
        ActionListener evt = this;

        //Tamaño de botones
        int AnchoBoton1 = ScrollAnalisis.getWidth() / 2;
        int AltoBoton1 = ScrollAnalisis.getWidth() / 6;

        //Boton crear texto
        y += ScrollAnalisis.getHeight() + 5;
        String ruta = RUTA + "VerClase.png";
        Image img = Metodos.crearImagen(ruta, AltoBoton1 / 2, AltoBoton1 / 2);
        ImageIcon imgIcon = new ImageIcon(img);
        BoAnalizar = Metodos.crearBotonImg(imgIcon, AnchoBoton1, AltoBoton1, x, y, true, evt);
        BoAnalizar.setText("Analizar");
        BoAnalizar.setFont(Fuente);
        BoAnalizar.setBackground(coBotones);
        this.add(BoAnalizar);

        //Boton optener texto
        int x2 = x + BoAnalizar.getWidth() + 5;
        ruta = RUTA + "BucarArchivo.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        imgIcon = new ImageIcon(img);
        BoOptener = Metodos.crearBotonImg(imgIcon, AltoBoton1, AltoBoton1, x2, y, true, evt);
        BoOptener.setBackground(coBotones);
        this.add(BoOptener);

        //Boton borrar texto
        x2 += BoOptener.getWidth() + 5;
        ruta = RUTA + "EliminarArchivo.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        imgIcon = new ImageIcon(img);
        BOBorrarAn = Metodos.crearBotonImg(imgIcon, AltoBoton1, AltoBoton1, x2, y, true, evt);
        BOBorrarAn.setBackground(coBotones);
        this.add(BOBorrarAn);

        //Titulo
        y = 5; //Area de texto resultado
        int x3 = (ScrollAnalisis.getWidth() + 15);
        int w = (AnchoMax - x3) - y * 5;
        int h = (ScrollAnalisis.getY() + (ScrollAnalisis.getHeight()) - 5);

        //Tabla
        String[] titulos = {"Numero", "Cadena", "Resultado"};
        Tabla = Metodos.CrearTabla(0, 3, titulos, null);
        Tabla.setEnabled(false);
        Tabla.setBackground(coAreas);
        Tabla.setFont(Fuente);
        Tabla.setForeground(coLetra);
        Tabla.getTableHeader().setReorderingAllowed(false);
        SCrollResultado = Metodos.crearScrollTabla(Tabla, w, h, x3, y);
        SCrollResultado.setForeground(coLetra);
        this.add(SCrollResultado);

        //Titulos
        JTableHeader th;
        th = Tabla.getTableHeader();
        Font f = new Font(TIPO_DE_LETRA, ESTILO_DE_LETRA, AltoMax / 30);
        th.setBackground(new Color(255, 255, 210));
        th.setFont(f);

        //Tamaños
        y = (SCrollResultado.getY() + SCrollResultado.getHeight()) + 5;
        x3 = (SCrollResultado.getX() + SCrollResultado.getWidth()) - (AltoBoton1);

        //Boton Rapido
        ruta = RUTA + "Terminar.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        imgIcon = new ImageIcon(img);
        BotonDestruir = Metodos.crearBotonImg(imgIcon, AltoBoton1, AltoBoton1, x3, y, true, evt);
        BotonDestruir.setEnabled(false);
        BotonDestruir.setBackground(coBotones);
        this.add(BotonDestruir);

        x3 = BotonDestruir.getX() - AltoBoton1 - 10;

        //Boton borrar Area de clase
        ruta = RUTA + "BorrarTexto.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        imgIcon = new ImageIcon(img);
        BoBorrarTa = Metodos.crearBotonImg(imgIcon, AltoBoton1, AltoBoton1, x3, y, true, evt);
        BoBorrarTa.setBackground(coBotones);
        this.add(BoBorrarTa);

        x3 = BoBorrarTa.getX() - AltoBoton1 - 10;

        //Boton Rapido
        ruta = RUTA + "Rapido.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        imgIcon = new ImageIcon(img);
        BoRapido = Metodos.crearBotonImg(imgIcon, AltoBoton1, AltoBoton1, x3, y, true, evt);
        BoRapido.setEnabled(false);
        BoRapido.setBackground(coBotones);
        this.add(BoRapido);

        //Evento
        BoRapido.addMouseListener(new java.awt.event.MouseAdapter() {

            JFrame ven = ventana;

            @Override  //Mientras se da click
            public void mousePressed(MouseEvent me) {
                if (BoRapido.isEnabled()) {
                    Recorrido.setVelocidad(0, 100);
                }
            }

            @Override  //Al soltar
            public void mouseReleased(MouseEvent me) {
                if (BoRapido.isEnabled()) {
                    String st = Barra.getValue() + "00";
                    int n = Integer.parseInt(st);
                    Recorrido.setVelocidad(n, 0);
                    ven.repaint();
                }
            }

        });

        x3 = BoRapido.getX() - AltoBoton1 - 10;

        //Imagens
        ruta = RUTA + "Pause.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        Pause = new ImageIcon(img);
        ruta = RUTA + "Play.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        Play = new ImageIcon(img);

        //Boton Estado
        BoPause = Metodos.crearBotonImg(Pause, AltoBoton1, AltoBoton1, x3, y, true, evt);
        BoPause.setBackground(coBotones);
        BoPause.setEnabled(false);
        this.add(BoPause);

        //Barra de desplasamiento
        x3 = BoPause.getX() - AltoBoton1 * 6 - 10;
        int or = JSlider.HORIZONTAL;
        Barra = Metodos.crearSlider(or, x3, y, AltoBoton1 * 6, AltoBoton1, 1, 10, 1, 1);
        Barra.setBackground(coBotones);
        Barra.setEnabled(false);
        Barra.setFont(Fuente);
        Barra.setValue(5);
        Barra.setForeground(coLetra);
        this.add(Barra);

        //Evento de la bara
        Barra.addChangeListener((ChangeEvent event) -> {
            if (Barra.isEnabled()) {
                String st = Barra.getValue() + "00";
                int n = Integer.parseInt(st);
                Recorrido.setVelocidad(n, 0);
            }
        });

        //Tamaño de letras
        TaLetra = Fuente.getSize();
        TaLetraAux = TaLetra;

    }

    //Comprobar texto
    private void AnalizarTexto() throws FileNotFoundException, IOException {
        if (AreaAnalisis.getText() != null) {
            if (AreaAnalisis.getText().length() > 0) {

                //Creamos un recorrido
                Recorrido = new Recorrido(
                        Tabla,
                        AreaAnalisis.getText(),
                        SCrollResultado,
                        this,
                        AnalizadorEscogido);

                //Bloqueamos botones
                ActualizarBotones();

                //Iniciamos
                Recorrido.start();

            } else {
                JOptionPane.showMessageDialog(null, "Texto demaciado corto", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tienes nada escrito", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }

    //Actualizar botones
    public void ActualizarBotones() {
        //Bloqueamos botones
        if (Recorrido.isEstado()) {
            //Apagar
            BoAnalizar.setEnabled(false);
            BoBorrarTa.setEnabled(false);
            //Prender
            BoPause.setEnabled(true);
            BoRapido.setEnabled(true);
            BotonDestruir.setEnabled(true);
            Barra.setEnabled(true);
        } else {
            //Prender
            BoAnalizar.setEnabled(true);
            BoBorrarTa.setEnabled(true);
            //Apagar
            Pausado = false;
            BoPause.setEnabled(false);
            BoPause.setIcon(Pause);
            BoRapido.setEnabled(false);
            BotonDestruir.setEnabled(false);
            Barra.setEnabled(false);
        }
    }

    //Resetrar area
    private void resetearArea() {
        TaLetra = TaLetraAux;
        Font f = new Font(TIPO_DE_LETRA, ESTILO_DE_LETRA, TaLetra);

        AreaAnalisis.setFont(f);
        AreaAnalisis.setText(null);
        ventana.repaint();
    }

    //Resetear tabla
    private void resetearTabla() {

        //Eliminamos filas
        DefaultTableModel Modelo = (DefaultTableModel) this.Tabla.getModel();
        Modelo.setRowCount(0);

        //Redibujamos
        Tabla.repaint();
        ventana.repaint();
        NoFila = 0;
    }

    @Override//Eventos boton
    public void actionPerformed(ActionEvent e) {

        //Optener texto de un documento
        if (e.getSource() == BoOptener) {
            try {
                resetearArea();
                String t = Metodos.optenerTextoDeArchivo(null);
                AreaAnalisis.setText(t);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(PanelAnalizador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Analizar Texto
        if (e.getSource() == BoAnalizar) {
            try {
                AnalizarTexto();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PanelAnalizador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PanelAnalizador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Borrar area Entrada
        if (e.getSource() == BOBorrarAn) {
            if (AreaAnalisis.getText() != null) {
                resetearArea();
            }
        }

        //Borrar tabla
        if (e.getSource() == BoBorrarTa) {
            resetearTabla();
        }

        //Boton pausar
        if (e.getSource() == BoPause) {
            //Intercambiamos estado
            Pausado = !Pausado;

            //Imagen
            if (Pausado) {
                BoPause.setIcon(Play);
                BoRapido.setEnabled(false);
            } else {
                BoPause.setIcon(Pause);
                BoRapido.setEnabled(true);
            }

        }

        //Boton terminar
        if (e.getSource() == BotonDestruir) {
            //Imagen
            if (Recorrido.isEstado()) {
                Recorrido.destruir();
            }
        }

    }

    @Override //Evento de rueda Del raton
    public void mouseWheelMoved(MouseWheelEvent e) {

        // -1 es arriba y 1 es abajo
        int Orientacion = e.getWheelRotation();

        //Si tenemos precionada tecla de control
        if (PresControl) {

            //Mover rueda dentro del area de entrada
            if (e.getComponent() == AreaAnalisis) {
                TaLetra = Metodos.cambarTamañoDeLetra(AreaAnalisis,
                        Orientacion, TaLetra, TIPO_DE_LETRA, ESTILO_DE_LETRA);
            }

        } else /*Si NO tenemos precionada tecla de control*/ {

            int m = 40;

            //Moverse por el scroll de entrada
            if (e.getComponent() == AreaAnalisis) {

                int pos = ScrollAnalisis.getVerticalScrollBar().getValue();

                if (Orientacion == -1) {//Arriba
                    ScrollAnalisis.getVerticalScrollBar().setValue(pos - m);
                } else {//Abajo
                    ScrollAnalisis.getVerticalScrollBar().setValue(pos + m);
                }
            }

        }

    }

    @Override//Precionar un caracter
    public void keyTyped(KeyEvent e) {
    }

    @Override //Al presionar una tecla
    public void keyPressed(KeyEvent e) {
        //Si se preciona la tecla control
        if (e.getKeyCode() == CONTROL) {
            PresControl = true;
        }
    }

    @Override//Al soltar
    public void keyReleased(KeyEvent e) {
        //Si se preciona la tecla control
        if (e.getKeyCode() == CONTROL) {
            PresControl = false;
        }
    }

    //Get 
    public int getNoFila() {
        return NoFila;
    }

    public JSlider getBarra() {
        return Barra;
    }

    //Is
    public boolean isPausado() {
        return Pausado;
    }

    //Set
    public void setNoFila(int NoFila) {
        this.NoFila = NoFila;
    }

}
