
package datasender;

import java.io.*;

public class Main {
    
   static long fileLength;
   static long regularLength;
   static long remaningLength;
   static long start1;
   static long end1;
   static long start2;
   static long end2;
   static long start3;
   static long end3;
   static long start4;
   static long end4;
   static long start5;
   static long end5;
   static long start6;
   static long end6;
   static String location;
   static int port1=8981;
   static int port2=8982;
   static int port3=8983;
   static int port4=8984;
   static int port5=8985;
   static int port6=8986;


    public static void main(String[] args) {
        
        location = "C:\\Project\\java\\DataSender\\src\\datasender\\audio.mp3";
        try
        {
            File file = new File(location);
            fileLength = file.length();
            remaningLength = fileLength%5;
            fileLength -= remaningLength;
            regularLength = fileLength/5;

            start1=0;
            end1=regularLength;
            start2=end1+1;
            end2=start2+regularLength;
            start3=end2+1;
            end3=start3+regularLength;
            start4=end3+1;
            end4= start4+regularLength;
            start5=end4+1;
            end5=start5+regularLength;
            start6=end5+1;
            end6=start6+remaningLength;
           // Thread.sleep(5000);
            //Send s1 = new Send("1",start1,end1,location,port1);
            Send s2 = new Send("2",start2,end2,location,port2);
         //   Send s3 = new Send("3",start3,end3,location,port3);
         //   Send s4 = new Send("4",start4,end4,location,port4);
         //   Send s5 = new Send("5",start5,end5,location,port5);
//         //   Send s6 = new Send("6",start6,end6,location,port6);

           // s1.T.join();
            s2.T.join();
         //   s3.T.join();
          //  s4.T.join();
          //  s5.T.join();
          //  s6.T.join();

            System.out.println("Data Transfer Complete");
        }
        catch(Exception e)
        {
            System.out.println("Sorry! Some error occoured in reading file1");
             e.printStackTrace();

        }

    }

}

