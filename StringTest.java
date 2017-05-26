class StringTest
{
	public static void main(String args[])
	{
		String s1 = "Hello this is java";
		StringBuffer s2 = new StringBuffer("Hello this is java");
		s2.replace(6,10,"to));
		s1.replace(6,10 , "to");
		System.out.println(s1);
		System.out.println(s2);
	}

}