/*
     Project: Alavu V2.0
     Date:    May 29, 2004, Saturday, 8:53 AM IST
     Scope:   Shows trend of two channels of resolution
              600X300
     Uses:    SignalData
*/

import java.applet.Applet;
import java.awt.*;

public class Trend extends Frame implements Runnable{

SignalData sd;

public int trendchannel;

final int heightcorrection = 50;
final int widthcorrection  = 20;
final int gridheight       = 3;
final int gridwidth        = 5;

int noofRows = 100;
int noofCols = 100;

public int trendresolution = 14; // 4096/300 = 13.65

public static int i;

Trend()
{
    sd = new SignalData();

    trendchannel = sd.currentsignalcount; //multiples of 5
   
    for(int count1 = 0; count1 < sd.signaldatacount; count1++)
     for(int count = 0; count  < sd.width; count++)
       {
         sd.signaltrendvalue[count1][count] = 0;
       }
   
    setBackground(Color.black); //backgroungcolor
    setTitle("Trend Graph");
}

public void run()
{
 show();
 String str= new String();
 for(;;)
 {
         sd.signaltrendvalue[trendchannel][sd.width-1] = sd.signalcurrentvalue[trendchannel]/trendresolution;
         sd.signaltrendvalue[trendchannel+1][sd.width-1] = sd.signalcurrentvalue[trendchannel+1]/trendresolution;
         for(int count = 0; count  < sd.width-1; count++)
             {
               sd.signaltrendvalue[trendchannel][count] = sd.signaltrendvalue[trendchannel][count+1];
               sd.signaltrendvalue[trendchannel+1][count] = sd.signaltrendvalue[trendchannel+1][count+1];
               str += sd.signaltrendvalue[trendchannel+1][count];
             }
         repaint();
         try{Thread.sleep(sd.waittime);}
         catch(InterruptedException e){}
 }
}

public void paint(Graphics g)
{


    int rowcount , widthcount;

    int height = 0 , width = 0;

    int trendchannelinc1, trendchannelinc2;

    int countt1 = 0, countt2 = 0;

    String str = new String();

    Font fnt = new Font("TimesNewRoman",Font.BOLD,8);

    g.setFont(fnt);

    height = size().height;

    width  = size().width;
    
    g.setColor(Color.white); // Grid Colour

    g.drawRect(4*widthcorrection,heightcorrection,noofCols*gridwidth,noofRows*gridheight);

    int count1 = 100;
  
    for(rowcount = heightcorrection; rowcount <= noofRows*gridheight+heightcorrection; rowcount+= gridheight)
	{
            g.setColor(Color.gray); // Grid Colour

            g.drawLine(4*widthcorrection,rowcount,4*widthcorrection+noofCols*gridwidth,rowcount);

            g.setColor(Color.yellow);

            if(count1%5 == 0)
             {
                g.drawString(str.valueOf(count1),2*widthcorrection+widthcorrection/2,rowcount);//3 row adjustment
             }
            count1--;
	}

        count1 = 100; //10 samples per second assumed each pixel
        //100 millisecond

    for(widthcount = 4*widthcorrection; widthcount < noofCols*gridwidth+4*widthcorrection; widthcount+= gridwidth)
        {

            g.setColor(Color.gray); 
            g.drawLine(widthcount,heightcorrection,widthcount,rowcount-gridheight);
            g.setColor(Color.green);
            
            if(count1%10==0)
            {
              g.drawString(str.valueOf(-(count1/10)),widthcount,rowcount+heightcorrection/2);
            }
            count1 --;
        }

    g.drawString(str.valueOf(-(count1)),widthcount,rowcount+heightcorrection/2);

    g.drawString("Minutes",width/2,rowcount+heightcorrection);
    g.setColor(Color.yellow);
    g.drawString(sd.processlow[trendchannel],widthcorrection/2,rowcount);
    g.drawString(sd.processhigh[trendchannel],widthcorrection/2,heightcorrection);
    g.drawString(sd.processunit[trendchannel],widthcorrection/2,height/3+heightcorrection/4);
    g.drawString(sd.signaltagname[trendchannel],widthcorrection/2,height/3);

    g.setColor(Color.blue);
    g.drawString(sd.processlow[trendchannel+1],widthcorrection/2,rowcount-heightcorrection/4);
    g.drawString(sd.processhigh[trendchannel+1],widthcorrection/2,heightcorrection*3/4);
    g.drawString(sd.processunit[trendchannel+1],widthcorrection/2,height*2/3+heightcorrection/4);
    g.drawString(sd.signaltagname[trendchannel+1],widthcorrection/2,height*2/3);
    g.setColor(Color.yellow);

    width = size().width;

    countt1 = 4*widthcorrection+noofCols*gridwidth;

    String str1= "";
   
    for(;countt1>4*widthcorrection;countt1--)
    {
     g.drawLine(countt1,rowcount-gridheight-sd.signaltrendvalue[trendchannel][countt1],countt1-1,rowcount-sd.signaltrendvalue[trendchannel][countt1-1]-gridheight);
    }

    g.setColor(Color.blue);

    for(countt2 = 4*widthcorrection+noofCols*gridwidth; countt2 > 4*widthcorrection; countt2--)
	{
          g.drawLine(countt2+1,rowcount-gridheight-sd.signaltrendvalue[trendchannel+1][countt2],countt2,rowcount-sd.signaltrendvalue[trendchannel+1][countt2-1]-gridheight);
	}
}

public void stop(){
Thread th = new Thread(this);
th.stop();

}

public boolean handleEvent(Event evt){

 if(evt.id == Event.WINDOW_DESTROY)
    {
            sd.signalgroupenable[trendchannel] = 0;// used to indicated selected trend group is closed
            hide();
            this.stop();
    } 
 return super.handleEvent(evt);
}
}





