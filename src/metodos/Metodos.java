package metodos;

import interfazCrear.PanelCrear;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

//METODOS ESTATCOS
public final class Metodos {

    //Crear JTabbedPane
    public static JTabbedPane crearJTaPanel(int Ancho, int Alto,
            int x, int y, Color color, Color ColorF, Font Fuente) {

        JTabbedPane JTpanel = new JTabbedPane();
        JTpanel.setBounds(x, y, Ancho, Alto);
        JTpanel.setBackground(color);
        JTpanel.setForeground(ColorF);
        JTpanel.setFont(Fuente);
        return JTpanel;

    }

    //Crear JTabbedPane
    public static JDesktopPane crearJDeskPanel(Color color) {
        JDesktopPane JDpanel = new JDesktopPane();
        JDpanel.setBackground(color);
        JDpanel.setLayout(null);
        return JDpanel;
    }

    //Crear imagen
    public static Image crearImagen(String Ruta, int Ancho, int Alto) throws IOException {
        BufferedImage buffer = ImageIO.read(new File(Ruta));
        Image img = buffer.getScaledInstance(Ancho, Alto, Image.SCALE_DEFAULT);
        return img;
    }

    //Crear etiqueta
    public static JLabel crearEtiqueta(String Titulo, int w,
            int h, int x, int y, Font Fuente, Color color) {
        //Fecha
        JLabel Etiqueta = new JLabel(Titulo);
        Etiqueta.setSize(w, h);
        Etiqueta.setLocation(x, y);
        Etiqueta.setForeground(color);
        Etiqueta.setFont(Fuente);
        return Etiqueta;
    }

    //Crear scroll
    public static JScrollPane crearScroll(JTextArea Area, int Ancho, int Alto, int x, int y) {
        JScrollPane Cuadro = new JScrollPane(Area);
        Cuadro.setSize(Ancho, Alto);
        Cuadro.setLocation(x, y);
        return Cuadro;
    }

    //Crear Slider
    public static JSlider crearSlider(int orientacion, int x, int y, int w,
            int h, int min, int max, int pos, int tc) {
        JSlider slider = new JSlider(orientacion, min, max, pos);
        slider.setBounds(x, y, w, h);
        slider.setPaintTicks(true); //las rayitas que marcan los números
        slider.setMajorTickSpacing(tc); // de cuanto en cuanto los números en el slider
        slider.setMinorTickSpacing(tc); //las rayitas de cuanto en cuanto
        slider.setFocusable(false);
        slider.setPaintLabels(true);
        slider.setValue(pos);
        return slider;
    }

    //Crear Area
    public static JTextArea crearArea(Font Fuente, Color coFondo, Color coLetra) {
        JTextArea Area = new JTextArea();
        Area.setFont(Fuente);
        Area.setForeground(coLetra);
        Area.setBackground(coFondo);
        Area.setLineWrap(false);
        Area.setWrapStyleWord(false);
        return Area;
    }

    //Crear boton con texto
    public static JButton crearBotonText(String Titulo, int w, int h, int x, int y,
            Color coFondo, Color coLetra, Font Fuente, boolean estado, ActionListener evt) {

        JButton boton = new JButton(Titulo);
        boton.setSize(w, h);
        boton.setLocation(x, y);
        boton.setFont(Fuente);
        boton.setEnabled(estado);
        boton.addActionListener(evt);
        boton.setBackground(coFondo);
        boton.setForeground(coLetra);
        return boton;

    }

    //Crear boton con Imagen
    public static JButton crearBotonImg(ImageIcon img, int w, int h, int x, int y,
            boolean estado, ActionListener evt) {
        JButton boton = new JButton(img);
        boton.setSize(w, h);
        boton.setLocation(x, y);
        boton.setEnabled(estado);
        boton.setFocusable(false);
        boton.addActionListener(evt);
//        boton.setFocusPainted(false);
//        boton.setBorderPainted(false);
//        boton.setContentAreaFilled(false);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        return boton;
    }

    //Seleccionar archivo
    public static String optenerRutaDeArchivo(String[] Extenciones) throws FileNotFoundException, SQLException, IOException {
        //Creamos un selector de archivos
        JFileChooser file = new JFileChooser();
        JDialog Seleccion = new JDialog();
        file.showOpenDialog(Seleccion);

        //Seleccionamos la imagen
        File archivo = file.getSelectedFile();

        Seleccion.dispose();

        //Verificamos que no este vacio
        if (archivo != null) {
            //Obtenemos la ruta
            String ruta = archivo.getPath();
            //Comprovamos que el array este vacio
            if (Extenciones == null) {
                return ruta;
                //Comprovamos que contengan archivos validos
            } else if (buscarDatos(Extenciones, ruta)) {
                return ruta;
            } else {
                String Mensaje = "Solo se acceptan archivos con extencion ";
                for (int i = 0; i < Extenciones.length; i++) {
                    if (i < Extenciones.length - 1) {
                        Mensaje += Extenciones[i] + " ";
                    } else {
                        Mensaje += Extenciones[i];
                    }
                }

                JOptionPane.showMessageDialog(null, Mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes de seleccionar un archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    //Optener datos de un archivo
    public static String obtenerDatosDelAcrchivo(File Archivo) throws FileNotFoundException, IOException {
        try {

            //Ponemos los datos de el archivo
            String linea;
            String texto = "";

            FileReader fr = new FileReader(Archivo);
            BufferedReader br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }

            fr.close();

            return texto;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //Seleccionar una ruta para guardar un archivo
    public static File crearFicheroConRutaEspecificada(String Contenido, String[] Extenciones) {

        //Creamos una ventana auxiliar
        JFileChooser guardar = new JFileChooser();
        guardar.showSaveDialog(null);
        guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        //Verificamos que no este vacio
        if (guardar.getSelectedFile() != null) {

            //Creamos un archivo con la ruta seleccionada
            File archivo = guardar.getSelectedFile();

            //Comprobamos tamaño
            if (archivo.getName().length() > 4) {

                //Optenemos nombre
                String nombre = archivo.getName();

                //Caracter
                char c = '.';

                //Comprobamos que el nombre contenga un punto
                if (nombre.contains(String.valueOf(c))) {

                    //buscamos las extenciones correctas
                    if (buscarExtenciones(Extenciones, nombre, c)) {
                        return archivo;
                    } else {
                        String Mensaje = "Solo se acceptan archivos con extencion ";
                        for (int i = 0; i < Extenciones.length; i++) {
                            if (i < Extenciones.length - 1) {
                                Mensaje += Extenciones[i] + " ";
                            } else {
                                Mensaje += Extenciones[i];
                            }
                        }
                        JOptionPane.showMessageDialog(null, Mensaje, "EXTENCION INVALIDA", JOptionPane.ERROR_MESSAGE);
                        return null;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El nombre debe de tener un punto", "NOMBRE INVALIDO", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nombre demaciado corto", "NOMBRE INVALIDO", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes de seleccionar una ruta", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    //Guardar fichero
    public static void guardarFichero(String Contenido, File archivo) {
        try {

            //Creamos un FileWriter
            FileWriter escribir = new FileWriter(archivo, false);
            //Agregamos contenido
            escribir.write(Contenido);
            //Cerramos 
            escribir.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error inesperado --> " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    //Buscar cadenas
    public static boolean buscarExtenciones(String[] Array, String Cadena, char Caracter) {

        //Optenemos la pocicion del punto
        int pos = optenerPrimerPosicionDeUnCaracter(Cadena, Caracter);

        //Sub cadena
        String supCadena = Cadena.substring(pos);

        //Buscamos datos
        for (String Array1 : Array) {
            if (supCadena.equals(Array1)) {
                //Si tiene
                return true;
            }
        }

        //No tiene
        return false;

    }

    //Optener posicion de un caracter
    public static int optenerPrimerPosicionDeUnCaracter(String Cadena, char Caracter) {

        //Recorremos
        for (int i = 0; i < Cadena.length(); i++) {
            if (Cadena.substring(i, i + 1).equals(String.valueOf(Caracter))) {
                return i;
            }
        }

        return 0;

    }

    //Buscar cadenas
    public static boolean buscarDatos(String[] Array, String Cadena) {

        //Buscamos datos
        for (String Array1 : Array) {
            if (Cadena.contains(Array1)) {
                //Si tiene
                return true;
            }
        }

        //No tiene
        return false;

    }

    //Comprovar valides del achivo
    public static boolean comprobarValidesDelArchivoSeleccionado(File archivo) {
        //Comprobamos tamaño
        if (archivo.getName().length() > 4) {

            //Optenemos nombre
            String nombre = archivo.getName();

            //Caracter
            char c = '.';

            //Comprobamos que el nombre contenga un punto
            if (nombre.contains(String.valueOf(c))) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "El nombre debe de tener un punto", "NOMBRE INVALIDO", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nombre demaciado corto", "NOMBRE INVALIDO", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //Optener texto de un archivo
    public static String optenerTextoDeArchivo(String[] Extenciones) throws SQLException, IOException {

        //Optenemos ruta
        String rutaAux = optenerRutaDeArchivo(Extenciones);

        if (rutaAux != null) {//Comprobamos ruta

            //Creamos un archivo
            File ArchivoAux = new File(rutaAux);

            if (!comprobarValidesDelArchivoSeleccionado(ArchivoAux)) {
                return null;
            }

            //Optenemos datos
            String Texto = Metodos.obtenerDatosDelAcrchivo(ArchivoAux);

            if (Texto != null) { //Comprobar si el texto no esta vacio

                if (Texto.length() < 500000) {

                    return Texto;

                } else {
                    JOptionPane.showMessageDialog(null, "Archivo con demaciados caracteres", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else {//Error
                JOptionPane.showMessageDialog(null, "Error inesperado", "ERROR", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            return null;
        }

    }

    //Cambiar tamaño de letra
    public static int cambarTamañoDeLetra(JTextArea Area, int Orientacion, int Size, String Tipo, int Estilo) {

        //Tamaño de la letra
        int size = Size;

        //Arriba
        if (Orientacion == -1) {

            //Maximo amaño de letra
            if (size < 70) {
                size++;
                Font fuente = new Font(Tipo, Estilo, size);
                Area.setFont(fuente);
                return size;
            } else {
                return size;
            }

        } else /*Abajo*/ {

            //Minimo amaño de letra
            if (size > 3) {
                size--;
                Font fuente = new Font(Tipo, Estilo, size);
                Area.setFont(fuente);
                return size;
            } else {
                return size;
            }

        }

    }

    //Crear una tabla
    public static JTable CrearTabla(int Filas, int Columnas,
            String[] titulos, Object[][] DatosTabla) {

        //Diseño de la tabla de clientes
        DefaultTableModel ModeloTabla = new DefaultTableModel();
        ModeloTabla.setRowCount(Filas);
        ModeloTabla.setColumnCount(Columnas);
        ModeloTabla.fireTableDataChanged();

        //Darle el modelo a la tabla
        JTable Tabla = new JTable();
        Tabla.setModel(ModeloTabla);

        //Llenar tabla
        for (int i = 0; i < Filas; i++) {
            for (int j = 0; j < Columnas; j++) {
                Tabla.setValueAt(DatosTabla[i][j], i, j);
            }
        }

        if (Columnas != 0) {
            //Poner titulos
            for (int i = 0; i < titulos.length; i++) {
                Tabla.getColumn(Tabla.getColumnName(i)).setHeaderValue(titulos[i]);
            }
        }

        return Tabla;

    }

    //Retornar ruta sin nombre del archivo
    public static String retornarRutaSinNombrte(String Ruta) {

        //Optenemos tamaño
        int i = Ruta.length() - 1;

        //Recoremos
        while (i >= 0) {

            char c = Ruta.charAt(i);

            //Verificamos
            if (c == '\\') {
                return Ruta.substring(0, i + 1);
            }

            i--;
        }

        return null;
    }

    //Crear scroll tabla
    public static JScrollPane crearScrollTabla(JTable Tabla, int Ancho, int Alto, int x, int y) {
        JScrollPane Cuadro = new JScrollPane(Tabla);
        Cuadro.setSize(Ancho, Alto);
        Cuadro.setLocation(x, y);
        return Cuadro;
    }
}
