import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame {

    MyPanel panel;
    MyFrame(){
        panel = new MyPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public static void main(String[] args){
        new MyFrame();
    }
}
