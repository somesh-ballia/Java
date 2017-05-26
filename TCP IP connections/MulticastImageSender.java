import java.net.*;  // for MulticastSocket and DatagramPacket
import java.io.*;   // for IOException

public class MulticastImageSender {

  private static final int TIMEOUT = 3000;     // Time between sends (milliseconds)
  private static final int MAXFILELEN = 65000; // File must fit in single datagram

  public static void main(String[] args) throws IOException, InterruptedException {

    if (args.length < 4)  // Test for correct # of args
      throw new IllegalArgumentException(
        "Parameter(s):  <Multicast Address> <Port> <TTL> <Image File> [<Image File>...]");

    InetAddress multicastAddress = InetAddress.getByName(args[0]);
    int destPort = Integer.parseInt(args[1]);  // Destination port of multicast packets
    int TTL = Integer.parseInt(args[2]);

    // Create a UDP multicast socket with any available local port
    MulticastSocket socket = new MulticastSocket();

    socket.setTimeToLive(TTL);  // Set the TTL

    for (int i=3; i < args.length; i++)
    {
      RandomAccessFile file = new RandomAccessFile(args[i], "r");

      if (file.length() > MAXFILELEN)
        throw new IOException("File too big");

      byte [] fileBuffer = new byte[(int) file.length()];
      file.read(fileBuffer);
      file.close();

      // Create a datagram to send
      DatagramPacket sendPacket = new DatagramPacket(fileBuffer,
        fileBuffer.length, multicastAddress, destPort);

      socket.send(sendPacket);  // Send the echo string
      System.out.println("Sent " + args[i] + " to " +
                  sendPacket.getAddress().getHostAddress() +
                 " on port " + sendPacket.getPort());
      Thread.sleep(TIMEOUT);
    }
    socket.close();
  }
}