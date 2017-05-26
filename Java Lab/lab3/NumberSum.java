class NumberSum
{
	public static void main(String args[])
	{
	
		if(args[0].length() !=0 && args[1].length()!=0)
		{
			int num1= Integer.parseInt(args[0]);
			int num2= Integer.parseInt(args[1]);
			int sum = num1+num2;
			System.out.println("The sum is :"+sum);				
		}
		else
		{
			System.out.println("Data incomplete");
		}
	}
}