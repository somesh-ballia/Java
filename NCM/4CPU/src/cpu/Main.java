/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cpu;

/**
 *
 * @author Wavejumper
 */

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
  for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
    method.setAccessible(true);
    if (method.getName().startsWith("get")
        && Modifier.isPublic(method.getModifiers())) {
            Object value;
        try {
            value = method.invoke(operatingSystemMXBean);
        } catch (Exception e) {
            value = e;
        } // try
        System.out.println(method.getName() + " = " + value);
    } // if
  }


    }

}
