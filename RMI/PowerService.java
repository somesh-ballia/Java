import java.math.BigInteger;
import java.rmi.*;

//
// PowerService Interface
//
// Interface for a RMI service that calculates powers
//
public interface PowerService extends java.rmi.Remote
{
	// Calculate the square of a number
	public BigInteger square ( int number )
		throws RemoteException;

	// Calculate the power of a number
	public BigInteger power  ( int num1, int num2) 
		throws RemoteException;
}
