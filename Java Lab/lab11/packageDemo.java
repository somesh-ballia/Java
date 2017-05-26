import p1.*;
import p1.p2.*;

class packageDemo
{
	public static void main(String args[])
	{
		ClassP1 p1 = new ClassP1();
		ClassP2 p2 = new ClassP2();
		System.out.println("This is PACKAGE DEMO");
		p1.show();
		p2.show(); 
	}
}