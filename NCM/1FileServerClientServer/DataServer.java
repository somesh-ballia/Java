import java.io.*;
import java.net.*;
import java.util.*;

public class DataServer {

    public static void main(String[] args) {
	
	long FileLength;	
	// working with file
	try
	{
                File file = new File("audio.mp3");
		//File file = new File(args[0]);
		FileInputStream inputData = new FileInputStream(file);
		FileLength = file.length(); 

		byte data [] = new byte[(int)FileLength];

		inputData.read(data);
	
	
    // working with network	
    
        ServerSocket s = new ServerSocket(8989);
        Socket incoming = s.accept();

        try
        {
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();

            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter (outStream,true);
            Thread.sleep(5000);
		
	    out.println(FileLength);		    

     //  while(true)
	// {
            for(int i=0;i<FileLength;i++)
		{
			out.println(data[i]);
			//Thread.sleep(100);
		}
	 //  }

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
