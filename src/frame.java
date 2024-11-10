import javax.swing.*;
import java.awt.*;

public class frame extends JFrame {

    Image img = Toolkit.getDefaultToolkit().createImage("KOLO.png");

    frame(){

        panel panel = new panel();
        this.setTitle("KOLLEKTORo");
        this.add(panel);
        this.setVisible(true);
        this.setResizable(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(img);
        
    }
    
}
