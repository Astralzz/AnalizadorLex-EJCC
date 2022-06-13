package interfazAnalizador;

import analizador_2.TokenPersonalizado;
import analizador_2.Analizador_2;
import analizador_1.Tokens;
import analizador_1.Analizador;
import analizador_3.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

//Recorrido
public class Recorrido extends Thread {

    //Tabla
    DefaultTableModel Modelo;
    private JTable Tabla;
    private JScrollPane Scroll;

    private File Archivo;
    private PrintWriter Escritura;
    private Reader Lectura, Lectura2;
    private Analizador_2 Analizar;

    private boolean Estado;
    private int Segundos;
    private int Nanosegundos = 0;
    private int OpcAnalizador;

    private String Texto;

    //Objeto;
    private PanelAnalizador Panel;
    private int NoFila;

    //Constructor
    public Recorrido(JTable Tabla, String Texto,
            JScrollPane Scroll, PanelAnalizador Panel, int OpcAnalizador) {

        String st = Panel.getBarra().getValue() + "00";
        Segundos = Integer.parseInt(st);

        //Estado de hilo
        Estado = true;

        //Objeto
        this.Panel = Panel;
        this.NoFila = Panel.getNoFila();

        //Tabla
        this.Tabla = Tabla;
        this.Scroll = Scroll;
        this.OpcAnalizador = OpcAnalizador;

        //Modelo
        this.Modelo = (DefaultTableModel) this.Tabla.getModel();

        //Texto
        this.Texto = Texto;

    }

    //Escribimos en el area
    private void escribir(int n, String c, String r) {
        this.Modelo.addRow(new Object[]{n, c, r});
    }

    //Pausar hilo
    private void pausa(int s, int n) throws InterruptedException {
        Recorrido.sleep(s, n);
    }

    //Destruir hilo
    public void destruir() {
        //Terminamos el bucle
        Estado = false;

        //Movemos el panel
        Scroll.getVerticalScrollBar().setValue(Scroll.getVerticalScrollBar().getMaximum());

        //Desbloqueamos botones
        Panel.ActualizarBotones();

        //devolvemos el numero de fila
        Panel.setNoFila(NoFila);
    }

    //Analizador 1
    public void Analizador1() throws FileNotFoundException {

        //Creamos un archivo auxiliar
        this.Archivo = new File("archivoAux.txt");
        this.Escritura = null;

        //Escribimos el texto area en el archivo
        Escritura = new PrintWriter(Archivo);
        Escritura.print(Texto);
        Escritura.close();

        //Optenemos el archivo
        Lectura = new BufferedReader(new FileReader("archivoAux.txt"));

        //Creamos un analizador
        Analizador lex = new Analizador(Lectura);

        //bucle infinito
        while (Estado) {

            try {

                //Si no esta pausado
                if (!Panel.isPausado()) {

                    //creamos un tooken
                    Tokens tokens = lex.yylex();

                    //Cuando el token vacio
                    if (tokens == null) {

                        //Destruimos hilo
                        destruir();
                        return;
                    }

                    //Datos
                    NoFila++;
                    String c, r;

                    //Tokens
                    switch (tokens) {
                        case ERROR -> {
                            c = lex.lexeme;
                            r = " No definido";
                        }

                        case Identificador, Numero, Reservadas -> {
                            c = lex.lexeme;
                            r = tokens.toString();
                        }
                        default -> {
                            c = tokens.toString();
                            r = tokens.toString();
                        }
                    }
                    //Error

                    //Agregaos
                    escribir(NoFila, c, r);

                    //Movemos el panel
                    Scroll.getVerticalScrollBar().setValue(Scroll.getVerticalScrollBar().getMaximum());

                    //Actualizamos
                    Panel.repaint();
                    Tabla.repaint();

                    //Pausamos
                    pausa(Segundos, Nanosegundos);

                } else {
                    pausa(30, 0);
                }

            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Recorrido.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    //Analizador 2
    public void Analizador2() throws IOException {

        //Creamos un archivo auxiliar
        this.Archivo = new File("archivoAux.txt");
        this.Escritura = null;

        //Escribimos el texto area en el archivo
        Escritura = new PrintWriter(Archivo);
        Escritura.print(Texto);
        Escritura.close();

        Lectura2 = new BufferedReader(new FileReader("archivoAux.txt"));

        Analizar = new Analizador_2(Lectura2);

        while (Estado) {

            try {

                //Si no esta pausado
                if (!Panel.isPausado()) {

                    //creamos un tooken
                    TokenPersonalizado token = Analizar.yylex();

                    //Cuando el token vacio
                    if (!Analizar.existenTokens()) {

                        //Destruimos hilo
                        destruir();
                        return;
                    }

                    //Datos
                    NoFila++;
                    String c = token.getToken();
                    String r = token.getToken();

                    //Agregaos
                    escribir(NoFila, c, r);

                    //Movemos el panel
                    Scroll.getVerticalScrollBar().setValue(Scroll.getVerticalScrollBar().getMaximum());

                    //Actualizamos
                    Panel.repaint();
                    Tabla.repaint();

                    //Pausamos
                    pausa(Segundos, Nanosegundos);

                } else {
                    pausa(30, 0);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Recorrido.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    //Analizador 3 Cup
    public void Analizador3() throws IOException, InterruptedException {
        //Creamos un lexer
        Lexer lexer = new Lexer(new StringReader(Texto));
        //Recorremos
        while (Estado) {

            //Si no esta pausado
            if (!Panel.isPausado()) {

                //creamos un tooken
                analizador_3.Tokens token = lexer.yylex();

                //Cuando el token vacio
                if (token == null) {

                    //Destruimos hilo
                    destruir();
                    return;
                }

                //Datos
                NoFila++;
                String c, r;

                switch (token) {
                    case Linea -> {
                        c = "\\n";
                        r = "Nueva linea";
                    }
                    case Comillas -> {
                        c = lexer.lexeme;
                        r = "Comillas";
                    }
                    case Cadena -> {
                        c = lexer.lexeme;
                        r = "Cadena";
                    }
                    case T_dato -> {
                        c = lexer.lexeme;
                        r = "Tipo de dato";
                    }
                    case If -> {
                        c = lexer.lexeme;
                        r = "Palabra reservada if";
                    }
                    case Else -> {
                        c = lexer.lexeme;
                        r = "Palabra reservada else";
                    }
                    case Do -> {
                        c = lexer.lexeme;
                        r = "Palabra reservada do";
                    }
                    case While -> {
                        c = lexer.lexeme;
                        r = "Palabra reservada while";
                    }
                    case For -> {
                        c = lexer.lexeme;
                        r = "Palabra reservada for";
                    }
                    case Igual -> {
                        c = lexer.lexeme;
                        r = "Signo de igual";
                    }
                    case Suma -> {
                        c = lexer.lexeme;
                        r = "Signo de suma";
                    }
                    case Resta -> {
                        c = lexer.lexeme;
                        r = "Signo de resta";
                    }
                    case Multiplicacion -> {
                        c = lexer.lexeme;
                        r = "Signo de Multiplicacion";
                    }
                    case Division -> {
                        c = lexer.lexeme;
                        r = "Signo de division";
                    }
                    case Op_logico -> {
                        c = lexer.lexeme;
                        r = "Operador logico";
                    }
                    case Op_incremento -> {
                        c = lexer.lexeme;
                        r = "Operador de incremento/decremento";
                    }
                    case Op_relacional -> {
                        c = lexer.lexeme;
                        r = "Operador relacional";
                    }
                    case Op_atribucion -> {
                        c = lexer.lexeme;
                        r = "Operador de atribucion";
                    }
                    case Op_booleano -> {
                        c = lexer.lexeme;
                        r = "Operador buleano";
                    }
                    case Parentesis_a -> {
                        c = lexer.lexeme;
                        r = "Parentesis de apertura";
                    }
                    case Parentesis_c -> {
                        c = lexer.lexeme;
                        r = "Parentesis de cierre";
                    }
                    case Llave_a -> {
                        c = lexer.lexeme;
                        r = "Llave de apertura";
                    }
                    case Llave_c -> {
                        c = lexer.lexeme;
                        r = "Llave de cierre";
                    }
                    case Corchete_a -> {
                        c = lexer.lexeme;
                        r = "Corchete de apertura";
                    }
                    case Corchete_c -> {
                        c = lexer.lexeme;
                        r = "Corchete de cierre";
                    }
                    case Main -> {
                        c = lexer.lexeme;
                        r = "Palabra reservada Main";
                    }
                    case P_coma -> {
                        c = lexer.lexeme;
                        r = "Punto y coma";
                    }
                    case Identificador -> {
                        c = lexer.lexeme;
                        r = "Identificador";
                    }
                    case Numero -> {
                        c = lexer.lexeme;
                        r = "Numero";
                    }
                    case ERROR -> {
                        c = lexer.lexeme;
                        r = "Simbolo no definido";
                    }
                    default -> {
                        c = lexer.lexeme;
                        r = "Simbolo no definido";
                    }
                }

                //Agregaos
                escribir(NoFila, c, r);

                //Movemos el panel
                Scroll.getVerticalScrollBar().setValue(Scroll.getVerticalScrollBar().getMaximum());

                //Actualizamos
                Panel.repaint();
                Tabla.repaint();

                //Pausamos
                pausa(Segundos, Nanosegundos);

            } else {
                pausa(30, 0);
            }
        }
    }

    @Override//Hilo
    public void run() {

        try {

            //Analizador escogido
            switch (OpcAnalizador) {
                case 1 -> {
                    Analizador1();
                }

                case 2 -> {
                    Analizador2();
                }

                case 3 -> {
                    Analizador3();
                }

                default -> {
                    JOptionPane.showMessageDialog(null, "No se escogio un analizador");
                    destruir();
                    return;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Recorrido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Recorrido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Recorrido.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Estado
    public boolean isEstado() {
        return Estado;
    }

    //Velocidad
    public void setVelocidad(int Segundos, int Nanosegundos) {
        this.Segundos = Segundos;
        this.Nanosegundos = Nanosegundos;
    }

    public int getVelocidad() {
        return Segundos;
    }

}
