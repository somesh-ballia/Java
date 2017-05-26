/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package multithreadechoserver;
import java.io.*;
import java.net.*;
import java.util.*;
import java.*;
/**
 *
 * @author Wavejumper
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {
            int i =1;
            ServerSocket s = new ServerSocket(8181);

            while(true)
            {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming,i);
                Thread t = new Thread(r);
                t.start();
                i++;
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
    
}

class ThreadedEchoHandler implements Runnable
{
    private Socket incoming;
    private int counter;

    public ThreadedEchoHandler(Socket i , int c)
    {
        incoming = i;
        counter = c;

    }

    public void run()
    {

        // TODO code application logic here
        try
		{
			try
			{
				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();

				Scanner in = new Scanner(inStream);
				PrintWriter out  = new PrintWriter(outStream,true);

				out.println("Hello this is Somesh SERVER. Your count is : " + counter + " Enter BYE to exit");

				out.println("Avilable Processors :- " + Runtime.getRuntime().availableProcessors());
                                out.println(" Max Memory : " + Runtime.getRuntime().maxMemory());
                                
                               // Runtime.getRuntime().exec("shutdown -i");
			}
			finally
			{
                               // out.println("OOPS! You did something wrong.");
				incoming.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

    
}