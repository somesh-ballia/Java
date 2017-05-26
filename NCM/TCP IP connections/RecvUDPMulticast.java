import java.net.*;  // for MulticastSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException

public class RecvUDPMulticast implements ItemQuoteTextConst {

  public static void main(String[] args) throws Exception {

    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Multicast Addr> <Port>");

    InetAddress address = InetAddress.getByName(args[0]);  // Multicast address
    if (!address.isMulticastAddress())  // Test if multicast address
      throw new IllegalArgumentException("Not a multicast address");

    int port = Integer.parseInt(args[1]);  // Multicast port

    MulticastSocket sock = new MulticastSocket(port); // Multicast receiving socket
    sock.joinGroup(address);                          // Join the multicast group

    // Create and receive a datagram
    DatagramPacket packet = new DatagramPacket(
      new byte[MAX_WIRE_LENGTH], MAX_WIRE_LENGTH);
    sock.receive(packet);

    ItemQuoteDecoder decoder = new ItemQuoteDecoderText();  // Text decoding
    ItemQuote quote = decoder.decode(packet);
    System.out.println(quote);

    sock.close();
  }
}