/*
       Project:  Alavu V2.0
       Date   :  May 29, 2004, Saturday 8:53 AM IST
       Scope  :  Communication layer collects data from
                 server at specified port and host and
                 also invokes logging of data of all
                 channels at specified interval and limits
                 number of Channels to maximum of 900
       Uses   :  SignalData Class, Record Class
    
*/
  
import java.net.*;

import java.io.*;

public class SocClient implements Runnable{

public static final int BUFFERLENGTH = 3700;// 900*4 + 8 = 3608

public static char[] doubleBuffer = new char[BUFFERLENGTH];

public static char[] stx = new char[4];

public static char[] etx = new char[4];

public static String strhost = new String();

public static String strport = new String();

public static double adcvalue = 4095.0;

SignalData sd;

Record  rd;

Thread thclient;

Socket conn;

public int recordcount = 0;// used to count no of samples to be logged

SocClient(){

sd = new SignalData();
rd = new Record();


}


public static void main(String arg[])
{
}


public void copyDoubleBuffer(){

  String data4 = new String();
  int arpointer = 0;
  int signalcount = 0;
  Integer convint = new Integer(10);
  double slope = 0, processvalue, temp;

  for(int count = 0; count < sd.noofSignal ; count++,arpointer+=4)
  {
      data4 = data4.copyValueOf(doubleBuffer,arpointer,4);

      for(int count1 = 0; count1 < sd.noofSignal; count1++)
      {
      if(sd.globalchannelno[count1] == count)
      {
      try{
      sd.signalcurrentvalue[count1]=convint.parseInt(data4);
      temp = sd.signalcurrentvalue[count1];
      slope = Integer.parseInt(sd.processhigh[count1])-Integer.parseInt(sd.processlow[count1]);
      slope = slope/adcvalue;
      processvalue = slope * temp;
      Double db = new Double(processvalue);
      sd.signalprocessvalue[count1]= db.intValue();
      sd.signalrecordvalue[count1][recordcount] = sd.signalcurrentvalue[count1];
      }
      catch(NumberFormatException e){;}
      }
      }
  }

  recordcount++;

  if(recordcount < sd.hourCount-1)
  {
    ;
  }
  else
    {
       recordcount = 0;
       rd.loghourlyData();
    }


}

public void run()
{

try{

System.out.println(sd.strhost+sd.strport);

conn = new Socket("",Integer.parseInt(sd.strport));

DataInputStream  in   = new DataInputStream(
                        new BufferedInputStream(conn.getInputStream()));

DataOutputStream out  = new DataOutputStream(
                        new BufferedOutputStream(conn.getOutputStream()));

int count = 0, count1 = 0, startidx = 0, endidx = 0;

String displaystr = new String();
String datastr    = new String();
String line       = new String();

datastr = "";

while(true)
{

 line = in.readUTF();
 
 count = 1;

 line.getChars(0, line.length(), doubleBuffer, count1);

 datastr += line;

 count1  += line.length();

 if(count1 > BUFFERLENGTH)
 {

   System.out.println("Information: Buffer Overflow");
   count1 = 0;
   datastr = "";
   continue;

 }
 
 if(datastr.endsWith("ZZZZ"))
  {

    count     = 0;
    startidx  = datastr.indexOf("S");
    endidx    = datastr.indexOf("Z");

    if(startidx == -1)
         System.out.println("Error start ");

    if(endidx == -1)
       {
         System.out.println("Error end ");
         continue;
       }

    datastr.getChars(startidx+4, endidx-1, doubleBuffer,0);
    displaystr = datastr.copyValueOf(doubleBuffer,0,endidx-4);
    copyDoubleBuffer();    
    count1     = 0;
    String str1 = new String();
    line       = "";
    datastr    = "";
  }

}

}

catch(UnknownHostException e){
   System.out.println("Socket error ");
}
catch(IOException e){
   System.out.println("IO Error ");
}
catch(NumberFormatException e)
{
   System.out.println("ClientMod: Number Format Exception");
}

}

}
