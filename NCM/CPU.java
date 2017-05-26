import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CPU
{

	public static void main(String args[])
	{

	MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();

	OperatingSystemMXBean osMBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);

	long nanoBefore = System.nanoTime();
	long cpuBefore = osMBean.getProcessCpuTime();

// Call an expensive task, or sleep if you are monitoring a remote process

	long cpuAfter = osMBean.getProcessCpuTime();
	long nanoAfter = System.nanoTime();

	long percent;
	if (nanoAfter > nanoBefore)
		percent = ((cpuAfter-cpuBefore)*100L)/(nanoAfter-nanoBefore);
	else percent = 0;

	System.out.println("Cpu usage: "+percent+"%");

	}
}