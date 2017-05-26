import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
class UserRam extends Frame implements ActionListener
{
	Frame f1;
	String arr[];
	TextArea ta;
	Panel pan;
	Button bn;
	public UserRam()
	{
		bn=new Button("OK");
		bn.addActionListener(this);
		pan=new Panel();
		pan.setLayout(new FlowLayout());
		f1=new Frame("User RAM Area");
		f1.setSize(400,400);
		arr=new String[200];
		ta=new TextArea(20,20);
		pan.add(ta);
		pan.add(bn);
		f1.add(pan);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==bn)
		{
			f1.dispose();
		}
	}
}

class RunPro extends MemArea
{
	int ct;
	public RunPro()
	{
		ct=ctn;
	}
	public int compil()
	{
		if(act[ctn-1]!="76")
			return 1;
		else
			return 0;
	}
}


class Proceed1
{
	int B_reg[]=new int[8];
	int C_reg[]=new int[8];
	int D_reg[]=new int[8];
	int E_reg[]=new int[8];
	int H_reg[]=new int[8];
	int L_reg[]=new int[8];
	int sph[]=new int[8];
	int spl[]=new int[8];
	int pch[]=new int[8];
	int pcl[]=new int[8];
	int tmpry[]=new int[8];
	int fg=0;
	String jo1,jo2,jo3,jo4,jo5,jo6,jo7,jo8,jo9,jo10;
	public Proceed1()
	{
		for(int cnt=0;cnt<8;cnt++)
		{
			B_reg[cnt]=0;
			C_reg[cnt]=0;
			D_reg[cnt]=0;
			E_reg[cnt]=0;
			H_reg[cnt]=0;
			L_reg[cnt]=0;
			sph[cnt]=0;
			spl[cnt]=0;
			pch[cnt]=0;
			pcl[cnt]=0;
		}

	}
	public void XCHG()
	{
		int tmp[]=new int[8];
		for(int cnt=0;cnt<8;cnt++)
		{
			tmp[cnt]=H_reg[cnt];
			H_reg[cnt]=D_reg[cnt];
			D_reg[cnt]=tmp[cnt];
		}
		for(int cnt=0;cnt<8;cnt++)
		{
			tmp[cnt]=L_reg[cnt];
			L_reg[cnt]=E_reg[cnt];
			E_reg[cnt]=tmp[cnt];
		}
	}
	public void PreCon()
	{
		jo1=ReConvert(2);
		jo2=ReConvert(3);
		jo3=ReConvert(4);
		jo4=ReConvert(5);
		jo5=ReConvert(6);
		jo6=ReConvert(7);
		jo7=ReConvert(8);
		jo8=ReConvert(9);
		jo9=ReConvert(10);
		jo10=ReConvert(11);
	}
	public int Pchl()
	{
		for(int cnt=0;cnt<8;cnt++)
			pcl[cnt]=L_reg[cnt];
		for(int cnt=0;cnt<8;cnt++)
			pch[cnt]=H_reg[cnt];
		int o=calci();
		return o;
	}
	public int calci()
	{
		int k=0;int tmpo=0;
		for(int cnt=0;cnt<8;cnt++)
		{
			k=(int)Math.pow(2,(7-cnt));
			tmpo=tmpo+(pcl[cnt]*k);
		}
		for(int cnt=0;cnt<8;cnt++)
		{
			k=(int)Math.pow(2,(8+cnt));
			tmpo=tmpo+(pch[cnt]*k);
		}
		return tmpo;
	}
	public void Convrt(String t,int n)
	{
		if(t.length()==1)
			t=0+t;
		char a[];
		a=t.toCharArray();
		if(a[1]=='0')
		{
			tmpry[7]=0;tmpry[6]=0;tmpry[5]=0;tmpry[4]=0;
		}
		if(a[1]=='1')
		{
			tmpry[7]=1;tmpry[6]=0;tmpry[5]=0;tmpry[4]=0;
		}
		if(a[1]=='2')
		{
			tmpry[7]=0;tmpry[6]=1;tmpry[5]=0;tmpry[4]=0;
		}
		if(a[1]=='3')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=0;tmpry[4]=0;
		}
		if(a[1]=='4')
		{
			tmpry[7]=0;tmpry[6]=0;tmpry[5]=1;tmpry[4]=0;
		}
		if(a[1]=='5')
		{
			tmpry[7]=1;tmpry[6]=0;tmpry[5]=1;tmpry[4]=0;
		}
		if(a[1]=='6')
		{
			tmpry[7]=0;tmpry[6]=1;tmpry[5]=1;tmpry[4]=0;
		}
		if(a[1]=='7')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=1;tmpry[4]=0;
		}
		if(a[1]=='8')
		{
			tmpry[7]=0;tmpry[6]=0;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='9')
		{
			tmpry[7]=1;tmpry[6]=0;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='A')
		{
			tmpry[7]=0;tmpry[6]=1;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='B')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='C')
		{
			tmpry[7]=0;tmpry[6]=0;tmpry[5]=1;tmpry[4]=1;
		}
		if(a[1]=='D')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='E')
		{
			tmpry[7]=0;tmpry[6]=1;tmpry[5]=1;tmpry[4]=1;
		}
		if(a[1]=='F')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=1;tmpry[4]=1;
		}
		if(a[0]=='0')
		{
			tmpry[3]=0;tmpry[2]=0;tmpry[1]=0;tmpry[0]=0;
		}
		if(a[0]=='1')
		{
			tmpry[3]=1;tmpry[2]=0;tmpry[1]=0;tmpry[0]=0;
		}
		if(a[0]=='2')
		{
			tmpry[3]=0;tmpry[2]=1;tmpry[1]=0;tmpry[0]=0;
		}
		if(a[0]=='3')
		{
			tmpry[3]=1;tmpry[2]=1;tmpry[1]=0;tmpry[0]=0;
		}
		if(a[0]=='4')
		{
			tmpry[3]=0;tmpry[2]=0;tmpry[1]=1;tmpry[0]=0;
		}
		if(a[0]=='5')
		{
			tmpry[3]=1;tmpry[2]=0;tmpry[1]=1;tmpry[0]=0;
		}
		if(a[0]=='6')
		{
			tmpry[3]=0;tmpry[2]=1;tmpry[1]=1;tmpry[0]=0;
		}
		if(a[0]=='7')
		{
			tmpry[3]=1;tmpry[2]=1;tmpry[1]=1;tmpry[0]=0;
		}
		if(a[0]=='8')
		{
			tmpry[3]=0;tmpry[2]=0;tmpry[1]=0;tmpry[0]=1;
		}
		if(a[0]=='9')
		{
			tmpry[3]=1;tmpry[2]=0;tmpry[1]=0;tmpry[0]=1;
		}
		if(a[0]=='A')
		{
			tmpry[3]=0;tmpry[2]=1;tmpry[1]=0;tmpry[0]=1;
		}
		if(a[0]=='B')
		{
			tmpry[3]=1;tmpry[2]=1;tmpry[1]=0;tmpry[0]=1;
		}
		if(a[0]=='C')
		{
			tmpry[3]=0;tmpry[2]=0;tmpry[1]=1;tmpry[0]=1;
		}
		if(a[0]=='D')
		{
			tmpry[3]=1;tmpry[2]=0;tmpry[1]=1;tmpry[0]=1;
		}
		if(a[0]=='E')
		{
			tmpry[3]=0;tmpry[2]=1;tmpry[1]=1;tmpry[0]=1;
		}
		if(a[0]=='F')
		{
			tmpry[3]=1;tmpry[2]=1;tmpry[1]=1;tmpry[0]=1;
		}
		for(int cnt=0;cnt<8;cnt++)
		{
			if(n==0)
				pch[cnt]=tmpry[cnt];
			if(n==1)
				pcl[cnt]=tmpry[cnt];
			if(n==2)
				sph[cnt]=tmpry[cnt];
			if(n==3)
				spl[cnt]=tmpry[cnt];
			if(n==4)
				B_reg[cnt]=tmpry[cnt];
			if(n==5)
				C_reg[cnt]=tmpry[cnt];
			if(n==6)
				D_reg[cnt]=tmpry[cnt];
			if(n==7)
				E_reg[cnt]=tmpry[cnt];
			if(n==8)
				H_reg[cnt]=tmpry[cnt];
			if(n==9)
				L_reg[cnt]=tmpry[cnt];
		}
	}
	public String ReConvert(int l)
	{
		int tmpo=0;
		int tmpo1=0;
		int k=0;
		for(int cnt=4;cnt<8;cnt++)
		{
			k=(int)Math.pow(2,(7-cnt));
			if(l==2)
				tmpo=tmpo+(B_reg[cnt]*k);
			if(l==3)
				tmpo=tmpo+(C_reg[cnt]*k);
			if(l==4)
				tmpo=tmpo+(D_reg[cnt]*k);
			if(l==5)
				tmpo=tmpo+(E_reg[cnt]*k);
			if(l==6)
				tmpo=tmpo+(H_reg[cnt]*k);
			if(l==7)
				tmpo=tmpo+(L_reg[cnt]*k);
			if(l==8)
				tmpo=tmpo+(sph[cnt]*k);
			if(l==9)
				tmpo=tmpo+(spl[cnt]*k);
			if(l==10)
				tmpo=tmpo+(pch[cnt]*k);
			if(l==11)
				tmpo=tmpo+(pcl[cnt]*k);
		}
		for(int cnt=0;cnt<4;cnt++)
		{
			k=(int)Math.pow(2,3-cnt);
			if(l==2)
				tmpo1=tmpo1+(B_reg[cnt]*k);
			if(l==3)
				tmpo1=tmpo1+(C_reg[cnt]*k);
			if(l==4)
				tmpo1=tmpo1+(D_reg[cnt]*k);
			if(l==5)
				tmpo1=tmpo1+(E_reg[cnt]*k);
			if(l==6)
					tmpo1=tmpo1+(H_reg[cnt]*k);
			if(l==7)
				tmpo1=tmpo1+(L_reg[cnt]*k);
			if(l==8)
				tmpo1=tmpo1+(sph[cnt]*k);
			if(l==9)
				tmpo1=tmpo1+(spl[cnt]*k);
			if(l==10)
				tmpo1=tmpo1+(pch[cnt]*k);
			if(l==11)
				tmpo1=tmpo1+(pcl[cnt]*k);
		}
		String b1,b2;
		b1=" ";
		b2=" ";
		if(tmpo<=9)
			b1=String.valueOf(tmpo);
		if(tmpo1<=9)
			b2=String.valueOf(tmpo1);
		if(tmpo==10)
			b1="A";
		if(tmpo==11)
			b1="B";
		if(tmpo==12)
			b1="C";
		if(tmpo==13)
			b1="D";
		if(tmpo==14)
			b1="E";
		if(tmpo==15)
			b1="F";
		if(tmpo1==10)
			b2="A";
		if(tmpo1==11)
			b2="B";
		if(tmpo1==12)
			b2="C";
		if(tmpo1==13)
			b2="D";
		if(tmpo1==14)
			b2="E";
		if(tmpo1==15)
			b2="F";
		System.out.println(b2+b1);
		return b2+b1;
	}
}
class Proceed  extends RunPro
{

	String op1[],op2[],op3[];
	String o2[]=new String[50];
	String o3[]=new String[50];
	String tobe[]=new String[8];
	int q=0;
	int l=0;
	int yu,yt;
	static int acc[]=new int[8];
	String accl;
	int yp=0;
	static int z1=0;
	int j=0,flk;
	static int tmpry[]=new int[8];;
	String y1[]=new String[100];
	SetFlag ag=new SetFlag();
	public Proceed()
	{
		yu=0;yt=0;

tmpry[0]=0;tmpry[1]=0;tmpry[2]=0;tmpry[3]=0;tmpry[4]=0;tmpry[5]=0;tmpry[6]=0;tmpry[7]=0;
		
acc[0]=0;acc[1]=0;acc[2]=0;acc[3]=0;acc[4]=0;acc[5]=0;acc[6]=0;acc[7]=0;
		accl="00";
		String
op1[]={"88","89","8A","8B","8C","8D","8E","8F","80","81","82","83","84","85",
"86","87","A0","A1","A2","A3","A4","A5","A6","A7","2F","3F","B8","B9","
BA","BB","BC","BD","BE","BF","27","09","19","29","39","05","0D","15","1D",
"25","2D","35","3D","0B","1B","2B","3B","F3","FB","76","DB","04","0C","14"
,"1C","24","2C","34","3C","03","13","23","33","0A","1A","40","41","42","43
","44","45","46","47","48","49","4A","4B","4C","4D","4E","4F","50","51","5
2","53","54","55","56","57","58","59","5A","5B","5C","5D","5E","5F","60","
61","62","63","64","65","66","67","68","69","6A","6B","6C","6D","6E","6F",
"70","71","72","73","74","75","77","78","79","7A","7B","7C","7D","7E","7F"
,"00","B0","B1","B2","B3","B4","B5","B6","B7","E9","C1","D1","E1","F1","C5
","D5","E5","F5","17","1F","07","0F","C9","D8","D0","F0","F8","E8","E0","C
8","C0","20","98","99","9A","9B","9C","9D","9E","9F","30","F9","02","12","
37","90","91","92","93","94","95","96","97","EB","A8","A9","AA","AB","AC",
"AD","AE","AF","E3"};
		String
op2[]={"CE","C6","E6","FE","DB","06","0E","16","1E","26","2E","36","3E","F
6","D3","DE","D6","EE"};
		String
op3[]={"CD","DC","D4","F4","FC","EC","E4","CC","C4","C3","DA","D2","F2","F
A","EA","E2","CA","C2","3A","2A","01","11","21","31","22","32"};
		while(q<ct)
		{
			for(int w=0;w<op1.length;w++)
			{
				if(act[q]==op1[w])
				{
					l=1;
					y1[z1]=act[q];
					z1++;
				}
			}
			for(int w=0;w<op2.length;w++)
			{
				if(act[q]==op2[w])
				{
					l=2;
					y1[z1]=act[q];
					z1++;
					o2[yu]=act[q+1];
					yu++;
				}
			}
			for(int w=0;w<op3.length;w++)
			{
				if(act[q]==op3[w])
				{
					l=3;
					y1[z1]=act[q];
					z1++;
					o3[yt]=act[q+1]+act[q+2];
					yt++;
				}
			}

			if(l==1)
				q++;
			if(l==2)
				q+=2;
			if(l==3)
				q+=3;
		}
		Temp();
	}
	public void Temp()
	{
		j=0;int gh=0;
		Proceed1 ty=new Proceed1();
		while(y1[j]!="76")
		{
			//Check for instruction
			if(y1[j]=="37")
				ag.SetF("37");
			if(y1[j]=="3F")
				ag.SetF("3F");
			if(y1[j]=="CE")
			{
				Convert(o2[gh]);
				gh++;
				addc();
				ReConvert();
			}
			if(y1[j]=="C6")
			{
				Convert(o2[gh]);
				gh++;
				addc();
				ReConvert();
			}
			if(y1[j]=="E6")
			{
				Convert(o2[gh]);
				gh++;
				anda();
				ReConvert();
			}
			if(y1[j]=="2F")
			{
				Convert(accl);
				gh++;
				Cmpt();
				ReConvert();
			}
			if(y1[j]=="F6")
			{
				Convert(o2[gh]);
				gh++;
				Ori();
				ReConvert();
			}
			if(y1[j]=="DE")
			{
				Convert(o2[gh]);
				gh++;
				Sbi();
				ReConvert();
			}
			if(y1[j]=="D6")
			{
				Convert(o2[gh]);
				gh++;
				Sbi();
				ReConvert();
			}
			if(y1[j]=="EE")
			{
				Convert(o2[gh]);
				gh++;
				Xri();
				ReConvert();
			}
			if(y1[j]=="FE")
			{
				Convert(o2[gh]);
				gh++;
				Cpi();
			}
			if(y1[j]=="17")
			{
				Ral();
				ReConvert();
			}
			if(y1[j]=="1F")
			{
				Rar();
				ReConvert();
			}
			if(y1[j]=="07")
			{
				Ral();
				ReConvert();
			}
			if(y1[j]=="0F")
			{
				Rar();
				ReConvert();
			}
			if(y1[j]=="EB")
			{
				ty.XCHG();
				ty.PreCon();
			}
			if(y1[j]=="E9")
			{
				int p=ty.Pchl();
				//System.out.println("p================"+p);
				ty.PreCon();
				j=p;
				j--;
			}
			j++;
		}

		ty.Convrt(String.valueOf(q),1);
		ty.PreCon();
		tobe[0]=ty.jo9+ty.jo10;
		tobe[1]=ty.jo7+ty.jo8;
		tobe[2]=ty.jo1;
		tobe[3]=ty.jo2;
		tobe[4]=ty.jo3;
		tobe[5]=ty.jo4;
		tobe[6]=ty.jo5;
		tobe[7]=ty.jo6;
	}
	public void Rar()
	{
		int ct;
		int tmp[]=new int[8];
		if(y1[j]=="1F")
			tmp[0]=ag.fl_cy;
		else
			tmp[0]=acc[7];
		if(acc[7]==0)
			ag.SetF("37o");
		else
			ag.SetF("37");
		for(int cnt=1;cnt<8;cnt++)
			tmp[cnt]=acc[cnt-1];
		for(int cnt=0;cnt<8;cnt++)
			acc[cnt]=tmp[cnt];
	}
	public void Ral()
	{
		int ct;
		int tmp[]=new int[8];
		if(y1[j]=="17")
			tmp[7]=ag.fl_cy;
		else
			tmp[7]=acc[0];
		for(int cnt=0;cnt<7;cnt++)
			tmp[6-cnt]=acc[7-cnt];
		ct=acc[0];
		if(ct==1)
			ag.SetF("37");
		else
			ag.SetF("37o");
		for(int cnt=0;cnt<8;cnt++)
			acc[cnt]=tmp[cnt];
	}
	public void Cpi()
	{
		int ghi[]=new int[8];
		for(int cnt=0;cnt<8;cnt++)
			ghi[cnt]=acc[cnt];
		Sbi();
		int ct=0;
		for(int cnt=0;cnt<8;cnt++)
		{
			if(acc[cnt]==0)
				ct++;
		}
		if(ct%2==0)
			ag.SetF("Pe");
		else
			ag.SetF("Po");
		ReConvert();
		if((accl.charAt(0)=='0')&&(accl.charAt(1)=='0'))
		{
			ag.SetF("zon");
			ag.SetF("37o");
		}
		else
		{
			if(ag.fl_cy==1)
			{
				ag.SetF("37");
				ag.SetF("zoff");
			}
			else
			{
				ag.SetF("zoff");
				ag.SetF("37o");
			}
		}
		for(int cnt=0;cnt<8;cnt++)
			acc[cnt]=ghi[cnt];
		ReConvert();
	}
	public void Xri()
	{
		int ct=0;
		for(int cnt=0;cnt<8;cnt++)
		{
			acc[cnt]=acc[cnt]+tmpry[cnt];
			if((acc[cnt]==2)||(acc[cnt]==0))
				acc[cnt]=0;
			if(acc[cnt]==0)
				ct++;
		}
		if(ct==8)
			ag.SetF("zon");
		else
			ag.SetF("zoff");
		if(ct%2==0)
			ag.SetF("Pe");
		else
			ag.SetF("Po");
		if(acc[0]==0)
			ag.SetF("son");
		else
			ag.SetF("soff");
		ag.SetF("37o");
		ag.SetF("acoff");
	}
	public void Sbi()
	{
		int cty=1;
		int ht=3;
		int temp[]=new int[8];
		System.out.println(ag.fl_cy);
		if((y1[j]=="DE")||(y1[j]=="FE"))
		{
			if(ag.fl_cy==1)
			{
				cty=1;
				for(int cnt=0;cnt<8;cnt++)
				{
					tmpry[7-cnt]=tmpry[7-cnt]+cty;
					cty=0;
					if(tmpry[7-cnt]==2)
					{
						tmpry[7-cnt]=0;
						cty=1;
					}
					if(tmpry[7-cnt]==3)
					{
						tmpry[7-cnt]=1;
						cty=1;
					}
				}
			}
		}
		for(int cnt=0;cnt<8;cnt++)
		{
			System.out.print(tmpry[cnt]);
			if(tmpry[cnt]==0)
				temp[cnt]=1;
			if(tmpry[cnt]==1)
				temp[cnt]=0;
		}
		System.out.println();
		for(int cnt=0;cnt<8;cnt++)
			tmpry[cnt]=temp[cnt];
		for(int cnt=0;cnt<8;cnt++)
		{
			tmpry[7-cnt]=tmpry[7-cnt]+cty;
			cty=0;
			if(tmpry[7-cnt]==2)
			{
				tmpry[7-cnt]=0;
				cty=1;
			}
			if(tmpry[7-cnt]==3)
			{
				tmpry[7-cnt]=1;
				cty=1;
			}
		}
		if(ag.fl_cy==1)
		{
			cty=1;
			for(int cnt=0;cnt<8;cnt++)
			{
				tmpry[7-cnt]=tmpry[7-cnt]+cty;
				cty=0;
				if(tmpry[7-cnt]==2)
				{
					tmpry[7-cnt]=0;
					cty=1;
				}
				if(tmpry[7-cnt]==3)
				{
					tmpry[7-cnt]=1;
					cty=1;
				}
			}
		}
		int cn=0;
		for(int cnt=0;cnt<8;cnt++)
		{
			if(acc[cnt]==0)
				cn++;
		}
		ag.SetF("37o");
		addc();
		if(ag.fl_cy==0)
			ht=1;
		if(ag.fl_cy==1)
			ht=0;
		if(ht==1)
			ag.SetF("37");
		if(ht==0)
			ag.SetF("37o");
		if(y1[j]=="DE")
			ag.SetF("acon");
		if(cn==8)
			ag.SetF("zon");
		else
			ag.SetF("zoff");
		if(cn%2==0)
			ag.SetF("Po");
		else
			ag.SetF("Pe");
		if(acc[0]==1)
			ag.SetF("son");
		else
			ag.SetF("soff");
	}
	public void Ori()
	{
		for(int cnt=0;cnt<8;cnt++)
		{
			acc[cnt]=acc[cnt]+tmpry[cnt];
			if(acc[cnt]>0)
				acc[cnt]=1;
			if(acc[cnt]==0)
				ctn++;
		}
		if(ctn==8)
			ag.SetF("zon");
		else
			ag.SetF("zoff");
		if(ctn%2==0)
			ag.SetF("Po");
		else
			ag.SetF("Pe");
		ag.SetF("acoff");
		ag.SetF("37o");
	}
	public void Cmpt()
	{
		int yup=0;
		for(int cnt=0;cnt<8;cnt++)
		{
			if(tmpry[cnt]==0)
				acc[cnt]=1;
			if(tmpry[cnt]==1)
				acc[cnt]=0;
		}
	}
	public void anda()
	{
		int ctn=0;int ctn1=0;
		for(int cnt=0;cnt<8;cnt++)
		{
			acc[cnt]=acc[cnt]+tmpry[cnt];
			if(acc[cnt]==1)
				acc[cnt]=0;
			if(acc[cnt]==2)
				acc[cnt]=1;
			if(acc[cnt]==0)
				ctn++;
			if(acc[cnt]==0)
				ctn1++;
		}
			if(ctn==8)
				ag.SetF("zon");
			else
				ag.SetF("zoff");
			if(ctn1%2==0)
				ag.SetF("Po");
			else
				ag.SetF("Pe");
			ag.SetF("acon");
			ag.SetF("37o");
	}
	public void ReConvert()
	{
		int tmpo=0;
		int tmpo1=0;
		int k=0;
		flk=ag.Getcy();
		for(int cnt=4;cnt<8;cnt++)
		{
			k=(int)Math.pow(2,(7-cnt));
			tmpo=tmpo+(acc[cnt]*k);
		}
		for(int cnt=0;cnt<4;cnt++)
		{
			k=(int)Math.pow(2,3-cnt);
			tmpo1=tmpo1+(acc[cnt]*k);
		}
		String b1,b2;
		b1=" ";
		b2=" ";
		if(tmpo<=9)
			b1=String.valueOf(tmpo);
		if(tmpo1<=9)
			b2=String.valueOf(tmpo1);
		if(tmpo==10)
			b1="A";
		if(tmpo==11)
			b1="B";
		if(tmpo==12)
			b1="C";
		if(tmpo==13)
			b1="D";
		if(tmpo==14)
			b1="E";
		if(tmpo==15)
			b1="F";
		if(tmpo1==10)
			b2="A";
		if(tmpo1==11)
			b2="B";
		if(tmpo1==12)
			b2="C";
		if(tmpo1==13)
			b2="D";
		if(tmpo1==14)
			b2="E";
		if(tmpo1==15)
			b2="F";
		accl=b2+b1;
	}
	public void addc()
	{
		int cyt=0;
		int ghl=0;
		if((y1[j]=="C6")||(y1[j]=="DE"))
			flk=0;
		int lk=0;
		for(int mn=0;mn<8;mn++)
		{
			acc[7-mn]=acc[7-mn]+tmpry[7-mn]+cyt+flk+lk;
			flk=0;
			cyt=0;
			lk=0;
			if(acc[7-mn]==2)
			{
				acc[7-mn]=0;
				cyt=1;
			}
			if(acc[7-mn]==3)
			{
				acc[7-mn]=1;
				cyt=1;
			}
			if(acc[7-mn]==4)
			{
				acc[7-mn]=0;
				cyt=0;
				lk=1;
			}
			if(mn==4)
			{
				if(acc[4]+tmpry[4]+cyt==3)
					ghl=1;
				else
					ghl=0;
			}
		}
		int yup=0;
		for(int mn=0;mn<8;mn++)
		{
			if(acc[mn]==0)
				yup++;
		}
		if(acc[0]==1)
			ag.SetF("son");
		if(acc[0]==0)
			ag.SetF("soff");
		if(yup==8)
			ag.SetF("zon");
		else
			ag.SetF("zoff");
		if(ghl==1)
			ag.SetF("acon");
		else
			ag.SetF("agoff");
		if(yup%2==0)
			ag.SetF("Pe");
		else
			ag.SetF("Po");
		if(cyt==1)
			ag.SetF("37");
	}
	public void Convert(String h)
	{
		char a[];
		a=h.toCharArray();
		if(a[1]=='0')
		{
			tmpry[7]=0;tmpry[6]=0;tmpry[5]=0;tmpry[4]=0;
		}
		if(a[1]=='1')
		{
			tmpry[7]=1;tmpry[6]=0;tmpry[5]=0;tmpry[4]=0;
		}
		if(a[1]=='2')
		{
			tmpry[7]=0;tmpry[6]=1;tmpry[5]=0;tmpry[4]=0;
		}
		if(a[1]=='3')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=0;tmpry[4]=0;
		}
		if(a[1]=='4')
		{
			tmpry[7]=0;tmpry[6]=0;tmpry[5]=1;tmpry[4]=0;
		}
		if(a[1]=='5')
		{
			tmpry[7]=1;tmpry[6]=0;tmpry[5]=1;tmpry[4]=0;
		}
		if(a[1]=='6')
		{
			tmpry[7]=0;tmpry[6]=1;tmpry[5]=1;tmpry[4]=0;
		}
		if(a[1]=='7')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=1;tmpry[4]=0;
		}
		if(a[1]=='8')
		{
			tmpry[7]=0;tmpry[6]=0;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='9')
		{
			tmpry[7]=1;tmpry[6]=0;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='A')
		{
			tmpry[7]=0;tmpry[6]=1;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='B')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='C')
		{
			tmpry[7]=0;tmpry[6]=0;tmpry[5]=1;tmpry[4]=1;
		}
		if(a[1]=='D')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=0;tmpry[4]=1;
		}
		if(a[1]=='E')
		{
			tmpry[7]=0;tmpry[6]=1;tmpry[5]=1;tmpry[4]=1;
		}
		if(a[1]=='F')
		{
			tmpry[7]=1;tmpry[6]=1;tmpry[5]=1;tmpry[4]=1;
		}
		if(a[0]=='0')
		{
			tmpry[3]=0;tmpry[2]=0;tmpry[1]=0;tmpry[0]=0;
		}
		if(a[0]=='1')
		{
			tmpry[3]=1;tmpry[2]=0;tmpry[1]=0;tmpry[0]=0;
		}
		if(a[0]=='2')
		{
			tmpry[3]=0;tmpry[2]=1;tmpry[1]=0;tmpry[0]=0;
		}
		if(a[0]=='3')
		{
			tmpry[3]=1;tmpry[2]=1;tmpry[1]=0;tmpry[0]=0;
		}
		if(a[0]=='4')
		{
			tmpry[3]=0;tmpry[2]=0;tmpry[1]=1;tmpry[0]=0;
		}
		if(a[0]=='5')
		{
			tmpry[3]=1;tmpry[2]=0;tmpry[1]=1;tmpry[0]=0;
		}
		if(a[0]=='6')
		{
			tmpry[3]=0;tmpry[2]=1;tmpry[1]=1;tmpry[0]=0;
		}
		if(a[0]=='7')
		{
			tmpry[3]=1;tmpry[2]=1;tmpry[1]=1;tmpry[0]=0;
		}
		if(a[0]=='8')
		{
			tmpry[3]=0;tmpry[2]=0;tmpry[1]=0;tmpry[0]=1;
		}
		if(a[0]=='9')
		{
			tmpry[3]=1;tmpry[2]=0;tmpry[1]=0;tmpry[0]=1;
		}
		if(a[0]=='A')
		{
			tmpry[3]=0;tmpry[2]=1;tmpry[1]=0;tmpry[0]=1;
		}
		if(a[0]=='B')
		{
			tmpry[3]=1;tmpry[2]=1;tmpry[1]=0;tmpry[0]=1;
		}
		if(a[0]=='C')
		{
			tmpry[3]=0;tmpry[2]=0;tmpry[1]=1;tmpry[0]=1;
		}
		if(a[0]=='D')
		{
			tmpry[3]=1;tmpry[2]=0;tmpry[1]=1;tmpry[0]=1;
		}
		if(a[0]=='E')
		{
			tmpry[3]=0;tmpry[2]=1;tmpry[1]=1;tmpry[0]=1;
		}
		if(a[0]=='F')
		{
			tmpry[3]=1;tmpry[2]=1;tmpry[1]=1;tmpry[0]=1;
		}
	}
}
class SetFlag extends FlagsWindow
{
	static int fl_s,fl_cy,fl_ac,fl_p,fl_z;
	public SetFlag()
	{
	}
	public void SetF(String n)
	{
		if(n=="37")
			fl_cy=1;
		if(n=="37o")
			fl_cy=0;
		if(n=="3F")
		{
			if(fl_cy!=1)
				fl_cy=1;
			else if(fl_cy==1)
				fl_cy=0;
		}
		if(n=="Pe")
			fl_p=1;
		if(n=="Po")
			fl_p=0;
		if(n=="acon")
			fl_ac=1;
		if(n=="acoff")
			fl_ac=0;
		if(n=="zon")
			fl_z=1;
		if(n=="zoff")
			fl_z=0;
		if(n=="son")
			fl_s=1;
		if(n=="soff")
			fl_s=0;
		SetFir();
	}
	public int Getcy()
	{
		return fl_cy;
	}
	public void SetFir()
	{
		txf[0].setText(String.valueOf(fl_s));
		txf[1].setText(String.valueOf(fl_z));
		txf[3].setText(String.valueOf(fl_ac));
		txf[5].setText(String.valueOf(fl_p));
		txf[7].setText(String.valueOf(fl_cy));
	}
}
class RunErrors extends Frame implements ActionListener
{
	Frame frm=new Frame("Error");
	Panel pan=new Panel();
	Panel pn;
	Label lab=new Label("Compile Error...Use HLT to terminate program");
	Button btn=new Button("OK");
	public RunErrors()
	{
		pan.setLayout(new BorderLayout());
		pn=new Panel();
		frm.setFont(new Font("Times Roman",Font.BOLD,14));
		frm.setBackground(Color.GREEN);
		frm.setSize(375,150);
		pan.add(lab,BorderLayout.CENTER);
		btn.addActionListener(this);
		pn.add(btn);
		pan.add(pn,BorderLayout.SOUTH);
		frm.add(pan);
		frm.show();
	}
	public void actionPerformed(ActionEvent ae)
	{
		frm.dispose();
	}
}
class MemArea extends Frame
{
	static int y=3;static int z=0;
	Frame mem;
	static String []act=new String[150];
	static int ctn=0;
	Panel frm;
	TextField mmr[];
	public MemArea()
	{
		mem=new Frame("Memory Window");
		mem.setFont(new Font("Monotype Corsiva",Font.BOLD,12));
		mem.setBackground(Color.gray);
		mem.setForeground(Color.blue);
		frm=new Panel();
		mem.setSize(180,600);
		frm.setLayout(new GridLayout(30,2));
		mmr=new TextField[60];
		for(int cnt=0;cnt<60;cnt++)
		{
				mmr[cnt]=new TextField(4);
				mmr[cnt].setEditable(false);
				frm.add(mmr[cnt]);
		}
		mmr[0].setText("Mem. Addr.");
		mmr[1].setText("Code");
		mem.add(frm);
	}
	public void SetCode(String n)
	{
		mem.setForeground(Color.RED);
		act[ctn]=n;
		ctn++;
		mmr[y-1].setText(String.valueOf(z));
		mmr[y].setText(n);
		y=y+2;
		z++;
	}
	public void Show()
	{
		mem.show();
	}
}
class InstArea extends Frame implements ActionListener,WindowListener
{
	MenuBar mbar=new MenuBar();
	Menu fil=new Menu("File");
	Menu edt=new Menu("Edit");
	Menu tools=new Menu("Tools");
	MenuItem nw=new MenuItem("New");
	MenuItem op=new MenuItem("Open");
	MenuItem cl=new MenuItem("Close");
	Frame ins_frm;
	Panel p2,p3;
	Button cls=new Button("CLOSE");
	Label lb=new Label("Intstruction Area");
	TextArea ta;
	static int inr=0;;
	String lbs[]={"INSTR. NO.","MEM. ADDR.","LABEL","OPCODE","OPERANDS"};
	public InstArea()
	{
		ins_frm=new Frame("Instruction Area");
		ins_frm.setSize(400,740);
		ins_frm.setBackground(Color.YELLOW);
		ins_frm.setForeground(Color.BLUE);
		ins_frm.setFont(new Font("Arial Black",Font.BOLD,13));
		cl.addActionListener(this);
		cls.addActionListener(this);
		p2=new Panel();p3=new Panel();
		ta=new TextArea(300,600);
		ta.setEditable(false);
		ta.append("Inst. No.");
		ta.append("	Mem. Addr.");
		ta.append("	Opcode");
		ta.append("	Operands");

ta.append("
----------------------------------------------------");
		fil.add(nw);
		fil.add(op);
		fil.add(cl);
		mbar.add(fil);
		mbar.add(edt);
		mbar.add(tools);
		ins_frm.setMenuBar(mbar);
		p2.add(lb);
		p3.add(cls);
		ins_frm.add(ta,BorderLayout.CENTER);
		ins_frm.add(p2,BorderLayout.NORTH);
		ins_frm.add(p3,BorderLayout.SOUTH);
	}
	public void SetString(String n)
	{
			ta.append("     ");
			ta.append(n);
			FrmShow();
	}
	public void FrmShow()
	{
		ins_frm.show();
	}
	public void FrmDsps()
	{
		ins_frm.dispose();
	}
	public void Adjust(String m,int l)
	{
		inr++;
		ta.append("
");
		ta.append(String.valueOf(inr));
		ta.append("		");
		ta.append(String.valueOf(l));
		ta.append("	                 ");
		ta.append(m);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==cls)
			FrmDsps();
		if(ae.getSource()==cl)
			FrmDsps();
	}
	public void windowClosing(WindowEvent we)
	{
			ins_frm.dispose();
	}
	public void windowActivated(WindowEvent we){}
	public void windowDeactivated(WindowEvent we){}
	public void windowDeiconified(WindowEvent we){}
	public void windowIconified(WindowEvent we){}
	public void windowClosed(WindowEvent we){}
	public void windowOpened(WindowEvent we){}
}
class SetC extends Check4
{
	char ch[];
	String
code1[]={"40","41","42","43","44","45","46","47","48","49","4A","4B","4C",
"4D","4E","4F","50","51","52","53","54","55","56","57","58","59","5A","5B"
,"5C","5D","5E","5F","60","61","62","63","64","65","66","67","68","69","6A
","6B","6C","6D","6E","6F","70","71","72","73","74","75","
","77","78","79","7A","7B","7C","7D","7E","7F"};
	public String setC(String m)
	{
		tmp=10;int tm=10;
		char ch[]=m.toCharArray();
		for(int cnt=0;cnt<8;cnt++)
		{
			if(ch[0]==ch1[cnt])
				tmp=cnt;
		}
		for(int cnt=0;cnt<8;cnt++)
		{
			if(ch[2]==ch1[cnt])
				tm=cnt;
		}
		return code1[tmp*8+tm];
	}
}
class Check
{
	char ch[]={'B','D','H','S','P'};
	public int getR(String sn)
	{
		int tmp=0;
		char ta[]=sn.toCharArray();
		if(ta.length==1)
		{
			for(int cnt=0;cnt<3;cnt++)
			{
				if(ta[0]==ch[cnt])
					tmp=cnt+1;
			}
		}
		if(ta.length==2)
		{
			if((ta[0]==ch[3])&&(ta[1]==ch[4]))
			{
				tmp=4;
			}
		}
		if(tmp!=0)
		{
			return tmp;
		}
		else
		{
			return 5;
		}
	}
}
class Check1
{
	int cnt=0;
	char
nm[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	public int chk(String n)
	{
		if(n.charAt(2)==' ')
			cnt++;
		char ch[]=n.toCharArray();
		if(ch.length==5)
			cnt++;
		{
			for(int y=0;y<16;y++)
			{
				if(ch[0]==nm[y])
					cnt++;
				if(ch[0]==nm[y])
					cnt++;
				if(ch[0]==nm[y])
					cnt++;
				if(ch[0]==nm[y])
					cnt++;
			}
		}
		return cnt;
	}
}
class About extends Frame implements ActionListener,WindowListener
{
	Frame fr;
	Button btn;
	TextArea ta;
	Panel pl,p_north,p_south;
	public About()
	{
		fr=new Frame("About");
		fr.addWindowListener(this);
		fr.setResizable(false);
		fr.setSize(400,400);
		ta=new TextArea("
");
		ta.setEditable(false);
		ta.setRows(20);
		ta.setColumns(40);
		ta.append("
");
		ta.append("   Welcome to 8085 Microprocessor Simulator
");
		ta.append("   __________________________________
");
		ta.append("`````````````````````````````````````````````````````````````
");
		ta.append("       Although 8085s are absolete in todays world,
");
		ta.append("   yet they are still widely used in schools and   
");
		ta.append("   colleges to train students in microprocessor    
");
		ta.append("   technologies.
");
		ta.append("      This is a small endeavor to make the job of 
");
		ta.append("   such students a bit simpler.
");
		btn=new Button("OK");
		btn.addActionListener(this);
		pl=new Panel();
		pl.setLayout(new BorderLayout());
		p_north=new Panel();
		p_south=new Panel();
		p_north.add(ta);
		p_south.add(btn);
		pl.add(p_north,BorderLayout.NORTH);
		pl.add(p_south,BorderLayout.SOUTH);
		fr.add(pl);
		fr.show();
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==btn)
			fr.dispose();
	}
	public void windowClosing(WindowEvent we)
	{
			fr.dispose();
	}
	public void windowActivated(WindowEvent we){}
	public void windowDeactivated(WindowEvent we){}
	public void windowDeiconified(WindowEvent we){}
	public void windowIconified(WindowEvent we){}
	public void windowClosed(WindowEvent we){}
	public void windowOpened(WindowEvent we){}
}
class Check2
{
	char ch[];
	char ch1[]={'B','D'};
	String tm[]={"02","12"};
	int tmp1=5;
	public String Chk2(String n)
	{
		int tmp=0;
		char ch[]=n.toCharArray();
		if(ch.length==1)
			tmp++;
		for(int cnt=0;cnt<2;cnt++)
		{
			if(ch[0]==ch1[cnt])
			{
				tmp++;
				tmp1=cnt;
			}
		}
		if(tmp==2)
		{
			if(tmp1==0)
				return tm[0];
			else
				return tm[1];
		}
		else
			return "0";
	}
}
class Check3 extends Check2
{
	char ch[];
	String tm[]={"0A","1A"};
	public String Chk3(String n)
	{
		int tmp=0;
		char ch[]=n.toCharArray();
		if(ch.length==1)
			tmp++;
		for(int cnt=0;cnt<2;cnt++)
		{
			if(ch[0]==ch1[cnt])
			{
				tmp++;
				tmp1=cnt;
			}
		}
		if(tmp==2)
		{
			if(tmp1==0)
				return tm[0];
			else
				return tm[1];
		}
		else
			return "0";
	}
}
class Check4
{
	char ch[];
	int tmp=0;
	char ch1[]={'B','C','D','E','H','L','M','A'};
	public String Chk4(String n)
	{
		char ch[]=n.toCharArray();
		if(ch.length==3)
			tmp++;
		if(ch[1]==' ')
			tmp++;
		for(int cnt=0;cnt<8;cnt++)
		{
			if(ch[0]==ch1[cnt])
				tmp++;
			if(ch[2]==ch1[cnt])
				tmp++;
		}
		if((ch[0]=='M')&&(ch[2]=='M'))
			tmp--;
		if(tmp==4)
			return "OK";
		else
			return "0";
	}
}
class FlagsWindow extends Frame implements WindowListener
{
	public static Panel pn1;
	public static Frame fagl;
	public static TextField txf[];
	public static Label lab[];
	public FlagsWindow()
	{
			String flags[]={"S","Z","x","AC","x","P","x","CY"};
			pn1=new Panel();
			pn1.setLayout(new FlowLayout());
			fagl=new Frame("FLAGS");
			fagl.addWindowListener(this);
			fagl.setResizable(false);
			fagl.setSize(350,120);
			lab=new Label[8];
			txf=new TextField[8];
			for(int cnt=0;cnt<8;cnt++)
			{
					lab[cnt]=new Label(flags[cnt]);
					pn1.add(lab[cnt]);
					txf[cnt]=new TextField(4);
					txf[cnt].setEditable(false);
					pn1.add(txf[cnt]);
			 }
			 fagl.add(pn1);
	}
	public void ShowF()
	{
		fagl.show();
	}
	public void windowClosing(WindowEvent we)
	{
			fagl.dispose();
	}
	public void windowActivated(WindowEvent we){}
	public void windowDeactivated(WindowEvent we){}
	public void windowDeiconified(WindowEvent we){}
	public void windowIconified(WindowEvent we){}
	public void windowClosed(WindowEvent we){}
	public void windowOpened(WindowEvent we){}
}
public class Project extends Applet implements
ActionListener,WindowListener,KeyListener
{
		Image img;
		int cgt=0;
		String re[]={"06","0E","16","1E","26","2E","36","3E"};
		String str1,gu,sng,hk;
		String
rege[]={"ADC","ADD","ANA","CMP","DCR","INR","ORA","SBB","SUB","XRA"};
		String
rez[]={"88","89","8A","8B","8C","8D","8E","8F","80","81","82","83","84","8
5","86","87","A0","A1","A2","A3","A4","A5","A6","A7","B8","B9","BA","BB","
BC","BD","BE","BF","05","0D","15","1D","25","2D","35","3D","04","0C","14",
"1C","24","2C","34","3C","B0","B1","B2","B3","B4","B5","B6","B7","98","99"
,"9A","9B","9C","9D","9E","9F","90","91","92","93","94","95","96","97","A8
","A9","AA","AB","AC","AD","AE","AF"};
		char reg1[]={'B','C','D','E','H','L','M','A'};
		String
ins1[]={"ADC","ADD","ANA","CMA","CMC","CMP","DAA","DAD","DCR","DCX","DI","
EI","HLT","INR","INX","LDAX","MOV","ORA","PCHL","POP","PUSH","RAL","RAR","
RLC","RRC","RET","RC","RNC","RP","RM","RPE","RPO","RZ","RNZ","RIM","RST","
TRAP","RST 5.5","RST 6.6","RST
7.5","SBB","SIM","SPHL","STAX","STC","SUB"};
		String
ins2[]={"ACI","ADI","ANI","CPI","IN","MVI","ORI","OUT","SBI","SUI","XRI"};
		String
ins3[]={"CALL","CALL","CC","CNC","CP","CM","CPE","CPO","CZ","CNZ","LDA","J
MP","JC","JNC","JP","JM","JPE","JPO","JZ","JNZ","LHLD","LXI","SHLD","STA"}
;
		String str[]={"A","B","C","D","E","H","L","SP","PC"};
		String
sxtin[]={"CALL","CC","CNC","CP","CM","CPE","CPO","CZ","CNZ","JMP","JC","JN
C","JP","JM","JPE","JPO","JZ","JNZ","LDA","LHLD","SHLD","STA"};
		String
code4[]={"CD","DC","D4","F4","FC","EC","E4","CC","C4","C3","DA","D2","F2",
"FA","EA","E2","CA","C2","3A","2A","22","32"};
		String
imm_ops8[]={"ACI","ADI","ANI","CPI","IN","ORI","OUT","SBI","SUI","XRI"};
		String code3[]={"CE","C6","E6","FE","DB","F6","D3","DE","D6","EE"};
		String
no_ops[]={"CMA","CMC","DAA","DI","EI","HLT","NOP","PCHL","RAL","RAR","RLC"
,"RRC","RET","RC","RNC","RP","RM","RPE","RPO","RZ","RNZ","RIM","SIM","SPHL
","STC","XCHG","XTHL"};
		String
code2[]={"2F","3F","27","F3","FB","76","00","E9","17","1F","07","0F","C9",
"D8","D0","F0","F8","E8","E0","C8","C0","20","30","F9","37","EB","E3"};
		String
all[]={"ACI","ADC","ADD","ADI","ANA","ANI","CALL","CC","CNC","CP","CM","CP
E","CPO","CZ","CNZ","CMA","CMC","CMP","CPI","DAA","DAD","DCR","DCX","DI","
EI","HLT","IN","INR","INX","JMP","JC","JNC","JP","JM","JPE","JPO","JZ","JN
Z","LDA","LDAX","LHLD","LXI","MOV","MVI","ORA","ORI","OUT","PCHL","POP","P
USH","RAL","RAR","RLC","RRC","RET","RC","RNC","RP","RM","RPE","RPO","RZ","
RNZ","RST 5.5","RST 6.5","RST
7.5","TRAP","SBB","SBI","SHLD","SIM","SPHL","STA","STAX","STC","SUB","SUI"
,"XCHG","XRA","XRI","XTHL"};
		String s1[]={"DAD","DCX","INX"};
		char sg1[]={'B','D','H','S','P'};
		String code5[]={"09","19","29","39"};
		String code6[]={"0B","1B","2B","3B"};
		String code7[]={"03","13","23","33"};
		String sh1[]={"POP","PUSH"};
		char ch2[]={'B','D','H','P','S','W'};
		String code8[]={"C1","D1","E1","F1","C5","D5","E5","F5"};
		String code9[]={"01","11","21","31"};
		Button
bc,flgs,intr[],run,stp,okey1,okey2,gen,bb,okey4,about,okey5,okey6,instrn,b
,b1,b5,b6,b7,b100;
		Frame fagl,rb,bout,hex_dia1,hex_dia2,fr,f1,f2,ff,f5,f6,f7,f;
		Panel mn_south,mn_east,pn1,pn2,pn3,pb,pl,p1,p2,p,pp,p5,p6,p7;
		Label 
clk,t_states,lb[],lab[],lab1,lab2,llb,bl,bl1,l1,l2,l,ll,l5,l6,l7;
		TextField ck,ts,txf[],tx2,tx3,tb,xt,t1,t2,t,tt,t5,t6,t7;
		int k=0,j=0,tp=0;
		static int kl,tmp1=0,q=0;
		Panel ko;
		String regsr[]={"PC","SP","A","B","C","D","E","H","L"};
		TextField ft[];
		Label bll[],bll1[],lbb;;
//INIT:
		public void init()
		{
//MAIN WINDOW
			bll1=new Label[13];
			ko=new Panel();
			lbb=new Label(" ");
			ko.setBackground(Color.pink);
			ko.setForeground(Color.blue);
			ko.setLayout(new GridLayout(30,1));
			for(int cnt=0;cnt<11;cnt++)
			{
				bll1[cnt]=new Label("    ");
				ko.add(bll1[cnt]);
			}
			ft=new TextField[9];
			bll=new Label[9];
			for(int cnt=0;cnt<9;cnt++)
			{
				bll[cnt]=new Label(regsr[cnt]);
				ft[cnt]=new TextField(4);

				ft[cnt].setEditable(false);
				ko.add(bll[cnt]);
				ko.add(ft[cnt]);
				//ko.add(lbb);
			}
			b100=new Button("See RAM AREA");
			b100.addActionListener(this);
			img=getImage(getDocumentBase(),getParameter("img"));
			about=new Button("About");
			about.addActionListener(this);
			gen=new Button("Generate code");
			gen.addActionListener(this);
			gen.addActionListener(this);
			instrn=new Button("Instruction Window");
			instrn.addActionListener(this);
			setLayout(new BorderLayout());
			clk=new Label("Clock Cycles");
			run=new Button("RUN");
			run.addActionListener(this);
			stp=new Button("STEP");
			t_states=new Label("T-States");
			ck=new TextField(5);
			ts=new TextField(5);
			ck.setEditable(false);
			ts.setEditable(false);
			flgs=new Button("Flags");
			mn_south=new Panel();
			mn_east=new Panel();
			mn_south.setBackground(Color.pink);
			mn_east.setLayout(new GridLayout(27,3));
			flgs.addActionListener(this);
			mn_south.add(flgs);
			mn_south.add(clk);
			mn_south.add(ck);
			mn_south.add(t_states);
			mn_south.add(ts);
			mn_south.add(run);
			mn_south.add(stp);
			mn_south.add(gen);
			mn_south.add(instrn);
			mn_south.add(b100);
			mn_south.add(about);
			intr=new Button[all.length];
			for(int cnt=0;cnt<all.length;cnt++)
			{
				intr[cnt]=new Button(all[cnt]);
				intr[cnt].addActionListener(this);
				mn_east.add(intr[cnt]);
			}
			add(ko,BorderLayout.WEST);
			add(mn_east,BorderLayout.EAST);
			add(mn_south,BorderLayout.SOUTH);
	//8-BIT DIALOG BOX
			tx2=new TextField(5);
			hex_dia1=new Frame("8-BIT DATA INPUT");
			hex_dia1.setSize(300,150);
			pn2=new Panel();
			lab1=new Label("Enter an 8-bit data in Hex Code..ex.88,90,9A,FF 
etc");
			okey1=new Button("OK");
			okey1.addActionListener(this);
			pn2.add(lab1);
			pn2.add(tx2);
			pn2.add(okey1);
			hex_dia1.add(pn2);
	//16-BIT DIALOG BOX
			tx3=new TextField(5);
			hex_dia2=new Frame("16-BIT DATA INPUT");
			hex_dia2.setSize(300,150);
			pn3=new Panel();
			lab2=new Label("Enter an 16-bit data in Hex Code..ex.88 90,AA B9 
etc");
			okey2=new Button("OK");
			okey2.addActionListener(this);
			pn3.add(lab2);
			pn3.add(tx3);
			pn3.add(okey2);
			hex_dia2.add(pn3);
	//REGISTER PROMPT DIALOG BOX
			rb=new Frame("Register Prompt");
			rb.setSize(300,150);
			llb=new Label("Enter Register or M");
			bb=new Button("OK");
			bb.addActionListener(this);
			tb=new TextField(4);
			pb=new Panel();
			pb.add(llb);
			pb.add(tb);
			pb.add(bb);
			rb.add(pb);
	//REG/MEM
			fr=new Frame("Enter your choice");
			fr.setSize(400,150);
			okey4=new Button("OK");
			okey4.addActionListener(this);
			xt=new TextField(4);
			bl=new Label("Enter your choice from the following options..");
			bl1=new Label("B C D E H L M A");
			pl=new Panel();
			pl.add(bl);
			pl.add(bl1);
			pl.add(xt);
			pl.add(okey4);
			fr.add(pl);
	//REG PAIR DIALOG BOX
			f1=new Frame("Enter a Register Pair");
			f1.setSize(300,150);
			l1=new Label("Enter one of the following options: B D H SP");
			p1=new Panel();
			t1=new TextField(4);
			okey5=new Button("OK");
			okey5.addActionListener(this);
			p1.add(l1);
			p1.add(t1);
			p1.add(okey5);
			f1.add(p1);
	//REG PAIR DIALOG BOX@
			f2=new Frame("Enter a Register Pair");
			f2.setSize(300,150);
			l2=new Label("Enter one of the following options: B D H PSW");
			p2=new Panel();
			t2=new TextField(4);
			okey6=new Button("OK");
			okey6.addActionListener(this);
			p2.add(l2);
			p2.add(t2);
			p2.add(okey6);
			f2.add(p2);
	//LXI DIALOG BOX
			f=new Frame("LXI Options window");
			f.setSize(400,175);
			p=new Panel();
			b=new Button("OK");
			b.addActionListener(this);
			t=new TextField();
			l=new Label("Enter any one of the folowing (B D H SP)");
			p.add(l);
			p.add(t);
			p.add(b);
			f.add(p);
			ff=new Frame("LXI Options window");
			ff.setSize(400,175);
			pp=new Panel();
			ll=new Label("Enter a 16-bit Data");
			tt=new TextField(4);
			bc=new Button("OK");
			bc.addActionListener(this);
			pp.add(ll);
			pp.add(tt);
			pp.add(bc);
			ff.add(pp);
	//B-D REG PAIR
			f5=new Frame("B or D Register Pair");
			f5.setSize(300,200);
			p5=new Panel();
			l5=new Label("Enter one of the following options. B or D");
			t5=new TextField(4);
			b5=new Button("OK");
			b5.addActionListener(this);
			p5.add(l5);
			p5.add(t5);
			p5.add(b5);
			f5.add(p5);
	//B-D REG PAIR
			f6=new Frame("B or D Register Pair");
			f6.setSize(300,200);
			p6=new Panel();
			l6=new Label("Enter one of the following options. B or D");
			t6=new TextField(4);
			b6=new Button("OK");
			b6.addActionListener(this);
			p6.add(l6);
			p6.add(t6);
			p6.add(b6);
			f6.add(p6);
	//MOV OPTIONS WINDOW
			f7=new Frame("B or D Register Pair");
			f7.setSize(300,200);
			p7=new Panel();
			l7=new Label("Enter Destination and Source..(B C D E H L M A)");
			t7=new TextField(4);
			b7=new Button("OK");
			b7.addActionListener(this);
			p7.add(l7);
			p7.add(t7);
			p7.add(b7);
			f7.add(p7);
			}
			MemArea qa=new MemArea();
			InstArea qb=new InstArea();
			FlagsWindow ac=new FlagsWindow();
//GRAPHICS:
		public void paint(Graphics g)
		{
			g.setColor(Color.blue);
			g.setFont(new Font("Times Roman",Font.ITALIC,19));
			g.drawString("Welcome! To 8085 Microprocessor Simulator ",185,150);
			g.setFont(new Font("Times Roman",Font.ITALIC,12));
			g.setColor(Color.black);
			g.drawString("Created By Kumar Bibek",420,170);
			g.setColor(Color.green);
			g.fillRect(200,180,400,2);
			g.setColor(Color.red);
			g.fillRect(210,185,400,2);
			g.setColor(Color.blue);
			g.fillRect(220,190,400,2);
			g.setColor(Color.green);
			g.drawImage(img,600,100,this);
		}
//LISTENERS
		public void actionPerformed(ActionEvent ae)
		{
				if(ae.getSource()==instrn)
					qb.FrmShow();
				if(ae.getActionCommand()=="MOV")
					f7.show();
				if(ae.getSource()==b7)
				{
					Check4 al=new Check4();
					String aw=t7.getText();
					String as=al.Chk4(aw);
					if(as=="0")
						f7.show();
					else
					{
						qb.SetString(aw);
						SetC ap=new SetC();
						String gw=ap.setC(aw);
						qa.SetCode(gw);
						f7.dispose();
					}
				}
				for(int cnt=0;cnt<s1.length;cnt++)
				{
					if(ae.getActionCommand()==s1[cnt])
					{
						tmp1=cnt;
						f1.show();
					}
				}
				if(ae.getSource()==okey5)
				{
					int tmp=5,tmp2=0;
					String st1=t1.getText();
					char ch[]=st1.toCharArray();
					for(int cnt=0;cnt<3;cnt++)
					{
						if(ch[0]==sg1[cnt])
						{
							tmp=0;
							tmp2=cnt;
						}
					}
					if((ch[0]==sg1[3])&&(ch[1]==sg1[4]))
					{
						tmp=0;
						tmp2=3;
					}
					if(tmp!=0)
						f1.show();
					else
					{
						if(tmp1==0)
							qa.SetCode(code5[tmp2]);
						if(tmp1==1)
							qa.SetCode(code6[tmp2]);
						if(tmp1==2)
							qa.SetCode(code7[tmp2]);
						f1.dispose();
						qb.SetString(st1);
					}
				}
				for(int cnt=0;cnt<sh1.length;cnt++)
				{
					if(ae.getActionCommand()==sh1[cnt])
					{
						f2.show();
						tp=cnt;
					}
				}
				if(ae.getSource()==okey6)
				{
					int tp1=5;
					String tps=t2.getText();
					char chs[]=tps.toCharArray();
					for(int cnt=0;cnt<3;cnt++)
					{
						if(chs[0]==ch2[cnt])
							tp1=cnt;
					}
					if((chs[0]==ch2[3])&&(chs[1]==ch2[4])&&(chs[2]==ch2[5]))
						tp1=3;
					if(tp1>4)
						f2.show();
					else
					{
						qa.SetCode(code8[tp*2+tp1]);
						qb.SetString(tps);
						f2.dispose();
					}
				}
				if(ae.getActionCommand()=="LXI")
					f.show();
				if(ae.getSource()==b)
				{
						sng=t.getText();
						Check ck=new Check();
						int flg=ck.getR(sng);
						if(flg!=5)
						{
							qa.SetCode(code9[flg-1]);
							f.dispose();
							ff.show();
						}
						else
							f.show();
				}
				if(ae.getSource()==bc)
				{
						String fh=tt.getText();
						Check1 ar=new Check1();
						int rt=ar.chk(fh);
						if(rt==6)
						{
							StringBuffer s5=new StringBuffer("       ");
							StringBuffer s6=new StringBuffer("       ");
							char qw[]=fh.toCharArray();
							s5.setCharAt(0,qw[0]);
							s5.setCharAt(1,qw[1]);
							s6.setCharAt(0,qw[3]);
							s6.setCharAt(1,qw[4]);
							qa.SetCode(s5.toString());
							qa.SetCode(s6.toString());
							qb.SetString(sng+","+tt.getText());
							ff.dispose();
						}
						else
							ff.show();
				}
				if(ae.getActionCommand()=="STAX")
				{
					String hk=ae.getActionCommand();
					f5.show();
				}
				if(ae.getSource()==b5)
				{
					Check2 an=new Check2();
					String jk=an.Chk2(t5.getText());
					if(jk=="0")
						f5.show();
					else
					{
						qa.SetCode(jk);
						qb.SetString(t5.getText());
						f5.dispose();
					}
				}
				if(ae.getActionCommand()=="LDAX")
				{
					String hk=ae.getActionCommand();
					f6.show();
				}
				if(ae.getSource()==b6)
				{
					Check3 am=new Check3();
					String jk=am.Chk3(t6.getText());
					if(jk=="0")
						f6.show();
					else
					{
						qa.SetCode(jk);
						qb.SetString(t6.getText());
						f6.dispose();
					}
				}
				if(ae.getSource()==about)
				{
					About ab=new About();
				}
				if(ae.getSource()==flgs)
				{
						ac.ShowF();
				}
				if(ae.getSource()==okey1)
				{
						str1=tx2.getText();
						if((str1.length()!=2))
							hex_dia1.show();
						else
						{
							qa.SetCode(str1);
							hex_dia1.dispose();
							qb.SetString("    ");
							qb.SetString(str1);
						}
				}
				for(int cnt=0;cnt<10;cnt++)
				{
					if(ae.getActionCommand()==rege[cnt])
					{
						q=cnt;
						fr.show();
					}
				}
				if(ae.getSource()==okey4)
				{
					if(cgt==0)
					{
						int bn=1;
						for(int ct=0;ct<8;ct++)
						{
							char ch;
							String jk=xt.getText();
							ch=jk.charAt(0);
							char sr2=reg1[ct];
							if(ch==sr2)
							{
								bn=0;
								StringBuffer t1=new StringBuffer(" ");
								t1.setCharAt(0,ch);
								String tmp=t1.toString();
								int hj=q*8;
								qb.SetString(tmp);
								qa.SetCode(rez[hj+ct]);
							}
						}
						fr.dispose();
						if(bn==1)
							fr.show();
					}
					if(cgt==10)
					{
						int bn=1;
						for(int ct=0;ct<8;ct++)
						{
							char ch;
							String jk=xt.getText();
							ch=jk.charAt(0);
							char sr2=reg1[ct];
							if(ch==sr2)
							{
								bn=0;
								StringBuffer t1=new StringBuffer(" ");
								t1.setCharAt(0,ch);
								String tmp=t1.toString();
								int hj=q*8;
								qb.SetString(tmp);
								qa.SetCode(re[ct]);
							}
						}
						fr.dispose();
						if(bn==1)
							fr.show();
						if(bn==0)
							hex_dia1.show();
					}
				}
				if(ae.getSource()==okey2)
				{
						str1=tx3.getText();
						if((str1.length()>4)&&(str1.length()<2))
								hex_dia2.show();
						if(str1.charAt(2)!=' ')
								hex_dia2.show();
						else
						{
							char s1,s2,s3,s4;
							StringBuffer s5=new StringBuffer("       ");
							StringBuffer s6=new StringBuffer("       ");
							String a,b;
							s1=str1.charAt(0);
							s2=str1.charAt(1);
							s3=str1.charAt(3);
							s4=str1.charAt(4);
							s5.setCharAt(0,s1);
							s5.setCharAt(1,s2);
							s6.setCharAt(0,s3);
							s6.setCharAt(1,s4);
							a=s5.toString();
							b=s6.toString();
							qa.SetCode(gu);
							qa.SetCode(a);
							qa.SetCode(b);
							hex_dia2.dispose();
							qb.SetString(str1);
							q=0;
						}
				}
				for(int cnt=0;cnt<all.length;cnt++)
				{
						if(ae.getActionCommand()==all[cnt])
						{
								if(j==1)
										k++;
								if(j==2)
										k=k+2;
								if(j==3)
										k=k+3;
								qb.Adjust(ae.getActionCommand(),k);
						}
				}
				for(int cnt=0;cnt<imm_ops8.length;cnt++)
				{
						if(ae.getActionCommand()==imm_ops8[cnt])
						{
								hex_dia1.show();
								for(int ctn=0;ctn<code3.length;ctn++)
								{
									if(ae.getActionCommand()==imm_ops8[ctn])
										qa.SetCode(code3[ctn]);
								}
						}
				}
				for(int cnt=0;cnt<sxtin.length;cnt++)
				{
						if(ae.getActionCommand()==sxtin[cnt])
						{
								if(ae.getActionCommand().length()==2)
									qb.SetString("    ");
								else
									qb.SetString("   ");
								gu=code4[cnt];
								hex_dia2.show();
								for(int ctn=0;ctn<19;ctn++)
								{
									if(ae.getActionCommand()==code4[ctn])
										qa.SetCode(code4[ctn]);
								}
						}
				 }
				for(int cnt=0;cnt<no_ops.length;cnt++)
				{
						if(ae.getActionCommand()==no_ops[cnt])
						{
								qb.SetString("");
								for(int ctn=0;ctn<code2.length;ctn++)
								{
									if(ae.getActionCommand()==no_ops[ctn])
										qa.SetCode(code2[cnt]);
								}
						}
				 }
				 if(ae.getActionCommand()=="MVI")
				 {
					 fr.show();
					 cgt=10;
				 }
				 else
				 	cgt=0;
				 for(int cnt=0;cnt<ins1.length;cnt++)
				 {
						if(ae.getActionCommand()==ins1[cnt])
							j=1;
				 }
				 for(int cnt=0;cnt<ins2.length;cnt++)
				 {
						if(ae.getActionCommand()==ins2[cnt])
								j=2;
				 }
				 for(int cnt=0;cnt<ins3.length;cnt++)
				 {
						if(ae.getActionCommand()==ins3[cnt])
								j=3;
				 }
//GENERATION OF CODE
				if(ae.getSource()==gen)
						qa.Show();
//RUNNING OF CODE
				if(ae.getSource()==run)
				{
					RunPro ar=new RunPro();
					int qwe=ar.compil();
					if(qwe==1)
					{
						RunErrors ws=new RunErrors();
					}
					if(qwe==0)
					{
						Proceed wa=new Proceed();
						ft[2].setText(wa.accl);
						ft[0].setText(wa.tobe[0]);
						ft[1].setText(wa.tobe[1]);
						for(int cnt=3;cnt<9;cnt++)
							ft[cnt].setText(wa.tobe[cnt-1]);
					}

				}
				if(ae.getSource()==b100)
				{
					UserRam kl=new UserRam();
					kl.f1.show();
				}
		}
		public void windowClosing(WindowEvent we)
		{
				qb.FrmDsps();
		}
		public void windowActivated(WindowEvent we){}
		public void windowDeactivated(WindowEvent we){}
		public void windowDeiconified(WindowEvent we){}
		public void windowIconified(WindowEvent we){}
		public void windowClosed(WindowEvent we){}
		public void windowOpened(WindowEvent we){}
		public void keyPressed(KeyEvent ke){}
		public void keyReleased(KeyEvent ke){}
		public void keyTyped(KeyEvent ke){}
}


