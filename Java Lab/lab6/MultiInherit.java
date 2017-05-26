import java.util.*;

interface basic
{
	public int sum(int a,int b);
	public int sub(int a,int b);
}

interface advance
{
	public float div(int a,int b);
	public float mul(int a,int b);
}



class Arithmatic implements basic,advance
{
	public int sum(int a,int b)
	{
		return(a+b);
	}
	
	public int sub(int a,int b)
	{
		return(a-b);
	}

	public float div(int a,int b)
	{
		return(a/b);
	}
	
	public float mul(int a,int b)
	{
		return(a*b);
	}
}

public class MultiInherit extends Arithmatic
{
	public double sqr(int a)
	{
		return(a*a);
	}

	public static void main(String args[])
	{
		MultiInherit obj = new MultiInherit();
		obj.run();
	}	

	public void run()
	{
		System.out.println("The sum is :- "+ sum(2,2));
		System.out.println("The sub is :- "+ sub(2,2));
		System.out.println("The mul is :- "+ mul(2,2));
		System.out.println("The div is :- "+ div(2,2));	
		System.out.println("The sqr is :- "+ sqr(2));	
	}


} 