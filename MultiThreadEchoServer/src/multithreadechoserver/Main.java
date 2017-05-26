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

				boolean done = false;
				while(!done /* && in.hasNextLine()*/)
				{
                                        
                                        out.println("Enter first number");
                                        String number1 = in.nextLine();

                                        if(number1.trim().equals("BYE"))
                                        {
						done = true;
                                        }
                                        else
                                        {
                                             out.println("Enter second number");
                                             String number2 = in.nextLine();

                                             if(number2.trim().equals("BYE"))
                                             {
						done = true;
                                             }
                                             else
                                             {
                                                 if(number1.length()>0 && number2.length()>0)
                                                     {
                                                          long num1 = Integer.parseInt(number1);
                                                          long num2 = Integer.parseInt(number2);

                                                          long sum = num1+num2;
                                                          out.println("\n The Sum of the Number is : " + sum);
                                                          done = false;
                                                      }
                                             }
                                        }

				}
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