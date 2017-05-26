import java.util.*;

public class Calendar
{
	public static void main(String [] args)
	{
		// construct d as current date

		GregorianCalendar d = new GregorianCalendar();
		int today = d.get(Calendar.DAY_OF_MONTH);
		int month = d.get(Calendar.MONTH);

		// set d to start date of the month
		d.set(Calendar.DAY_OF_MONTH,1);

		int weekday = d.get(Calendar.DAY_OF_WEEK);

		// print heading
		System.out.println(" Sun Mon Tue Wed Thu Fri Sat");
		System.out.println("   ");

		do
		{
			// print day
			int day = d.get(Calendar.DAY_OF_MONTH);
			if (day < 10) System.out.print(" ");
			System.out.print(day);

			// mark current day with *
			if (day == today)
				System.out.print("* ");
			else
				System.out.print(" ");

			// start a new line after every Saturday
			if(weekday == Calendar.SATURDAY)
				System.out.println();

			// advance d to the next day
			d.add(Calendar.DAY_OF_MONTH,1);
			weekday = d.get(Calendar.DAY_OF_WEEK);
		}
		while(d.get(Calendar.MONTH) == month);
		// the loop exits when d is day 1 of the next month

		// print final end of line if necessary
		if(weekday != Calendar.SUNDAY)
			System.out.println();
	}
}
