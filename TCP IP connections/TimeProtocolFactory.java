import java.net.*;       // for Socket
import java.io.*;        // for IOException and Input/OutputStream
import java.util.*;      // for ArrayList

public class TimeProtocolFactory implements ProtocolFactory {

  static public final int BUFSIZE = 1024;   // Size of receive buffer

  public Runnable createProtocol(final Socket clntSock, final Logger logger) {
    return new Runnable() {
      public void run() {
        TimeProtocolFactory.handleClient(clntSock, logger);
      }
    };
  }

  static private void handleClient(Socket clntSock, Logger logger) {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());
    try {
      // Write date in default character encoding
      clntSock.getOutputStream().write((new Date() + "\n").getBytes()); 
    } catch (IOException e) {
      entry.add("Exception = " +  e.getMessage());
    }

    try {  // Close socket
      clntSock.close();
    } catch (IOException e) {
      entry.add("Exception = " +  e.getMessage());
    }

    logger.writeEntry(entry);
  }
}