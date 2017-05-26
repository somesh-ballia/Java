import java.util.*;

class newThread implements Runnable
{

  Thread t;

  newThread()
	{
		t=new Thread(this);
		System.out.println("Child Thread " + t);
		t.start();	
	}

  public void run()
	{
		try{
			
			for(int i=0;i<5;i++)
			{
				System.out.println("Child thread " +i);
				t.sleep(500);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}
	}

}


class threadDemo
{
	public static void main(String args[])
	{
		new newThread();		

		try{
			for(int i=0;i<5;i++)
			{
				System.out.println("Main thread " + i);
				Thread.sleep(1000);
			}
		}
		catch(Exception e)
		{	
			System.out.println("Error in main");
		}

		System.out.println("Main Ends");

	}

}