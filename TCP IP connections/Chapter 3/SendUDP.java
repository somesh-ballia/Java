import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException

public class SendUDP {

  public static void main(String args[]) throws Exception {

    if (args.length != 2 && args.length != 3)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Destination>" +
					 " <Port> [<encoding]");

    InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
    int destPort = Integer.parseInt(args[1]);               // Destination port

    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets",
                                    1000, 12999, true, false);

    DatagramSocket sock = new DatagramSocket(); // UDP socket for sending

    ItemQuoteEncoder encoder = (args.length == 3 ?
				new ItemQuoteEncoderText(args[2]) :
				new ItemQuoteEncoderText());

    byte[] codedQuote = encoder.encode(quote);

    DatagramPacket message = new DatagramPacket(codedQuote, codedQuote.length, 
                                                destAddr, destPort);
    sock.send(message);

    sock.close();
  }
}