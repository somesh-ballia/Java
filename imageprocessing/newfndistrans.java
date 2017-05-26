//1:newfndistrans.java
//2:A bmp monochrome image(preferably 512-512)
//make the image from mspaint or any other source



import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class newfndistrans extends JFrame{
	frame frm=null;
	File f;
	FileInputStream ff;
	Color cl;
    int count;
    int  a[];

    int rnum,gnum;
   int xsize;
   int ysize;
  final int sample=5;
  String str=null;

newfndistrans(String str)
{
    super(str);
    frm=new frame();


    count=0;
    a=new int[8];
    rnum=0;gnum=0;
}

	 public void paint(Graphics g)
   {
   	int x,y,i=0,j=0,temp;

   	    try{

		System.out.println("Starting");
		System.out.println("filename="+frm.getfilename());
		try{f=frm.getfilename();
            ff=new FileInputStream(f);
           }catch(Exception e){
           	System.out.println("error in reading file");
           	}
     ff.skip(18);
     i=ff.read();
     i=((ff.read()<<8)|i);
     i=((ff.read()<<16)|i);
     i=((ff.read()<<24)|i);
     xsize=i;
     System.out.println("width="+xsize);
     i=0;
     i=ff.read();
     i=((ff.read()<<8)|i);
     i=((ff.read()<<16)|i);
     i=((ff.read()<<24)|i);
     ysize=i;
     System.out.println("Height="+ysize);

     ff.skip(38);//62-(2+16+4+4=26)=36, actually total=62 Bytes header
     //note:make it 38 for nrectbit1.bmp and nrectbit2.bmp
     x=0;y=ysize;
     int pix[][],mat1[][];
     pix=new int[xsize][ysize];
     mat1=new int[xsize][ysize];
     System.out.println("here");
   	while(true)
		{

			rnum=ff.read();
			//System.out.println("rnum="+rnum);
			if(rnum==-1)break;
			count=7;
	//image has 0 for black ,1 for white,but my convention is opposite
		while(rnum>0)
			{
				gnum=rnum%2;
				if(gnum==0)a[count]=1;
				 else a[count]=0;
				count--;
				rnum=rnum/2;
			}
			while(count>=0)
			{a[count]=1;count--;
			}
			for(i=0;i<=7;i++)
			{if(a[i]==0)cl=new Color(255,255,255);
			   else cl=new Color(0,0,0);
			  pix[x][y-1]=a[i];
			  mat1[x][y-1]=a[i];
			   g.setColor(cl);
				g.drawRect(x,y,1,1);
				x++;
				if(x==xsize){x=0;y--;}
			 }
			}//end while

System.out.println("File read successfully");
//sleep for 4 seconds(4000 msec)
try{Thread.sleep(4000);
}catch(Exception e){}
//clear the region where the image was drawn,area near the boundaries 
are
also cleaned
for(j=0;j<=ysize+2;j++)
for(i=0;i<=xsize+2;i++)
  g.clearRect(i,j,1,1);
//cleaning done above

//0 means white,1 means black
//perform the distance transform
for(j=0;j<ysize;j++)
for(i=0;i<xsize;i++)
 {
  if(mat1[i][j]==0)continue;
  if((j==0)||(j==ysize-1)){mat1[i][j]=1;continue;}
   if((i==0)||(i==xsize-1)){mat1[i][j]=1;continue;}


temp=findmin(mat1[i-1][j],mat1[i-1][j-1],mat1[i][j-1],mat1[i+1][j-1]);
      mat1[i][j]=temp+1;
 }



//perform the distance transform from bottom
for(j=ysize-1;j>=0;j--)
for(i=xsize-1;i>=0;i--)
 {if(mat1[i][j]==0)continue;
  if((j==0)||(j==ysize-1)){mat1[i][j]=1;continue;}
  if((i==0)||(i==xsize-1)){mat1[i][j]=1;continue;}


temp=findmin(mat1[i-1][j+1],mat1[i][j+1],mat1[i+1][j+1],mat1[i+1][j]);
      if(temp>(mat1[i][j]-1))temp=mat1[i][j]-1;
      mat1[i][j]=temp+1;
 }

int max=-9999,colornum,i1,j1,globmax=-9999;
x=0;y=0;
for(j=0;j<ysize-(ysize%sample);j+=sample)
for(i=0;i<xsize-(xsize%sample);i+=sample)
 {
 	max=-9999;
 	for(j1=j;j1<j+sample;j1++)
 	for(i1=i;i1<i+sample;i1++)
 	if(max<mat1[i1][j1])
 	{
 		max=mat1[i1][j1];
 		//xcord=i1;ycord=j1;
 	}
 	//out of both above for loops
 	if(globmax<max)globmax=max;


 	if(max!=0)
 	{
 	System.out.println("max="+max);
 	for(j1=j;j1<j+sample;j1++)
 	for(i1=i;i1<i+sample;i1++)
 		mat1[i1][j1]=max;
 	}

 }
System.out.println("globmax="+globmax);
 for(j=0;j<ysize;j++)
 for(i=0;i<xsize;i++)
  {colornum=(mat1[i][j]*255)/globmax;//contrast enhancement
   colornum=255-colornum;
   cl=new Color(colornum,colornum,colornum);
   g.setColor(cl);
   g.drawRect(i,j,1,1);
  }
ff.close();
System.out.println("program ends");
		}//end try block
	catch(Exception e)
		{System.out.println("Error"+e.getMessage());
		}



   	}
 int findmin(int num1,int num2,int num3,int num4)
 {
 	int min=4000;
 	if(min>num1)min=num1;
 	if(min>num2)min=num2;
 	if(min>num3)min=num3;
 	if(min>num4)min=num4;
 	return(min);
 }

	public static void main(String args[])
	{

		JFrame frm=new newfndistrans("Distance Transform");
		frm.setSize(700,700);
		frm.setVisible(true);


	}//end main()

}// end class fndistrans



class frame extends JFrame{
	JButton but;
	JFileChooser fch;
    String filename;
    File file=null;
	//Container con;
	boolean temp;
	frame()
	{ super("FileReading");
	  setSize(700,500);
	  setLayout(new FlowLayout());

	  temp=true;
	  but=new JButton("Open the binary file");
	  add(but);
	  fch=new JFileChooser();
	  but.addMouseListener(new MouseAdapter()
	  { public void mousePressed(MouseEvent me)
	  	{
	  		int retval=fch.showOpenDialog(but);
	  		if(retval==JFileChooser.APPROVE_OPTION)
	  		{
	  			file=fch.getSelectedFile();
	  			filename=file.getName();
	  			System.out.println("1filename="+filename);
	  			temp=false;
	  		}

	  	}

	  });
	  setVisible(true);
	while(temp);
	}//end constructor
File getfilename()
{System.out.println("filename="+filename);
	return file;
}

}
