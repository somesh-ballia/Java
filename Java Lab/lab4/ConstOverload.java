class test
{
	test()
	{
		System.out.println("This is the default constructor");
	}
	
	test(String Name)
	{
		System.out.println("This is the overloaded constructor. And my name is "+ Name);
	}	
}

class ConstOverload
{
	public static void main(String args[])		
	{
		test t1 = new test();
		test t2 = new test("INDIA");
	}

}