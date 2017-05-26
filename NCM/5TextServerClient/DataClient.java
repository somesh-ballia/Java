import java.io.*;
import java.net.*;
import java.util.*;

public class DataClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	String data[]= new String[19];

    try
    {
        Socket s = new Socket("172.16.1.3",8989);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.flush();
        String line;
	int count=0;
        
        while((line = in.readLine()) != null)
        {
	    data[count]=line;   
            System.out.print(line);
	    count=(count+1)%12;
		
        }
        
    }
    catch(Exception e)
     {
          System.out.println(" \n Server Has Stopped\n\n\n");
	  for(int i=0;i<12;i++)
		{
			System.out.print(data[i]);
		}
	
     }
}
    
}