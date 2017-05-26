import java.net.*;   // for Socket
import java.io.*;    // for IOException and Input/OutputStream
import java.util.*;  // for ArrayList

public class TimelimitEchoProtocolFactory implements ProtocolFactory {

  public Runnable createProtocol(Socket clntSock, Logger logger) {
    return new TimelimitEchoProtocol(clntSock, logger);
  }
}

class TimelimitEchoProtocol implements Runnable {
  private static final int BUFSIZE = 32;  // Size (in bytes) of receive buffer
  private static final String TIMELIMIT = "10000";  // Default time limit (ms)
  private static final String TIMELIMITPROP = "Timelimit";  // Thread property

  private int timelimit;
  private Socket clntSock;
  private Logger logger;

  public TimelimitEchoProtocol(Socket clntSock, Logger logger) {
    this.clntSock = clntSock;
    this.logger = logger;
    // Get the time limit from the System properties or take the default
    timelimit = Integer.parseInt(System.getProperty(TIMELIMITPROP, TIMELIMIT));
  }

  public void run() {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());

    try {
      // Get the input and output I/O streams from socket
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      int recvMsgSize;                        // Size of received message
      int totalBytesEchoed = 0;               // Bytes received from client
      byte[] echoBuffer = new byte[BUFSIZE];  // Receive Buffer
      long endTime = System.currentTimeMillis() + timelimit;
      int timeBoundMillis = timelimit;

      clntSock.setSoTimeout(timeBoundMillis);

      // Receive until client closes connection, indicated by -1
      while ((timeBoundMillis > 0) &&     // catch zero values
	    ((recvMsgSize = in.read(echoBuffer)) != -1)) {
	  out.write(echoBuffer, 0, recvMsgSize);
	  totalBytesEchoed += recvMsgSize;
	  timeBoundMillis = (int) (endTime - System.currentTimeMillis()) ;
	  clntSock.setSoTimeout(timeBoundMillis);
      }

      entry.add("Client finished; echoed " + totalBytesEchoed + " bytes.");
    } catch (InterruptedIOException dummy) {
      entry.add("Read timed out");
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