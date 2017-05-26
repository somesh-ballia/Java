import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class RecvUDP implements ItemQuoteTextConst {

  public static void main(String[] args) throws Exception {

    if (args.length != 1 && args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

    int port = Integer.parseInt(args[0]);   // Receiving Port

    DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving
    ItemQuoteDecoder decoder = (args.length == 2 ?   // Which encoding
				new ItemQuoteDecoderText(args[1]) :
				new ItemQuoteDecoderText() );

    DatagramPacket packet = new DatagramPacket(
      new byte[MAX_WIRE_LENGTH], MAX_WIRE_LENGTH);
    sock.receive(packet);

    ItemQuote quote = decoder.decode(packet);
    System.out.println(quote);

    sock.close();
  }
}