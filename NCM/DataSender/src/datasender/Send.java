/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datasender;

import java.io.*;
import java.net.*;
import java.util.*;

class Send implements Runnable
{
    Thread T;
    ServerSocket s ;
    Socket incoming;
    InputStream inStream;
    OutputStream outStream;
    Scanner in;
    PrintWriter out;
    String address;
    File file;
    FileInputStream dataSource;
    String threadName;
    int port;
    long start=0;
    long end = 0;
    long size =0;


    Send(String name,long first,long last,String location,int portNo)
    {
        start = first;
        end = last;
        size = last-first-1;
        address = location;
        threadName = name;
        port = portNo;

       try
       {       

        // FILE HANDELING
        file = new File(address);
        dataSource = new FileInputStream(file);
      
        T = new Thread (this,name);
        System.out.println("Strean "+ name +" Started ");        
        T.start();
       }
       catch(IOException e)
       {
           System.out.println("ERROR IN CREATING FILE SERVER");
            e.printStackTrace();
       }
       catch(Exception e)
        {
             System.out.println("Sorry somes error has occoured1");
              e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
           s = new ServerSocket(port);
           incoming = s.accept();          
           inStream = incoming.getInputStream();
           outStream = incoming.getOutputStream();
           in = new Scanner(inStream);
           out = new PrintWriter (outStream,true);
           byte data [] = new byte[(int)size];
           try
           {
               System.out.println(start + " "+end);
           dataSource.read(data,(int)start,(int)end);
           System.out.println("Starting Transfer Via " + threadName + " Size "+ size);
           for(int i=0;i<size;i++)
		{
			out.println(data[i]);
		}
            System.out.println("Ending Transfer Via " + threadName);
           }
           catch(IOException e)
           {
               System.out.println("Sorry! Error in reading file");
                e.printStackTrace();
           }
        }
        catch(Exception e)
        {
              System.out.println("Sorry somes error has occoured");
               e.printStackTrace();
        }
    }

}

