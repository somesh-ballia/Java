class ExceptionHandel
{
	public static void main(String args[])
	{
		try
		{
			int i= Integer.parseInt(args[5]);

			try
			{
				int a=2;
				int b=0;
				int c=a/b;
				Thread.sleep(100);
				throw new IllegalAccessException("demo");
			
				
	
			}
			catch(ArithmeticException e)
			{
				System.out.println("ArithmeticException");
			}

			catch(InterruptedException e)
			{
				System.out.println("InterruptedException");
			}

			catch(IllegalAccessException e)
			{
				System.out.println("IllegalAccessException");
			}
				
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("ArrayIndexOutOfBoundsException");
		}
		
	}
	
}