/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fulltasklist;

/**
 *
 * @author Wavejumper
 */
import java.io.*;
import java.util.*;

public class Main {

     public static List<String> listRunningProcesses() {
    List<String> processes = new ArrayList<String>();
    try {
      String line;
      Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
      BufferedReader input = new BufferedReader
          (new InputStreamReader(p.getInputStream()));
      while ((line = input.readLine()) != null) {
          if (!line.trim().equals("")) {
              // keep only the process name
              line = line.substring(0);
              processes.add(line);
          }

      }
      input.close();
    }
    catch (Exception err) {
      err.printStackTrace();
    }
    return processes;
  }

  public static void main(String[] args){
      List<String> processes = listRunningProcesses();
      String result = "";

      // display the result
      Iterator<String> it = processes.iterator();
      int i = 0;
      while (it.hasNext()) {
         result += it.next() +"\n";
         i++;
         if (i==10) {
             result += "\n";
             i = 0;
         }
      }
      System.out.println("Running processes : \n" + result);
  }

  public static void msgBox(String msg) {
    javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
       null, msg, "WindowsUtils", javax.swing.JOptionPane.DEFAULT_OPTION);
  }

}
