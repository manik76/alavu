/*
       Project:  Alavu V2.0
       Date:     May 29, 2004, Saturday, 8:53 AM IST
       Scope:    Computes Minimum, Maximum, Mean, Standard Deviation
                 Regression and Correlation
       Uses:     SignalData
*/
import java.awt.*;
import java.io.*;

public class Statistics extends Frame implements Runnable{

final int noofData = 3600;
double meanvalueX;
double minvalueX;
double maxvalueX;
double sdvalueX;
double regressionvalue;
double meanvalueY;
double minvalueY;
double maxvalueY;
double sdvalueY;

double correlationvalue;
SignalData sd1;
double ChannelX[];
double ChannelY[];
double ChannelProcessX[];
double ChannelProcessY[];
int statChannelX;
int statChannelY;

String statChannelplX = "";
String statChannelphX = "";
String statChannelpuX = "";

String statChannelplY = "";
String statChannelphY = "";
String statChannelpuY = "";

Statistics()
  {

    sd1 = new SignalData();


  String record = "";
  String fieldarr[]  = new String[20];
  String field = "";
  int fieldcount=0;
  int charcount=0;
  String globalchno = "";

  try
  {
    DataInputStream din = new DataInputStream(
                          new FileInputStream(sd1.sigfilename));
    while(true)
    {
      record = din.readLine();

      try{
       record.length();  
      }
      catch(NullPointerException e){break;}

      field ="";
      charcount = 0;
      fieldcount = 0;

      while(charcount < record.length())
      {
          if(record.charAt(charcount) == ' ')//fielddelimiter
          {
            fieldarr[fieldcount] = field;
            field="";
            fieldcount++;
          }
          field += record.charAt(charcount);
          charcount++;
      }

      if(fieldarr[1].equals(sd1.statsignal1))
         {
           statChannelX = Integer.parseInt(fieldarr[0].trim());
           statChannelplX = fieldarr[3].trim();
           statChannelphX = fieldarr[4].trim();
           statChannelpuX =fieldarr[8];
         }
 
         
      if(fieldarr[1].equals(sd1.statsignal2))
         {
            statChannelY = Integer.parseInt(fieldarr[0].trim());
            statChannelplY = fieldarr[3].trim();
            statChannelphY = fieldarr[4].trim();
            statChannelpuY = fieldarr[8];

         }
    
     
    }

  }
  catch(FileNotFoundException e){;}
  catch(IOException e){;}

  ChannelX = new double[sd1.hourCount];
  ChannelY = new double[sd1.hourCount];
  ChannelProcessX = new double[sd1.hourCount];
  ChannelProcessY = new double[sd1.hourCount];


  setTitle("Statistics");

}

public void convProcess()
{

   double temp=0,slope =0;
   double adcvalue = 4095;
   

   for(int count1=0; count1 < sd1.hourCount;count1++)
      {
            
         temp = ChannelX[count1];
         slope = Integer.parseInt(statChannelphX) - Integer.parseInt(statChannelplX);
         slope = slope/adcvalue;
         temp  = slope * temp;
         ChannelProcessX[count1] = temp;
         
         temp = ChannelY[count1];
         slope = Integer.parseInt(statChannelphY) - Integer.parseInt(statChannelplY);
         slope = slope/adcvalue;
         temp  = slope * temp;
         ChannelProcessY[count1] = temp;

      } 


}

public void run()
  {

try{
    DataInputStream ch1File = new DataInputStream(
                       new FileInputStream(sd1.statlog1));
    for(int count=0; count <= statChannelX;count++)
     {
       for(int count1=0; count1 < sd1.hourCount;count1++)
          {
            sd1.statsignal1value[count][count1]=ch1File.readInt();
            
            if(count == statChannelX)
              {
               ChannelX[count1] = sd1.statsignal1value[count][count1];
              }

          } 
      }
    }
      catch(FileNotFoundException e){
      System.out.println("StatModule: File Not Found Error");
      }
      catch(IOException e){
      System.out.println("StatModule: IO Error");
      }


try{
    DataInputStream ch2File = new DataInputStream(
                       new FileInputStream(sd1.statlog2));
    for(int count=0; count <= statChannelY;count++)
     {

       for(int count1=0; count1 <sd1.hourCount;count1++)
          {
            sd1.statsignal2value[count][count1]=ch2File.readInt();
            if(count == statChannelY)
              {
               ChannelY[count1] = sd1.statsignal2value[count][count1];
              }

            
          } 
      }
    }
      catch(FileNotFoundException e){
      System.out.println("StatModule: File Not Found Error");
      }
      catch(IOException e){
      System.out.println("StatModule: IO Error");
      }

  convProcess();
  mean();
  sd();
  regression();
  correlation();
  show();
}


public void mean()
{
   minvalueX= 0;
   maxvalueX = 0;
   meanvalueX = 0;
   minvalueY = 0;
   maxvalueY = 0;
   meanvalueY = 0;

   for(int count = 0; count < sd1.hourCount; count++)
   {
      if(minvalueX > ChannelProcessX[count])
        {
           minvalueX = ChannelProcessX[count];
        }

      if(maxvalueX < ChannelProcessX[count])
        {
            maxvalueX = ChannelProcessX[count];
        }

       meanvalueX += ChannelProcessX[count];

       if(minvalueY > ChannelProcessY[count])
        {
           minvalueY = ChannelProcessY[count];
        }

      if(maxvalueY < ChannelProcessY[count])
        {
            maxvalueY = ChannelProcessY[count];
        }

       meanvalueY += ChannelProcessY[count];

      
   }
       meanvalueX = meanvalueX/sd1.hourCount;
       meanvalueY = meanvalueY/sd1.hourCount;

}

public void sd()
{
  sdvalueX = 0;
  sdvalueY = 0;

  for(int count = 0; count < sd1.hourCount; count ++)
     {
        sdvalueX += (ChannelProcessX[count] - meanvalueX)*(ChannelProcessX[count] - meanvalueX);
        sdvalueY += (ChannelProcessY[count] - meanvalueY)*(ChannelProcessY[count] - meanvalueY);
     }

     sdvalueX = Math.sqrt(sdvalueX/sd1.hourCount); 
     sdvalueY = Math.sqrt(sdvalueY/sd1.hourCount); 

}

public void regression()
{

  double sumX=0;
  double sumY=0;
  double sumXY=0;
  double sumXX=0;
  double meanX=0;
  double meanY=0;

  double YC=0;
  double a =0;
  double b =0;

  for(int count =0; count < sd1.hourCount; count++)
  {
      sumX += ChannelProcessX[count];
      sumY += ChannelProcessY[count];
      sumXY += ChannelProcessX[count]*ChannelProcessY[count];
      sumXX += ChannelProcessX[count]*ChannelProcessX[count];
  }

  meanX = sumX/sd1.hourCount;
  meanY = sumY/sd1.hourCount;

  b = (sumXY - sd1.hourCount*meanX*meanY)/(sumXX - sd1.hourCount*meanX*meanX);
  a =  meanY - b * meanX;

  for(int count1=0; count1 < sd1.hourCount; count1++)
  {
      YC = a + b * ChannelProcessX[count1];
      regressionvalue += (ChannelProcessY[count1] - YC)*(ChannelProcessY[count1] - YC);

  }

  regressionvalue = Math.sqrt(regressionvalue/sd1.hourCount);

}

public void correlation()
{

  double sumX=0;
  double sumY=0;
  double meanX=0;
  double meanY=0;
  double sumXY=0;
  double sumXX=0;
  double sumYY=0;

  for(int count =0; count < sd1.hourCount; count++)
  {
      sumX += ChannelProcessX[count];
      sumY += ChannelProcessY[count];
  }

  meanX = sumX/sd1.hourCount;
  meanY = sumY/sd1.hourCount;

  for(int count1 =0; count1 < sd1.hourCount; count1++)
  {
      sumXY += (ChannelX[count1]-meanX)*(ChannelY[count1]-meanY);
      sumXX += (ChannelX[count1]-meanX)*(ChannelX[count1]-meanX);
      sumYY += (ChannelY[count1]-meanY)*(ChannelY[count1]-meanY);

  }

  correlationvalue = sumXY/Math.sqrt(sumXX*sumYY);
   

}

public void paint(Graphics g)
{

   setBackground(Color.gray);
   g.setColor(Color.blue);
   String str="";
   str = "Statistic Report:";
   g.drawString(str,50,50);
   str = "------------------------";
   g.drawString(str,50,70);
   str = "Signal1 " + sd1.statsignal1+" Logname1 "+sd1.statlog1;
   g.drawString(str,50,90);
   str = "Minimum: " + str.valueOf(minvalueX)+ " "+statChannelpuX;
   g.drawString(str,50,110);
   str = "Maximum: " + str.valueOf(maxvalueX)+ " "+statChannelpuX;;
   g.drawString(str,50,130);
   str = "Mean: " + str.valueOf(meanvalueX)+ " "+statChannelpuX;;
   g.drawString(str,50,150);
   str = "Standard Deviation: " + str.valueOf(sdvalueX);
   g.drawString(str,50,170);
   g.setColor(Color.green); 
   str = "Signal2 " + sd1.statsignal2+" Logname2 "+sd1.statlog2;
   g.drawString(str,50,190);
   str = "Minimum: " + str.valueOf(minvalueY)+ " "+statChannelpuY;
   g.drawString(str,50,210);
   str = "Maximum: " + str.valueOf(maxvalueY)+ " "+statChannelpuY;
   g.drawString(str,50,230);
   str = "Mean: " + str.valueOf(meanvalueY)+ " "+statChannelpuY;
   g.drawString(str,50,250);
   str = "Standard Deviation: " + str.valueOf(sdvalueY);
   g.drawString(str,50,270);
   g.setColor(Color.yellow);
   str = "Signal1 " + sd1.statsignal1+" & ";    
   str += "Signal2 " + sd1.statsignal2;
   g.drawString(str,50,290);
   str = "Regression: Signal1 on Signal2 " + str.valueOf(regressionvalue);
   g.drawString(str,50,310);
   str = "Correlation: Signal1 and Signal2 " + str.valueOf(correlationvalue);
   g.drawString(str,50,330);


}

public void stop(){
Thread th = new Thread(this);
th.stop();

}

public boolean handleEvent(Event evt){

 if(evt.id == Event.WINDOW_DESTROY)
    {
            hide();
            this.stop();
    } 
 return super.handleEvent(evt);

}


}
