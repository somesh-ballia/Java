import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Screenshot {
  public static void main(String[] args) throws Exception {
    Screenshot ss = new Screenshot();
  }

  public Screenshot(){
    JFrame frame = new JFrame("Screen Shot Frame.");
    JButton button = new JButton("Capture Screen Shot");
    button.addActionListener(new MyAction());
    JPanel panel = new JPanel();
    panel.add(button);
    frame.add(panel, BorderLayout.CENTER);
    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public class MyAction implements ActionListener{
    public void actionPerformed(ActionEvent ae){
      try{
        String fileName = JOptionPane.showInputDialog(null, "Enter file name : ","Roseindia.net", 1);
        if (!fileName.toLowerCase().endsWith(".gif")){
          JOptionPane.showMessageDialog(null, "Error: file name must end with \".gif\".", "Roseindia.net", 1);
        }
        else{
          Robot robot = new Robot();
          BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
          ImageIO.write(image, "gif", new File(fileName));
          JOptionPane.showMessageDialog(null, "Screen captured successfully.","Roseindia.net", 1);
        }
      }
      catch(Exception e){}
    }
  }
}