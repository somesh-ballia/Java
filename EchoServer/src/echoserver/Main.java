/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package echoserver;

import java.io.*;
import java.net.*;
import java.util.*;
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

			ServerSocket s= new ServerSocket(818);
			Socket incoming = s.accept();
			try
			{
				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();

				Scanner in = new Scanner(inStream);
				PrintWriter out  = new PrintWriter(outStream,true);

				out.println("Hello this is Somesh server . Enter BYE to exit");

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
                                                          out.println(sum);
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

}




