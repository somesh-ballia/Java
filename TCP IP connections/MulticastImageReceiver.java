import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class MulticastImageReceiver extends JFrame {

  private JLabel picture;     // Label to contain image

  public MulticastImageReceiver() {
    super("Multicast Image Receiver");  // Set the window title
    setSize(300, 300);    // Set the window size

    picture = new JLabel("No image", SwingConstants.CENTER);
    JScrollPane scrollPane = new JScrollPane(picture);
    getContentPane().add(scrollPane, "Center");

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
  }

  public JLabel getPicture() {
    return picture;
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 2) // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s):  <Multicast Address> <Port>");

    final InetAddress multicastAddress = InetAddress.getByName(args[0]);

    if (!multicastAddress.isMulticastAddress())
      throw new IllegalArgumentException("Not a multicast address");

    int port = Integer.parseInt(args[1]);  // Destination port of multicast packets

    MulticastImageReceiver multicastImageReceiver = new MulticastImageReceiver();
    multicastImageReceiver.setVisible(true);
    new Thread(new MulticastImageReceiverThread(multicastImageReceiver, multicastAddress, port,
      "No Image")).start();
  }
}

class MulticastImageReceiverThread implements Runnable {
  private static final int MAXFILELEN = 65000; // File must fit in single datagram
  private InetAddress multicastAddress;        // Sender multicast address
  private int port;                            // Sender port
  Runnable updateImage;                        // Anonymous class for Swing event queue to update label
  String imageText;                            // Label text
  byte[] image = new byte[MAXFILELEN];         // Bytes of image
  boolean imageValid = false;                  // True if image contains valid bytes

  public MulticastImageReceiverThread(final MulticastImageReceiver frame, 
      InetAddress multicastAddress, int port, String initialImageText) {
    this.multicastAddress = multicastAddress;
    this.port = port;
    this.imageText = initialImageText;

    updateImage = new Runnable() {
      public void run() {
        JLabel picture = frame.getPicture();
        picture.setText(imageText);
        if (imageValid) {
          ImageIcon newImage = new ImageIcon(image);
          picture.setIcon(newImage);
          picture.setPreferredSize(new Dimension(newImage.getIconWidth(),
                                               newImage.getIconHeight()));
        } else
          picture.setIcon(null);
        picture.revalidate();
      }
    };
  }

  public void changeImage() {
    try {
      SwingUtilities.invokeAndWait(updateImage);  // Put update in queue and wait until handled
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void run() {
    // Create a datagram to receive
    DatagramPacket recvPacket = new DatagramPacket(image, MAXFILELEN);
    MulticastSocket socket;

    // Create a UDP multicast socket with the specified local port
    try {
      socket = new MulticastSocket(port);
      socket.joinGroup(multicastAddress);  // Join the multicast group
    } catch (IOException e) {
      imageText = "Problem with multicast socket";
      imageValid = false;
      changeImage();
      return;
    }

    for (;;) {
      try {
        socket.receive(recvPacket);  // Receive the image
      } catch (IOException e) {
        break;   // Assume exception due to file closing
      }

      imageText = "";                                          
      imageValid = true;
      changeImage();

      recvPacket.setLength(MAXFILELEN);   // You have to reset this!!!
    }
  }
}