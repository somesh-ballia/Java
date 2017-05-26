import java.net.*;  // for Socket
import java.io.*;   // for IOException and [File]Input/OutputStream

public class CompressClient {

  public static final int BUFSIZE = 256;  // Size of read buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 3)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Port> <File>");

    String server = args[0];               // Server name or IP address
    int port = Integer.parseInt(args[1]);  // Server port
    String filename = args[2];             // File to read data from

    // Open input and output file (named input.gz)
    FileInputStream fileIn = new FileInputStream(filename);
    FileOutputStream fileOut = new FileOutputStream(filename + ".gz");
  
    // Create socket connected to server on specified port
    Socket sock = new Socket(server, port);

    // Send uncompressed byte stream to server
    sendBytes(sock, fileIn);

    // Receive compressed byte stream from server
    InputStream sockIn = sock.getInputStream();
    int bytesRead;                      // Number of bytes read
    byte[] buffer = new byte[BUFSIZE];  // Byte buffer
    while ((bytesRead = sockIn.read(buffer)) != -1) {
      fileOut.write(buffer, 0, bytesRead);
      System.out.print("R");   // Reading progress indicator
    }
    System.out.println();      // End progress indicator line

    sock.close();     // Close the socket and its streams
    fileIn.close();   // Close file streams
    fileOut.close();
  }

  private static void sendBytes(Socket sock, InputStream fileIn) 
      throws IOException {
    OutputStream sockOut = sock.getOutputStream();
    int bytesRead;                      // Number of bytes read
    byte[] buffer = new byte[BUFSIZE];  // Byte buffer
    while ((bytesRead = fileIn.read(buffer)) != -1) {
      sockOut.write(buffer, 0, bytesRead);
      System.out.print("W");   // Writing progress indicator
    }
    sock.shutdownOutput();     // Done sending
  }
}