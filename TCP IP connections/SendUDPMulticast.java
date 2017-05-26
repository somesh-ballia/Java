import java.net.*;  // for MulticastSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException

public class SendUDPMulticast {

  public static void main(String args[]) throws Exception {

    if ((args.length < 2) || (args.length > 3)) // Test for correct # of args
      throw new IllegalArgumentException(
        "Parameter(s): <Multicast Addr> <Port> [<TTL>]");

    InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
    if (!destAddr.isMulticastAddress())  // Test if multicast address
      throw new IllegalArgumentException("Not a multicast address");

    int destPort = Integer.parseInt(args[1]);  // Destination port

    int TTL;   // Time to live for datagram
    if (args.length == 3)
      TTL = Integer.parseInt(args[2]);
    else
      TTL = 1;  // Default TTL

    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets", 
                                    1000, 12999, true, false);

    MulticastSocket sock = new MulticastSocket(); // Multicast socket to sending
    sock.setTimeToLive(TTL);                      // Set TTL for all datagrams

    ItemQuoteEncoder encoder = new ItemQuoteEncoderText();  // Text encoding
    byte[] codedQuote = encoder.encode(quote);

    // Create and send a datagram
    DatagramPacket message = new DatagramPacket(codedQuote, codedQuote.length, 
                                                destAddr, destPort);
    sock.send(message);

    sock.close();
  }
}