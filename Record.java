/*
    Project: Alavu V2.0
    Date:    May 29, 2004, Saturday, 8:53 AM IST
    Scope:   Constructs filename and logs the data as integer values
    Uses:    SignalData
    
*/

import java.util.Calendar;
import java.text.DateFormat;
import java.util.*;
import java.text.*;
import java.io.*;

public class Record {

SignalData sd;

String fname;

Record(){
}

public void loghourlyData(){
    Date dt = new Date();
    String str = new String();
    fname  = str.valueOf(1900+dt.getYear());
    fname += str.valueOf(1+dt.getMonth());
    fname += str.valueOf(dt.getDate());
    fname += str.valueOf(dt.getHours());
    fname += ".log";
    System.out.println(fname);
    try{
    DataOutputStream logFile = new DataOutputStream(
                       new FileOutputStream(fname));
    for(int count=0; count < sd.noofSignal;count++)
     {
       for(int count1=0; count1 < sd.hourCount;count1++)
          {
            logFile.writeInt(sd.signalrecordvalue[count][count1]);
          } 
      }
      logFile.close();
    }
      catch(FileNotFoundException e){
      System.out.println("LogModule: File Not Found Error");
      }
      catch(IOException e){
      System.out.println("LogModule: IO Error");
      }

      

}

}
