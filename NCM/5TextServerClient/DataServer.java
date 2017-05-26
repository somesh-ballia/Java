import java.io.*;
import java.net.*;
import java.util.*;

public class DataServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

    String data = "SOMESHPATHAK"; 
    try
    {
        ServerSocket s = new ServerSocket(8989);
        Socket incoming = s.accept();

        try
        {
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();

            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter (outStream,true);
            Thread.sleep(2000);
	    
       while(true)
	 {
            for(int i=0;i<data.length();i++)
		{
			out.println(data.charAt(i));
			Thread.sleep(1000);
		}
	   }

       }
       catch(InterruptedException e)
        {
           e.printStackTrace();
        }
        finally
        {
            incoming.close();
        }
    }
    catch(IOException e)
     {
          e.printStackTrace();
     }

    }
}
