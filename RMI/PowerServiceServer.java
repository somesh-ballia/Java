import java.math.*;
import java.rmi.*;
import java.rmi.server.*;

//
// PowerServiceServer
//
// Server for a RMI service that calculates powers
//
public class PowerServiceServer extends UnicastRemoteObject
implements PowerService
{
    public PowerServiceServer () throws RemoteException
    {
        super();
    }

    // Calculate the square of a number
    public BigInteger square ( int number ) 
    throws RemoteException
    {
        String numrep = String.valueOf(number);
        BigInteger bi = new BigInteger (numrep);
        
        // Square the number
        bi.multiply(bi);

        return (bi);
    }

    // Calculate the power of a number
    public BigInteger power ( int num1, int num2)
    throws RemoteException
    {
        String numrep = String.valueOf(num1);
        BigInteger bi = new BigInteger (numrep);

	bi = bi.pow(num2);
	return bi;
    }

    public static void main ( String args[] ) throws Exception
    {
        // Assign a security manager, in the event that dynamic
	// classes are loaded
        if (System.getSecurityManager() == null)
            System.setSecurityManager ( new RMISecurityManager() );

        // Create an instance of our power service server ...
        PowerServiceServer svr = new PowerServiceServer();

        // ... and bind it with the RMI Registry
        Naming.bind ("PowerService", svr);

        System.out.println ("Service bound....");
    }
}
