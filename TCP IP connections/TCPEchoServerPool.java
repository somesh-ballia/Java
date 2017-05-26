import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoServerPool {

  public static void main(String[] args) throws IOException {

    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port> <Threads>");

    int servPort = Integer.parseInt(args[0]);    // Server port
    int numThreads = Integer.parseInt(args[1]);  // Get number of threads

    // Create a server socket to accept client connection requests
    final ServerSocket servSock = new ServerSocket(servPort);
 
    final Logger logger = new ConsoleLogger();       // Log messages to console

    // Create N-1 threads, each running an iterative server
    for (int i = 0; i < (numThreads - 1); i++) { 
      Thread thread = new Thread() {
        public void run() {
          DispatchLoop(servSock, logger);
        }
      };
      thread.start();
      logger.writeEntry("Created and started Thread = " + thread.getName());
    }
    logger.writeEntry("Iterative server starting in main thread " + 
                    Thread.currentThread().getName());
    DispatchLoop(servSock, logger);  // Use main thread as Nth iterative server
    /* NOT REACHED */
  }

  private static void DispatchLoop(ServerSocket servSock, Logger logger) {
    // Run forever, accepting and handling each connection
    for (;;) {
      try {
        Socket clntSock = servSock.accept();  // Block waiting for connection
        EchoProtocol protocol = new EchoProtocol(clntSock, logger);
        protocol.run();
      } catch (IOException e) {
        logger.writeEntry("Exception = " +  e.getMessage());
      }
    }
  }
}