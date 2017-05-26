import java.net.*;

public class GetHost
{
	public void main (String args[])
	{
		System.out.println("Searching");
		try
		{
			InetAddress localAddress = InetAddress.getLocalHost();
			 System.out.println("IP address := "+ localAddress.getHostAddress());

		}
		catch(Exception e)
		{
			System.out.println("Unknown host");
		}
		
	}

}