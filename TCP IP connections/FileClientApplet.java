import java.net.*;        // for Socket
import java.io.*;         // for OutputStream
import java.awt.event.*;  // for ActionListener
import javax.swing.*;     // for Swing components

public class FileClientApplet extends JApplet implements ActionListener {

  private static final int PORT = 5000;  // Default port

  public void init() {
    text = new JTextArea(8, 20);
    getContentPane().add(new JScrollPane(text), "Center");

    saveButton = new JButton("Save");
    getContentPane().add(saveButton, "South");
    saveButton.addActionListener(this);
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == saveButton) {
      try {
        // Create socket connected to server on specified port
        Socket socket = new Socket(getCodeBase().getHost(), PORT);

        socket.getOutputStream().write(text.getText().getBytes());
     
        socket.close();
        text.setText(null);
      } catch (Exception e) {
        text.setText(null);
        text.append(e.getMessage());
      }
    }
  }

  private JButton saveButton;
  private JTextArea text;
}