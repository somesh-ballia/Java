
package datareciver;

import java.io.*;
import java.net.*;
import java.util.*;

public class Recive implements Runnable {

    Thread T;
    String threadName;
    String ipAddress;
    int port;
    byte data[]= new byte[2000];

    FileOutputStream incoming;
    Socket s;

    Recive(String name,String ip,int portNo)
    {
        threadName = name;
        ipAddress = ip;
        port =portNo;

        try
        {
         T = new Thread (this,name);
        System.out.println("Stream "+ name +" Download Started ");
        T.start();
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }
    }

    public void run()
    {

        try
        {
        incoming = new FileOutputStream("TempData"+threadName);
        s = new Socket(ipAddress,port);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.flush();
        String line;
	int count=0;
	System.out.println("Reciving data from stream "+ threadName);

        while((line = in.readLine()) != null)
        {
	    int i=Integer.parseInt(line);
	    byte b = (byte) i;
	    data[count] = b;
	    count++;
	    if(count == 2000)
		{
		  incoming.write(data);
		  count=0;
		}
        }

	if(count !=2000)
	{
		for(int j=0;j<count;j++)
				{
		         	    incoming.write(data[j]);
				}
	}

	System.out.println(threadName+ "Data Transfer Complete");
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }
    }
}
