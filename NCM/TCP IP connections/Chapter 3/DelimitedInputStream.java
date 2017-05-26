import java.io.*;  // for InputStream and ByteArrayOutputStream

public class DelimitedInputStream {
  private InputStream in;    // Stream to parse
  private int next[];
  private byte[] lastDelim;
  private boolean EOS;

  public DelimitedInputStream(InputStream underlying) {
    this.in = underlying;
    next = null;
    lastDelim = null;
    EOS = false;
  }

  void preProcess(byte delim[]) {
    if (delim != lastDelim) { // Shortcut if it is the same delimiter
      next = new int[delim.length];
      int i = 0;
      int j = -1;
      next[i] = j;
      while (i < delim.length-1) {
	while (j > -1 && delim[i] != delim[j])
          j = next[j];
        next[++i] = ++j;
      }
      lastDelim = delim;
    }
  }

  // Read bytes up to and including delimiter;
  // Return bytes read excluding delimiter.
  public byte[] nextToken(byte delim[]) throws IOException {
    if (EOS)
      throw new EOFException("nextToken called after stream ended");
    if (delim.length == 0)
      return new byte[0];
    preProcess(delim);  // delim.length > 0
    // Create a buffer to hold the token
    ByteArrayOutputStream token = new ByteArrayOutputStream();
    int seen = 0;  // # bytes of delimiter matched so far
    int nextByte;  // Next byte in input stream to process

    while (seen < delim.length && !EOS) {
      nextByte = in.read();
      if (nextByte == -1)
        EOS = true;
      else if ((byte) nextByte == delim[seen])
        seen += 1;
      else { // !EOS && nextByte != delim[seen]
        while ((byte) nextByte != delim[seen] && seen > 0) {
          token.write(delim,0,seen-next[seen]);
          seen = next[seen];
      }
      if ((byte) nextByte != delim[seen])
        token.write(nextByte);
      else
        seen += 1;
      }
    }
    // seen == delim.length || EOS
    if (seen < delim.length)
      token.write(delim,0,seen);
    return token.toByteArray();
  }
}