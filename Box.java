import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Box implements Icon
{
   public static void main(String args[])
   {
      JFrame frame = new JFrame();
      Box a = new Box("AC");
      a.translate(10,10);
      Icon icon = a;
      
      final JLabel label = new JLabel(icon);
      frame.setLayout(new FlowLayout());
      frame.add(label);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }
   
   public Box(String text)
   {
      
      if(text == "")
         text = " ";      
      this.text = text;   
   }
   
   public void paintIcon(Component c, Graphics g, int x, int y)
   {
      Graphics2D g2 = (Graphics2D) g;
           
      JLabel label = new JLabel(text);
      label.setFont(new Font("Sans", Font.BOLD, fs));
      dim = label.getPreferredSize();
      //label.setBounds(0, 0, dim.width, dim.height);
      
      g2.setColor(Color.white);
      g2.fillRect(x, y, dim.width +10 , dim.height);
      g2.setColor(Color.black);
      g2.drawRect(x, y, dim.width +10, dim.height);
      
      g2.setFont(new Font("Sans", Font.BOLD, fs));
      g2.drawString(text,x+7,y+dim.height*3/4);
              
      //g2.translate(5, y);
      //label.paint(g2);
      //g2.translate(-5, y);

   }
   public void translate(int dx,int dy)
   {
      x += dx;
      y += dy;
   }
   
   public int getIconHeight()
   {
      JLabel label = new JLabel(text);
      label.setFont(new Font("Sans", Font.BOLD, fs));
      dim = label.getPreferredSize();
      return dim.height +1;
   }

   public int getIconWidth()
   {
      JLabel label = new JLabel(text);
      label.setFont(new Font("Sans", Font.BOLD, fs));
      dim = label.getPreferredSize();
      return dim.width +11;
   }

   private int fs = 30;
   private String text;
   private JLabel label;
   private Dimension dim;
   private int x;
   private int y;
}
