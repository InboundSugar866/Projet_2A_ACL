import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class MyPanel extends JPanel {
    private final int width = 32*51;
    private final int height = 32*21;

    Image image;

    MyPanel(){
        URL url = getClass().getResource("./assets/rock-wall2.png");
        image = new ImageIcon(url).getImage();
        this.setPreferredSize(new Dimension(width,height));

    }
    public void paint(Graphics g){

        Graphics2D g2D = (Graphics2D) g;

        for (int i=0; i < 32*50; i=i+32){
            for (int j=0; j < 32*50; j=j+32){
                g2D.drawImage(image,i,0,null);
                g2D.drawImage(image,i,height - 32,null);
                g2D.drawImage(image,0,j,null);
                g2D.drawImage(image,width - 32,j,null);
            }
        }

        //g2D.setFont(new Font("Stencil",Font.BOLD,50));
        //g2D.drawString("YES", 100,100);

    }
}
