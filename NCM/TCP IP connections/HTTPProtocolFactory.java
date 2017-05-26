import java.net.*;   // for Socket
import java.io.*;    // for IOException and Input/OutputStream
import java.util.*;  // for ArrayList

class HTTPProtocolFactory implements ProtocolFactory {
  private static final int BUFSIZE = 32;          // Buffer size
  private static final String DOCROOTPROP = "DocumentRoot"; // Doc root property

  public Runnable createProtocol(final Socket clntSock, final Logger logger) {
    return new Runnable() {
      public void run() {
        String documentRoot = System.getProperty(DOCROOTPROP, "");
        HTTPProtocolFactory.handleClient(clntSock, logger, documentRoot);
      }
    };
  }

  static private void handleClient(Socket clntSock, Logger logger, 
                                   String documentRoot) {
    InputStream in = null;
    OutputStream out = null;
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());
    try {
      // Get the input and output I/O streams from socket
      in = clntSock.getInputStream();
      out = clntSock.getOutputStream();

      String filename = documentRoot + getFilename(in);
      entry.add("File = " + filename);
      FileInputStream fin = new FileInputStream(filename);
      String resp = "HTTP/1.0 200 OK\r\n\r\n";
      // Encoding for getBytes is specified in HTTP spec
      out.write(resp.getBytes("ISO-8859-1"));

      byte[] buf = new byte[BUFSIZE];
      int bytesRead;
      while ((bytesRead = fin.read(buf)) != -1)
        out.write(buf, 0, bytesRead);

      fin.close();
    } catch (Exception e) {
      String resp = "HTTP/1.0 404 File Not Found\r\n\r\nNot Found or Other I/O Error";
      entry.add("ERROR:  File not found");
      try {
        out.write(resp.getBytes("ISO-8859-1"));
      } catch (Exception dummy) {}
    }

    try {  // Close socket
      out.flush();
      clntSock.close();
    } catch (IOException e) {
      entry.add("Exception = " +  e.getMessage());
    }

    logger.writeEntry(entry);
  }

  private static String getFilename(InputStream in) throws IOException {
    // Read request
    StreamTokenizer tokenizer = new StreamTokenizer(new InputStreamReader(in));
    tokenizer.ordinaryChar('/');   // By default the single / character is a comment
    tokenizer.wordChars('!', '~'); // Set these characters in a URL as a word
    tokenizer.nextToken();
    if ((tokenizer.ttype != tokenizer.TT_WORD) || (tokenizer.sval.compareTo("GET") != 0)) {
      throw new IOException("Unhandlable request");
    }

    if (tokenizer.nextToken() != tokenizer.TT_WORD) {
      throw new IOException("Unhandlable request");
    }

    return tokenizer.sval.substring(1); // Kill leading "/"
  }
}