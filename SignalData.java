// File Version of SignalData Date: 20/05/2004

/*
      Project: Alavu V2.0
      Date:    May 29, 2004, Saturday  8:53 AM IST
      Scope:   Centralised in-memory database
      Uses:    Config.ini
*/

import java.sql.*;

import java.io.*;

public class SignalData  

{

public final String strconfig = "config.ini";
public final int    width     = 600;
public final int    waittime  = 1000;

//Config data
public static boolean firstsw = true;
public static int noofSignal;
public static int hourCount;
public static int trendcount;
public static int barcount;
public static int statuscount;
public static String drvname;
public static String dsnname;
public static String strhost;
public static String strport;
public static String sigfilename;
public static String siggrpfilename;
//Config data

public static int signaldatacount;

// Constructor updates
public static String[] signaltagname;// = new String[noofSignal];
public static String[] signalgroupname;// = new String[noofSignal]; 
public static String[] processunit   ;//  = new String[noofSignal];
public static String[] processlow    ;//  = new String[noofSignal];
public static String[] processhigh   ;//  = new String[noofSignal]; 
public static String[] processalarmlow;// = new String[noofSignal];
public static String[] processalarmhigh;//= new String[noofSignal]; 
public static String[] xcord          ;// = new String[noofSignal]; 
public static String[] ycord          ;// = new String[noofSignal];
public static String[] mimicimage     ;// = new String[noofSignal];
// Constructor

public static int[]    signalcurrentvalue;// = new int[noofSignal];
public static double[]    signalprocessvalue;
public static int[][]  signaltrendvalue ;//  = new int[noofSignal][width];
public static String[] signalgrouptype  ;//  = new String[noofSignal];
public static int[][]  signalrecordvalue;//  = new int[noofSignal][hourCount];
public static int[][]  statsignal1value;//  = new int[noofSignal][hourCount];
public static int[][]  statsignal2value;//  = new int[noofSignal][hourCount];
public static int[][]  foursignal1value;//  = new int[noofSignal][hourCount];
public static String   statsignal1;
public static String   statsignal2;
public static String   statlog1;
public static String   statlog2;
public static String   foursignal1;
public static String   fourlog1;

// Multiple threads updates this variable
public static int[]    signalgroupenable ;// = new int[noofSignal];
// Multiple threads updates this variable
public static int[]    globalchannelno ; //  = new int[noofSignal];
public static int currentsignalcount;

public SignalData()
{

int count = 0,count1 = 5;
if(firstsw)
{
try
{
  DataInputStream din = new DataInputStream(
                        new FileInputStream(strconfig));
  String configdata;
  configdata = din.readLine();
  hourCount  = 60*Integer.parseInt(configdata);
  configdata = din.readLine();
  noofSignal = Integer.parseInt(configdata);
  configdata = din.readLine();
  trendcount = Integer.parseInt(configdata);
  configdata = din.readLine();
  barcount   = Integer.parseInt(configdata);
  configdata = din.readLine();
  statuscount = Integer.parseInt(configdata);
  drvname     = din.readLine();
  dsnname     = din.readLine();
  strhost     = din.readLine();
  strport     = din.readLine();
  sigfilename = din.readLine();
  siggrpfilename=din.readLine();
  din.close();

}

catch(FileNotFoundException e)
{
  System.out.println("SignalDataMod: File Not Found ");
}

catch(IOException e)
{
  System.out.println("SignalDataMod: IO Error ");
}

signaltagname = new String[noofSignal];
signalgroupname = new String[noofSignal]; 
processunit     = new String[noofSignal];
processlow      = new String[noofSignal];
processhigh     = new String[noofSignal]; 
processalarmlow = new String[noofSignal];
processalarmhigh= new String[noofSignal]; 
xcord           = new String[noofSignal]; 
ycord           = new String[noofSignal];
mimicimage      = new String[noofSignal];

// Constructor

signalcurrentvalue = new int[noofSignal];
signalprocessvalue = new double[noofSignal];
signaltrendvalue   = new int[noofSignal][width];
signalgrouptype    = new String[noofSignal];
signalrecordvalue  = new int[noofSignal][hourCount];
statsignal1value  = new int[noofSignal][hourCount];
statsignal2value  = new int[noofSignal][hourCount];
foursignal1value  = new int[noofSignal][hourCount];

// Multiple threads updates this variable
signalgroupenable  = new int[noofSignal];
// Multiple threads updates this variable
globalchannelno    = new int[noofSignal];

statsignal1 = new String();
statsignal2 = new String();
statlog1    = new String();
statlog2    = new String();
foursignal1 = new String();
fourlog1    = new String();

String fieldarr[] = new String[20];
int fieldcount    = 0;
int charcount     = 0;
String record     = "";
String field      = "";

for(int initcount = 0; initcount < 20;initcount++)
{
   fieldarr[initcount] = "";
}

try{
DataInputStream din = new DataInputStream(
                      new FileInputStream(siggrpfilename));


      while(true)
           {
             if( count1 > noofSignal)
               break;
             charcount =0;
             fieldcount = 0;
             count1++;
             try{
             record = din.readLine();
//             System.out.println(record);
             record.length();
             }
             catch(NullPointerException e){break;}
             while(charcount < record.length())
             {
                if(record.charAt(charcount)==' ')
                  {
//                   System.out.println(field);
                   fieldarr[fieldcount] = field;
                   field = "";
                   fieldcount++;
                   if(fieldcount > 18)
                      break;
                   charcount++;
                   continue;
                   }
                field += record.charAt(charcount);

                charcount++;
             }
             signalgroupname[count]   = fieldarr[0].trim();
             signalgrouptype[count]   = fieldarr[1].trim();
             signaltagname[count]     = fieldarr[2].trim();
             mimicimage[count]        = fieldarr[17].trim();
             signalgroupname[count+1] = signalgroupname[count];
             signalgrouptype[count+1] = signalgrouptype[count];
             signaltagname[count+1] = fieldarr[3].trim();
             mimicimage[count+1]      = mimicimage[count];
             signalgroupname[count+2] = signalgroupname[count];
             signalgrouptype[count+2] = signalgrouptype[count];
             signaltagname[count+2] = fieldarr[4].trim();
             mimicimage[count+2]      = mimicimage[count];
             signalgroupname[count+3] = signalgroupname[count];
             signalgrouptype[count+3] = signalgrouptype[count];
             signaltagname[count+3] = fieldarr[5].trim();
             mimicimage[count+3]      = mimicimage[count];
             signalgroupname[count+4] = signalgroupname[count];
             signalgrouptype[count+4] = signalgrouptype[count] ;
             signaltagname[count+4] = fieldarr[6].trim();
             mimicimage[count+4]      = mimicimage[count];
             xcord[count]             = fieldarr[7].trim();
             ycord[count]             = fieldarr[8].trim();
             xcord[count+1]           = fieldarr[9].trim();
             ycord[count+1]           = fieldarr[10].trim();
             xcord[count+2]           = fieldarr[11].trim();
             ycord[count+2]           = fieldarr[12].trim();
             xcord[count+3]           = fieldarr[13].trim();
             ycord[count+3]           = fieldarr[14].trim();
             xcord[count+4]           = fieldarr[15].trim();
             ycord[count+4]           = fieldarr[16].trim();
             count+=5;
            }
       din.close();
}
catch(FileNotFoundException e){;}
catch(IOException e){;}

signaldatacount = count;
String str1 = "";
field = "";
int matchcount = 0;
int matchcount1 = 0;

for(;count>0;count--)
{
try{
DataInputStream din1 = new DataInputStream(
                       new FileInputStream(sigfilename));
String fieldarr1[] = new String[10];
String record1 = "";
int fieldcount1 = 0;
int charcount1 = 0;

      while(true)
            {
              charcount1 = 0;
              fieldcount1 = 0;
              matchcount1++;
              try{
              record1 = din1.readLine();
              record1.length();
              }
             catch(NullPointerException e){break;}
             while(charcount1 < record1.length())
             {
                if(record1.charAt(charcount1)==' ')
                  {
                   fieldarr1[fieldcount1] = field.trim();
                   fieldcount1++;
                   field = "";
                   charcount1++;
                   continue;
                   }
                field += record1.charAt(charcount1);
                charcount1++;
             
             }

          if(signaltagname[count-1].equals(fieldarr1[1]))
            {
              matchcount++;
              globalchannelno[count-1]  = Integer.parseInt(fieldarr1[0].trim());
              processlow[count-1]       = fieldarr1[3].trim();    
              processhigh[count-1]      = fieldarr1[4].trim();
              processalarmhigh[count-1] = fieldarr1[6].trim();
              processalarmlow[count-1]  = fieldarr1[5].trim();
              processunit[count-1]      = fieldarr1[8].trim();
            }
        
         }
}
catch(FileNotFoundException e){;}
catch(IOException e){;}
}

 firstsw = false;

}

}

}

