package interfazCrear;

import metodos.Metodos;
import interfazGrafica.Ventana;
import static interfazGrafica.Ventana.RUTA;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jflex.exceptions.SilentExit;

//Panel crear
public class PanelCrear extends JDesktopPane implements ActionListener, MouseWheelListener, KeyListener {

    //Componentes del panel
    private JTextArea AreaClases, AreaEntrada;
    private JScrollPane ScrollClase, ScrollEntrada;
    private JButton BotonCrearArchivo, BotonCrearArchivoJava;
    private JButton BotonElegirDocumento, BotonOptenerTexto;
    private JButton BotonBorrarTextoEntrada, BotonBorrarAreaClases;
    private JButton BotonVerClaseJava;
    private JLabel EtiquetaNombreDeArchivo;

    //Componentes del archivo
    private String Ruta;
    private File Archivo;

    //Letra
    private final String TIPO_DE_LETRA = "Avenir Next LT Pro";
    private final int ESTILO_DE_LETRA = 0;
    private int TaLetraEntrada, TaLetraClase;
    private int TaLetraAux;
    private JLabel Titulo;

    //Boton control
    private boolean PresControl = false;

    //Tecla
    private final int CONTROL = KeyEvent.VK_CONTROL;

    //Ventana
    private JFrame ventana;

    //Constructor
    public PanelCrear(Color color, JFrame ventana) throws IOException {

        //Ventana
        this.ventana = ventana;

        //Panel
        this.setLayout(null);
        this.setBackground(color);

        //Creamos componentes
        crearComponentes(Ventana.ANCHO, Ventana.ALTO);
    }

    //Crear componentes
    private void crearComponentes(int AnchoMax, int AltoMax) throws IOException {

        //Componentes
        Font Fuente = new Font(TIPO_DE_LETRA, ESTILO_DE_LETRA, AltoMax / 15);
        Color coLetra = Color.BLACK;
        Color coAreas = Color.WHITE;
        Color coBotones = Ventana.color;

        // Tamaño minimo
        Dimension minimDimension = new Dimension(100, 20);

        //Posiciones
        int x = 5;
        int y = 5;

        //Titulo
        Titulo = Metodos.crearEtiqueta(
                "Crear clase", AnchoMax / 4, AltoMax / 15, x, y, Fuente, coLetra);
        Titulo.setMinimumSize(minimDimension);
        this.add(Titulo);

        //Area de entrada
        y += Titulo.getHeight() + 5;
        Fuente = new Font(TIPO_DE_LETRA, ESTILO_DE_LETRA, Fuente.getSize() / 3);
        AreaEntrada = Metodos.crearArea(Fuente, coAreas, coLetra);
        AreaEntrada.addMouseWheelListener(this);
        AreaEntrada.addKeyListener(this);
        AreaEntrada.setFocusable(true);
        ScrollEntrada = Metodos.crearScroll(AreaEntrada,
                AnchoMax / 3, (AltoMax / 2) + (AltoMax / 10), x, y);
        this.add(ScrollEntrada);

        //Evento
        ActionListener evt = this;

        //Tamaño de botones
        int AnchoBoton1 = ScrollEntrada.getWidth() / 2;
        int AltoBoton1 = ScrollEntrada.getWidth() / 7;

        //Boton crear texto
        y += ScrollEntrada.getHeight() + 5;
        String ruta = RUTA + "OptenerTexto.png";
        Image img = Metodos.crearImagen(ruta, AltoBoton1 / 2, AltoBoton1 / 2);
        ImageIcon imgIcon = new ImageIcon(img);
        BotonCrearArchivo = Metodos.crearBotonImg(imgIcon, AnchoBoton1, AltoBoton1, x, y, true, evt);
        BotonCrearArchivo.setText("Guardar Texto");
        BotonCrearArchivo.setFont(Fuente);
        BotonCrearArchivo.setBackground(coBotones);
        this.add(BotonCrearArchivo);

        //Boton optener texto
        int x2 = x + BotonCrearArchivo.getWidth() + 5;
        ruta = RUTA + "CrearTexto.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        imgIcon = new ImageIcon(img);
        BotonOptenerTexto = Metodos.crearBotonImg(imgIcon, AltoBoton1, AltoBoton1, x2, y, true, evt);
        BotonOptenerTexto.setBackground(coBotones);
        this.add(BotonOptenerTexto);

        //Boton borrar texto
        x2 += BotonOptenerTexto.getWidth() + 5;
        ruta = RUTA + "EliminarArchivo.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        imgIcon = new ImageIcon(img);
        BotonBorrarTextoEntrada = Metodos.crearBotonImg(imgIcon, AltoBoton1, AltoBoton1, x2, y, true, evt);
        BotonBorrarTextoEntrada.setBackground(coBotones);
        this.add(BotonBorrarTextoEntrada);

        //Boton crear Documento
        y += BotonCrearArchivo.getHeight() + 5;
        ruta = RUTA + "CrearArchivo.png";
        img = Metodos.crearImagen(ruta, AltoBoton1 / 2, AltoBoton1 / 2);
        imgIcon = new ImageIcon(img);
        BotonCrearArchivoJava = Metodos.crearBotonImg(imgIcon, AnchoBoton1, AltoBoton1, x, y, true, evt);
        BotonCrearArchivoJava.setText("Crear Archivo .java");
        BotonCrearArchivoJava.setFont(Fuente);
        BotonCrearArchivoJava.setBackground(coBotones);
        this.add(BotonCrearArchivoJava);

        //Boton elegir documento
        int x3 = x + BotonCrearArchivoJava.getWidth() + 5;
        ruta = RUTA + "BucarArchivo.png";
        img = Metodos.crearImagen(ruta, AltoBoton1, AltoBoton1);
        imgIcon = new ImageIcon(img);
        BotonElegirDocumento = Metodos.crearBotonImg(imgIcon, AltoBoton1, AltoBoton1, x3, y, true, evt);
        BotonElegirDocumento.setBackground(coBotones);
        this.add(BotonElegirDocumento);

        //Documento seleccionado
        x3 += BotonElegirDocumento.getWidth() + 5;
        EtiquetaNombreDeArchivo = Metodos.crearEtiqueta(
                "", ScrollEntrada.getWidth() / 2, AltoBoton1, x3, y, Fuente, coLetra);
        this.add(EtiquetaNombreDeArchivo);

        //Area de texto clase
        y = 5;
        x3 = (ScrollEntrada.getWidth() + 15);
        int w = (AnchoMax - x3) - y * 5;
        int h = (Titulo.getHeight() + (ScrollEntrada.getHeight() + BotonCrearArchivo.getHeight())) + y * 5;
        AreaClases = Metodos.crearArea(Fuente, coBotones, coLetra);
        AreaClases.addMouseWheelListener(this);
        AreaClases.addKeyListener(this);
        AreaClases.setFocusable(true);
        AreaClases.setEditable(false);
        AreaClases.setBackground(coAreas);
        ScrollClase = Metodos.crearScroll(AreaClases, w, h, x3, y);
        this.add(ScrollClase);

        //Tamaño de botones
        AnchoBoton1 = ScrollClase.getWidth() / 3;
        AltoBoton1 = ScrollClase.getWidth() / 14;

        int TaIMG = (AltoBoton1 / 2) + AltoBoton1 / 7;

        //Boton borrar Area de clase
        y = (ScrollClase.getY() + ScrollClase.getHeight()) + 5;
        x3 = (ScrollClase.getX() + ScrollClase.getWidth()) - (AnchoBoton1);
        ruta = RUTA + "BorrarTexto.png";
        img = Metodos.crearImagen(ruta, TaIMG, TaIMG);
        imgIcon = new ImageIcon(img);
        BotonBorrarAreaClases = Metodos.crearBotonImg(imgIcon, AnchoBoton1, AltoBoton1, x3, y, true, evt);
        BotonBorrarAreaClases.setText("Borrar texto");
        BotonBorrarAreaClases.setFont(Fuente);
        BotonBorrarAreaClases.setBackground(coBotones);
        this.add(BotonBorrarAreaClases);

        //Boton ver documento java
        x3 -= (AnchoBoton1 + AnchoBoton1 / 25);
        ruta = RUTA + "VerClase.png";
        img = Metodos.crearImagen(ruta, TaIMG, TaIMG);
        imgIcon = new ImageIcon(img);
        BotonVerClaseJava = Metodos.crearBotonImg(imgIcon, AnchoBoton1, AltoBoton1, x3, y, true, evt);
        BotonVerClaseJava.setText("Ver clase");
        BotonVerClaseJava.setFont(Fuente);
        BotonVerClaseJava.setBackground(coBotones);
        this.add(BotonVerClaseJava);

        //Tamaño de letras
        TaLetraEntrada = Fuente.getSize();
        TaLetraClase = Fuente.getSize();

        TaLetraAux = TaLetraEntrada;

    }

    //Guardar Texto a archivo
    private void guardarTextoEnNuevoArchivo() {
        if (AreaEntrada.getText() != null) {
            if (AreaEntrada.getText().length() > 5) {
                if (Archivo == null) {

                    //Creamos un archivo con los datos del area de entrada
                    String[] Extenciones = {".txt", ".flex"};
                    File ArchivoAux = Metodos.crearFicheroConRutaEspecificada(AreaEntrada.getText(), Extenciones);

                    //Si no esta vacio
                    if (ArchivoAux != null) {

                        //Guardamos
                        Metodos.guardarFichero(AreaEntrada.getText(), ArchivoAux);

                        resetearArea();

                        JOptionPane.showMessageDialog(null,
                                "Se a guardado correctamente " + ArchivoAux.getName(), "EXITO", JOptionPane.INFORMATION_MESSAGE);

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El documento " + Archivo.getName() + " ya existe", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Texto demaciado corto", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No tienes nada escrito", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }

    //Crear clase con JFlex
    private void crearConJFlex(String Ruta) throws SilentExit {
        //Creamos Archivo java
        String[] argv = {Ruta};
        jflex.Main.generate(argv);
    }

    //Crear clase con Cup
    private void crearConCup(String Ruta) throws SilentExit, IOException, Exception {

        //Creamos los archivos Syntax y sym
        String[] arr = {"-parser", "Sintax", Ruta};
        java_cup.Main.main(arr);

    }

    //Crear Archivo Java
    private void crearArchivos() {
        try {//Si el archivo no esta vacio
            if (Archivo != null) {

                //Tipo de clase
                if (!Archivo.getName().contains(".cup")) {
                    crearConJFlex(Ruta);
                    JOptionPane.showMessageDialog(null, "Se a creado la clase java de " + Archivo.getName() + " usando JFlex", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    crearConCup(Ruta);
                    JOptionPane.showMessageDialog(null, "Se an creado los archivos de " + Archivo.getName() + " usando Cup", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                }
                resetearArea();
                ventana.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Aun no has seleccionado un archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SilentExit ex) {//Eror JFlex
            JOptionPane.showMessageDialog(null, "Error inesperado >" + ex.getMessage() + "<", "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {//Error cup
            Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Seleccionar archivo
    private void seleccionarArchivoExistente() {
        try {
            //Obtenemos ruta
            String[] Extenciones = {".txt", ".flex", ".cup"};
            Ruta = Metodos.optenerRutaDeArchivo(Extenciones);

            if (Ruta != null) {//Comprobamos ruta

                //Creamos un archivo
                Archivo = new File(Ruta);

                //Optenemos datos
                String Texto = Metodos.obtenerDatosDelAcrchivo(Archivo);

                AreaEntrada.setText(Texto);
                AreaEntrada.setEditable(false);

                //Ponemos el nombre del archivo
                EtiquetaNombreDeArchivo.setText(Archivo.getName());

                ventana.repaint();
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error inesperado >" + ex.getMessage() + "<", "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error inesperado >" + ex.getMessage() + "<", "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Seleccionar archivo
    private void verClaseJava() {
        try {
            //Obtenemos ruta
            String[] Extenciones = {".java"};
            String ruta = Metodos.optenerRutaDeArchivo(Extenciones);

            if (ruta != null) {//Comprobamos ruta

                //Creamos un archivo
                File archivo = new File(ruta);

                //Optenemos datos
                String Texto = Metodos.obtenerDatosDelAcrchivo(archivo);

                AreaClases.setText(Texto);

                ventana.repaint();
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error inesperado >" + ex.getMessage() + "<", "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error inesperado >" + ex.getMessage() + "<", "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Resetrar area
    private void resetearArea() {
        //Actualizamos
        TaLetraEntrada = TaLetraAux;
        Font f = new Font(TIPO_DE_LETRA, ESTILO_DE_LETRA, TaLetraEntrada);

        AreaEntrada.setFont(f);

        Archivo = null;
        AreaEntrada.setText(null);
        AreaEntrada.setEditable(true);
        EtiquetaNombreDeArchivo.setText(null);
        ventana.repaint();
    }

    @Override//Eventos boton
    public void actionPerformed(ActionEvent e) {

        //Optener texto de un documento
        if (e.getSource() == BotonOptenerTexto) {
            try {
                resetearArea();
                String t = Metodos.optenerTextoDeArchivo(null);
                AreaEntrada.setText(t);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Crear Archivo
        if (e.getSource() == BotonCrearArchivo) {
            guardarTextoEnNuevoArchivo();
        }

        //Seleccionar archivo
        if (e.getSource() == BotonElegirDocumento) {
            seleccionarArchivoExistente();
        }

        //Seleccionar archivo
        if (e.getSource() == BotonVerClaseJava) {
            verClaseJava();
        }

        //Crear archivo java
        if (e.getSource() == BotonCrearArchivoJava) {
            crearArchivos();
        }

        //Borrar area Entrada
        if (e.getSource() == BotonBorrarTextoEntrada) {
            if (AreaEntrada.getText() != null) {
                resetearArea();
                ventana.repaint();
            }
        }

        //Borrar area Salida
        if (e.getSource() == BotonBorrarAreaClases) {
            if (AreaClases.getText() != null) {

                TaLetraClase = TaLetraAux;
                Font f = new Font(TIPO_DE_LETRA, ESTILO_DE_LETRA, TaLetraClase);
                AreaClases.setFont(f);

                AreaClases.setText(null);
                ventana.repaint();
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
            if (e.getComponent() == AreaEntrada) {
                TaLetraEntrada = Metodos.cambarTamañoDeLetra(AreaEntrada,
                        Orientacion, TaLetraEntrada, TIPO_DE_LETRA, ESTILO_DE_LETRA);
            }
            //Mover rueda dentro del area de clse
            if (e.getComponent() == AreaClases) {
                TaLetraClase = Metodos.cambarTamañoDeLetra(AreaClases,
                        Orientacion, TaLetraClase, TIPO_DE_LETRA, ESTILO_DE_LETRA);
            }

        } else /*Si NO tenemos precionada tecla de control*/ {

            int m = 40;

            //Moverse por el scroll de entrada
            if (e.getComponent() == AreaEntrada) {

                int pos = ScrollEntrada.getVerticalScrollBar().getValue();

                if (Orientacion == -1) {//Arriba
                    ScrollEntrada.getVerticalScrollBar().setValue(pos - m);
                } else {//Abajo
                    ScrollEntrada.getVerticalScrollBar().setValue(pos + m);
                }
            }

            //Moverse por el scroll de clase
            if (e.getComponent() == AreaClases) {

                int pos = ScrollClase.getVerticalScrollBar().getValue();

                if (Orientacion == -1) {//Arriba
                    ScrollClase.getVerticalScrollBar().setValue(pos - m);
                } else {//Abajo
                    ScrollClase.getVerticalScrollBar().setValue(pos + m);
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
}
