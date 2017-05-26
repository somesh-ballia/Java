class pascle
{

	public static void main(String args[])
	{
		try
		{
			int i =Integer.parseInt(args[0]);
			int count = i;
			for(int j= 1; j<=i;j++)
			{	
				for(int x=0;x<count;x++)
				{	
					System.out.print(" ");
				}

				for(int y=1;y<=j;y++)
				{
					System.out.print(y);
				}
				for(int z=j-1;z>0;z--)
				{
					System.out.print(z);		
				}
				
				System.out.println();
				count--;

			}
		}	
		catch(Exception e)
		{
			System.out.println("Data not entered");
		}
		

	}

}

