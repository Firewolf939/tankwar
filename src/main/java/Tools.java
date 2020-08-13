import javax.print.attribute.standard.Media;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tools {
    public static Image getImage(String name){
        return new ImageIcon("assets/images/"+name).getImage();

    }

//    public static void playerAudio(String fileName){
//        Media sound = new Media(new File("assets/audios/"+fileName).toURI().toString());
//    }
}
