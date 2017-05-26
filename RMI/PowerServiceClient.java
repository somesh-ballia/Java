import java.rmi.*;
import java.rmi.Naming;
import java.io.*;

//
//
// PowerServiceClient
//
//
public class PowerServiceClient 
{
	public static void main(String args[]) throws Exception
	{
		// Check for hostname argument
		if (args.length != 1)
		{
			System.out.println
			("Syntax - PowerServiceClient host");
			System.exit(1);
		}

		// Assign security manager
		if (System.getSecurityManager() == null)
		{
			System.setSecurityManager
			(new RMISecurityManager());
		}

		// Call registry for PowerService
		PowerService service = (PowerService) Naming.lookup
			("rmi://" + args[0] + "/PowerService");

		DataInputStream din = new 
			DataInputStream (System.in);

		for (;;)
		{
			System.out.println 
			  ("1 - Calculate square");
			System.out.println 
			  ("2 - Calculate power");
			System.out.println 
			  ("3 - Exit"); System.out.println();
			System.out.print ("Choice : ");

			String line = din.readLine();
			Integer choice = new Integer(line);
			
			int value = choice.intValue();

			switch (value)
			{
			case 1:
			  System.out.print ("Number : ");
			  line = din.readLine();System.out.println();
			  choice = new Integer (line);
			  value  = choice.intValue();
				
			  // Call remote method
			  System.out.println 
			  ("Answer : " + service.square(value));
				
			  break;
			case 2:
			  System.out.print ("Number : ");
			  line = din.readLine();					
			  choice = new Integer (line);
			  value  = choice.intValue();
				
			  System.out.print ("Power  : ");
			  line = din.readLine();
			  choice = new Integer (line);
			  int power = choice.intValue();

			  // Call remote method
			  System.out.println 
		("Answer : " + service.power(value, power));

			  break;
			case 3:
			  System.exit(0);
			default :
			  System.out.println ("Invalid option");
			break;
			}
		}
	}

}
