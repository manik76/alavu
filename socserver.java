/*
       Project: Alavu V2.0
       Date:    May 29, 2004, Saturday, 8:53 AM IST
       Scope:   Simulate Values for 900 Channels

*/
import java.net.*;
import java.io.*;

public class SocServer implements Runnable
{

public int channelcount = 900;

public int adcValue = 4096;

public static int[] datavalue= new int[900];

String strdatavalue1 = new String();
String strdatavalue2 = new String();
String strdatavalue3 = new String();
String strdatavalue4 = new String();
String strdatavalue5 = new String();

public static int reverseflag = 0;

int tempcount = 0;

String strhost;
String strport;

public void GenerateData()
{


  String str = new String();

  strdatavalue1 = "";
  strdatavalue2 = "";
  strdatavalue3 = "";
  strdatavalue4 = "";
  strdatavalue5 = "";

  strdatavalue1 = "SSSS";

  if(reverseflag == 0)
  {
   
   for(int count = 0; count < 900; count++)
     {
       datavalue[count] = tempcount;
     }
     tempcount += 50;
     if(tempcount >= adcValue)
       {
        reverseflag = 1;
       }
     System.out.println("reverse 0");
  }
  else
  {
   for(int count = 0; count < 900; count++)
     {
       datavalue[count] = tempcount ;
     }
    //
    tempcount -=50;
    if(tempcount <= 0)
       {
         reverseflag = 0;
       }
   System.out.println("reverse 1");
  }

  for(int count = 0; count < 900; count++)
     {
       if(datavalue[count] < 9)
         {
           str = "000" + str.valueOf(datavalue[count]);
         }
       else if(datavalue[count] >= 10 && datavalue[count] < 99)
         {
           str = "00" + str.valueOf(datavalue[count]);
         }
       else if(datavalue[count] >= 100 && datavalue[count] < 999)
         {
           str = "0" + str.valueOf(datavalue[count]);
         }
       else if(datavalue[count] >= 1000 && datavalue[count] < 9999)
         {
           str = str.valueOf(datavalue[count]);
         };

       if(count < 200)
         {
           strdatavalue1 += str;
         }
       else if(count >= 200 && count < 400)
         {
           strdatavalue2 += str;
         }
       else if(count >= 400 && count < 600)
         {
           strdatavalue3 += str;
         }
       else if(count >= 600 && count < 800)
         {
           strdatavalue4 += str;
         }
       else if(count >= 800 && count < 900)
         {
           strdatavalue5 += str;
         }
     }
         strdatavalue5 += "ZZZZ";

}


SocServer()
{

  System.out.println("Server started...");
  try{
     DataInputStream din = new DataInputStream(new
                               FileInputStream("config.ini"));
     din.readLine();
     din.readLine();
     din.readLine();
     din.readLine();
     din.readLine();
     din.readLine();
     din.readLine();
     strhost = din.readLine();
     strport = din.readLine();
     System.out.println(strhost);
     System.out.println(strport);

  }
  catch(FileNotFoundException e){;}
  catch(IOException e){;}


}

public static void main(String args[])
{
  Thread thServer = new Thread(new SocServer());
  thServer.start();
}

public void run()
{

try
 {
 
  ServerSocket sconnection = new ServerSocket(Integer.parseInt(strport));

  Socket conn;

  conn = sconnection.accept();

  DataInputStream in = new DataInputStream(
    new BufferedInputStream(conn.getInputStream()));

  DataOutputStream out = new DataOutputStream(
    new BufferedOutputStream(conn.getOutputStream()));

  System.out.println("connection accepted...");

  for(;;)
  {
    GenerateData();

    System.out.println(strdatavalue1);
    System.out.println(strdatavalue2);
    System.out.println(strdatavalue3);
    System.out.println(strdatavalue4);
    System.out.println(strdatavalue5);

    out.writeUTF(strdatavalue1);
    out.writeUTF(strdatavalue2);
    out.writeUTF(strdatavalue3);
    out.writeUTF(strdatavalue4);
    out.writeUTF(strdatavalue5);

    Thread.sleep(1000);
  }
 }
 catch(InterruptedException e)
  {
    System.out.println("Thread Error...");
  }
 catch(UnknownHostException e)
  {
    System.out.println("Socket Error ");
  }
 catch(IOException e)
  {
    System.out.println("IO Error ");
  }
 catch(NumberFormatException e)
  {
    System.out.println("Number Format Exception");
  }

}

}

