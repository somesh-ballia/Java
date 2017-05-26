import java.io.*;
import java.net.*;
import java.util.*;

public class DataClient {

       	
   static byte data[]= new byte[2000];

    public static void main(String[] args) {	

    try
    {
	// working with file
	
	FileOutputStream outData = new FileOutputStream("output.mp3");
	
	// working with network
	
        Socket s = new Socket("172.16.1.3",8989);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.flush();
        String line;
	int count=0;
	System.out.println("Reciving data");
        
        while((line = in.readLine()) != null)
        {   
             		
	    int i=Integer.parseInt(line);
	    byte b = (byte) i;
	    data[count] = b;
	    count++;

	
	    if(count == 2000)
		{		
		  outData.write(data);
		  count=0;
			
		}		
        }
	
	if(count !=2000)
	{
		for(int j=0;j<count;j++)
				{
		         	    outData.write(data[j]);
				}
	}	 

	System.out.print("Done");
        
    }
    catch(Exception e)
     {
          e.printStackTrace();
	  
	
     }
}
    
}