package interfazGrafica;

import java.io.IOException;

public class Principal {

    //Metodo main
    public static void main(String[] args) throws IOException {

        //Ventana
        Ventana ventana = new Ventana();
        ventana.repaint();
        ventana.setVisible(true);

        //Relog
        Thread Relog = new Thread(ventana);
        Relog.start();

    }

}


/*
        //Evento al cambiar tamaño de ventana
        this.ventana.addComponentListener(new ComponentAdapter() {

            int Ancho = this.Ancho;
            int Alto = this.Alto;
            int ev = 0;

            JFrame Ventana = ventana;

            @Override
            public void componentResized(ComponentEvent e) {

                Component c = (Component) e.getSource();
                
                if(c.getFocusTraversalKeysEnabled()){
                    System.out.println("Solto");
                }

                //Atualizamos tamaño
                int anc = ventana.getWidth();
                int alt = ventana.getHeight();

//                try {
//
//                    if (ev < 200) {
//                        //Comprobamos limites
//                        if (anc > 700 && alt > 600) {
//                            //Actualizamos tamaño
//                            actualizarTaDeComponentes(anc, alt);
//                        } else {
//                            ventana.setResizable(false);
//                            ventana.setSize(700, 600);
//                            //Actualizamos tamaño
//                            actualizarTaDeComponentes(700, 600);
//                        }
//                        ev = 0;
//                    } else {
//                        ev++;
//                    }
//
//                    this.Ventana.repaint();
//
//                } catch (IOException ex) {
//                    Logger.getLogger(PanelCrear.class.getName()).log(Level.SEVERE, null, ex);
//                }

            }
        });

*/
