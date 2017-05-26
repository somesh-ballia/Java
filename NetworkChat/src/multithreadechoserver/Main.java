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
    private String HOST = "HOST";
    private String GUEST;

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
                                InputStreamReader ir = new InputStreamReader(System.in) ;
                                BufferedReader br = new BufferedReader(ir);

				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();

				Scanner in = new Scanner(inStream);
				PrintWriter out  = new PrintWriter(outStream,true);

				out.println("Hello this is CHAT SERVER. Your count is : " + counter + " Enter BYE to exit");

                                while(in.hasNextLine() == false)
                                {
                                    out.println("Please Enter Your Name");
                                    GUEST = in.nextLine();
                                }

                                if(GUEST.length() != 0)
                                {
                                    System.out.println(GUEST + " HAS ENTERED CHAT");
                                    out.println(" The chat has begun ");
                                    boolean done = false;
                                    while(!done /* && in.hasNextLine()*/)
                                        {
                                        
                                            out.println(GUEST + ":" );
                                            String message = in.nextLine();

                                            if(message.trim().equals("BYE"))
                                                {
                                                    done = true;
                                                }
                                            if(message.length() != 0)
                                            {
                                                System.out.println(GUEST + " : " + message );

                                            }
                                        
                                        }
                                }
                                else
                                {


                                }
			}
			finally
			{
                              incoming.close();
			}
		}
		catch(IOException e)
		{
                        counter --;
			e.printStackTrace();
		}
	}

    
}