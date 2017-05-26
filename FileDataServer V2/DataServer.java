import java.io.*;
import java.net.*;
import java.util.*;

public class DataServer {

    public static void main(String[] args) {
	
	long FileLength;
	long l1;
	long l2;
	// working with file
	try
	{
                File file = new File("audio.mp3");
		//File file = new File(args[0]);
		FileInputStream DataSource1 = new FileInputStream(file);
		FileInputStream DataSource2 = new FileInputStream(file);
		FileInputStream DataSource3 = new FileInputStream(file);
		FileInputStream DataSource4 = new FileInputStream(file);
		FileInputStream DataSource5 = new FileInputStream(file);
		FileInputStream DataSource6 = new FileInputStream(file);

		FileLength = file.length(); 
		l2=FileLength%5;
		FileLength -= l2;
		l1 = FileLength/5;
		byte data1 [] = new byte[(int)l1];
		byte data2 [] = new byte[(int)l1];
		byte data3 [] = new byte[(int)l1];
		byte data4 [] = new byte[(int)l1];
		byte data5 [] = new byte[(int)l1];
		byte data6 [] = new byte[(int)l2];
		
		DataSource1.read(data1,0,l1);
		DataSource2.read(data2,(l1+1),(2*l1));
                DataSource3.read(data2,((2*l1)+1),(3*l1));
		DataSource4.read(data2,((3*l1)+1),(4*l1));
		DataSource5.read(data2,((4*l1)+1),(5*l1));
		DataSource6.read(data2,((5*l1)+1),(l2));		
	
	
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

     
            for(int i=0;i<FileLength;i++)
		{
			out.println(data[i]);
			//Thread.sleep(100);
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
