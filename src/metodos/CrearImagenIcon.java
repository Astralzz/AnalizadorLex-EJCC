package metodos;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CrearImagenIcon extends ImageIcon {

    //Variables
    private String URL;
    private int Ancho, Alto;
    private BufferedImage bufferedImage;
    

    //Constructor
    public CrearImagenIcon(int Ancho, int Alto, String URL) throws IOException {

        //Componentes
        this.URL = URL;
        this.Ancho = Ancho;
        this.Alto = Alto;

        this.crearImagen();

    }

    //Crear imagen
    private void crearImagen() throws IOException {

        bufferedImage = ImageIO.read(new File(URL));
        Image image = bufferedImage.getScaledInstance(Ancho, Alto, Image.SCALE_DEFAULT);

        this.setImage(image);

    }

}
