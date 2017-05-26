import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket

public class SendTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");

    InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
    int destPort = Integer.parseInt(args[1]);               // Destination port

    Socket sock = new Socket(destAddr, destPort);

    ItemQuote quote = new ItemQuote(1234567890987654L, "5mm Super Widgets", 
                                    1000, 12999, true, false);

    // Send text-encoded quote
    ItemQuoteEncoder coder = new ItemQuoteEncoderText();
    byte[] codedQuote = coder.encode(quote);
    System.out.println("Sending Text-Encoded Quote (" + 
                        codedQuote.length + " bytes): ");
    System.out.println(quote);
    sock.getOutputStream().write(codedQuote);

    // Receive binary-encoded quote
    ItemQuoteDecoder decoder = new ItemQuoteDecoderBin();
    ItemQuote receivedQuote = decoder.decode(sock.getInputStream());
    System.out.println("Received Binary-Encode Quote:");
    System.out.println(receivedQuote);

    sock.close();
  }
}