class sort
{
	public static void main(String args[])
	{

		int num [] = { 9,8,7,6,5,4,3,2,1,0};
		int temp;
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<i;j++)
			{		
				if(num[i] < num[j])
				{
					temp = num[j];
					num[j] = num[i];
					num[i] = temp;
				}
			}
		}

		for(int i=0;i<10;i++)
		{

			System.out.println(num[i]);
		} 
	
	}

}