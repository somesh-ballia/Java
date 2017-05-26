
package datareciver;

import java.io.*;

public class Main {

    static String location;
    static String ipAddress;
    static int port1 = 8981;
    static int port2 = 8982;
    static int port3 = 8983;
    static int port4 = 8984;
    static int port5 = 8985;
    static int port6 = 8986;
   
    public static void main(String[] args) {

        location = "output.mp3";
        ipAddress= "172.16.1.3";
        try
        {
           // Recive r1 = new Recive("1",ipAddress,port1);
            Recive r2 = new Recive("2",ipAddress,port2);
          //  Recive r3 = new Recive("3",ipAddress,port3);
         //   Recive r4 = new Recive("4",ipAddress,port4);
          //  Recive r5 = new Recive("5",ipAddress,port5);
         //   Recive r6 = new Recive("6",ipAddress,port6);

         //   r1.T.join();
            r2.T.join();
        //    r3.T.join();
        //    r4.T.join();
        //    r5.T.join();
        //    r6.T.join();

            System.out.println("Data Recived, Combining entire data");
            File file1 = new File("TempData");
            File file2 = new File("TempData2");
            File file3 = new File("TempData3");
            File file4 = new File("TempData4");
            File file5 = new File("TempData5");
            File file6 = new File("TempData6");

            FileInputStream input1 = new FileInputStream(file1);
            FileInputStream input2 = new FileInputStream(file2);
            FileInputStream input3 = new FileInputStream(file3);
            FileInputStream input4 = new FileInputStream(file4);
            FileInputStream input5 = new FileInputStream(file5);
            FileInputStream input6 = new FileInputStream(file6);

            long length1 = file1.length();
            long length2 = file2.length();
            long length3 = file3.length();
            long length4 = file4.length();
            long length5 = file5.length();
            long length6 = file6.length();

            long TotalLength = length1+length2+length3+length4+length5+length6;

            byte data1 [] = new byte[(int)length1];
            byte data2 [] = new byte[(int)length2];
            byte data3 [] = new byte[(int)length3];
            byte data4 [] = new byte[(int)length4];
            byte data5 [] = new byte[(int)length5];
            byte data6 [] = new byte[(int)length6];

            byte Data[] = new byte [(int)TotalLength];

            input1.read(data1);
            input2.read(data2);
            input3.read(data3);
            input4.read(data4);
            input5.read(data5);
            input6.read(data6);

            for(long i=0;i<TotalLength;i++)
            {
                for(long j=0;j<length1;j++)
                {
                    Data[(int)i]=data1[(int)j];
                }
                for(long j=0;j<length2;j++)
                {
                    Data[(int)i]=data2[(int)j];
                }
                for(long j=0;j<length3;j++)
                {
                    Data[(int)i]=data3[(int)j];
                }
                for(long j=0;j<length4;j++)
                {
                    Data[(int)i]=data4[(int)j];
                }
                for(long j=0;j<length5;j++)
                {
                    Data[(int)i]=data5[(int)j];
                }
                for(long j=0;j<length6;j++)
                {
                    Data[(int)i]=data6[(int)j];
                }
            }

            FileOutputStream outData = new FileOutputStream(location);
            outData.write(Data);

            file1.delete();
            file2.delete();
            file3.delete();
            file4.delete();
            file5.delete();
            file6.delete();

            System.out.println("Task Completed");
        }
        catch(Exception e)
        {
             e.printStackTrace();
        }
    }

}
