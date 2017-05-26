class compute
{
	
  int l,b,h,r;  
	compute(int a)
	{
		// for circle
		r=a;	
	}
	compute()
	{
		//for square
		l = 5;
	}

	compute(int t,int j)
	{
		b=t;
		h=j;
	}
	
	compute(compute c)
	{
		//for triangle
		b=c.b;
		h=c.h;
	}

	public void area(int rad)
	{
		// for circle
		System.out.println("The area of circle is :- "+(3.14*(r+rad)*(r+rad))); 
	}
	
	public void area()
	{
		//for square
		System.out.println("The area of square is :- "+(l*l));
	}

	public void area(int y,int z)
	{
		b=b+y;
		h=h+z;
		System.out.println("The area of triangle is :- "+(.5)*((b+y)*(h+z)));		
	}
}


public class Area
{
	public static void main(String args[])
	{
		compute c1 = new compute();
		c1.area();

		compute c2 = new compute(3);
		c2.area(5);

		compute c3 = new compute(4,5);
		
		compute c4 = new compute(c3);
		c4.area(2,2);
	}
}