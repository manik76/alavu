/*
       Project: Alavu V2.0
       Date:    May 29, 2004, Saturday, 8:53 AM IST
       Scope:   Shows five channels refreshed every waittime
       Uses:    SignalData
*/

import java.awt.*;

class Bar extends Frame implements Runnable{

final int heightcalibration = 30;
final int widthcalibration  = 30;

final int barresolution = 14; //4096/300
final int barheight = 300;
final int adcvalue = 4096;

int height = 0;
int width  = 0;
int barchannel = 0;
int barchannelvalue1, barchannelvalue2;
int barchannelvalue3, barchannelvalue4,barchannelvalue5;

SignalData sd;

Bar()
{
   sd = new SignalData();
   barchannel = sd.currentsignalcount;
}

public void run()
{


  show();
  for(;;)
  {
        
        try{Thread.sleep(sd.waittime);}
        catch(InterruptedException e){;}
        repaint();

  }
}

public void paint(Graphics g)
{
  int tempwidth = 0;
  String strvalue = "";
  height = size().height;
  width  = size().width;
  tempwidth = (width - 2*widthcalibration) / 10;
  setTitle("Vertical Bar");
  g.setColor(Color.red);
  g.drawRect(widthcalibration,2*heightcalibration,widthcalibration+tempwidth,barheight);//height-3*heightcalibration);//note x2=widthcalibration+tempwidth
  g.setColor(Color.black);
  g.drawString(sd.processlow[barchannel],2*widthcalibration+tempwidth, height-heightcalibration);
  g.drawString(sd.processhigh[barchannel],2*widthcalibration+tempwidth, 2*heightcalibration);
  g.drawString(sd.processunit[barchannel],widthcalibration+tempwidth, height-heightcalibration/2);
  g.drawString(sd.signaltagname[barchannel],widthcalibration, height-heightcalibration/2);
  g.setColor(Color.blue);
  g.drawRect(widthcalibration+tempwidth*2,2*heightcalibration,widthcalibration+tempwidth,barheight);
  g.setColor(Color.black);
//  g.drawString(strvalue.valueOf(sd.signalcurrentvalue[barchannel+1]),tempwidth*3,(2*heightcalibration+barheight)/2);
  g.drawString(sd.processlow[barchannel+1],2*widthcalibration+tempwidth*3,height-heightcalibration);
  g.drawString(sd.processhigh[barchannel+1],2*widthcalibration+tempwidth*3,2*heightcalibration);
  g.drawString(sd.processunit[barchannel+1],widthcalibration+tempwidth*3,height-heightcalibration/2);
  g.drawString(sd.signaltagname[barchannel+1],widthcalibration+tempwidth*2,height-heightcalibration/2);
  g.setColor(Color.green);
  g.drawRect(widthcalibration+tempwidth*4,2*heightcalibration,widthcalibration+tempwidth,barheight);
  g.setColor(Color.black);
//  g.drawString(strvalue.valueOf(sd.signalcurrentvalue[barchannel+2]),tempwidth*5,(2*heightcalibration+ barheight)/2);
  g.drawString(sd.processlow[barchannel+2],2*widthcalibration+tempwidth*5,height-heightcalibration);
  g.drawString(sd.processhigh[barchannel+2],2*widthcalibration+tempwidth*5,2*heightcalibration);
  g.drawString(sd.processunit[barchannel+2],widthcalibration+tempwidth*5,height-heightcalibration/2);
  g.drawString(sd.signaltagname[barchannel+2],widthcalibration+tempwidth*4,height-heightcalibration/2);
  g.setColor(Color.gray);

  g.drawRect(widthcalibration+tempwidth*6,2*heightcalibration,widthcalibration+tempwidth,barheight);
  g.setColor(Color.black);
//  g.drawString(strvalue.valueOf(sd.signalcurrentvalue[barchannel+3]),tempwidth*7,(2*heightcalibration+ barheight)/2);
  g.drawString(sd.processlow[barchannel+3],2*widthcalibration+tempwidth*7,height-heightcalibration);
  g.drawString(sd.processhigh[barchannel+3],2*widthcalibration+tempwidth*7, 2*heightcalibration);
  g.drawString(sd.processunit[barchannel+3],widthcalibration+tempwidth*7,height-heightcalibration/2);
  g.drawString(sd.signaltagname[barchannel+3],widthcalibration+tempwidth*6,height-heightcalibration/2);
  g.setColor(Color.cyan);
  g.drawRect(widthcalibration+tempwidth*8,2*heightcalibration,widthcalibration+tempwidth,barheight);
  g.setColor(Color.black);
//  g.drawString(strvalue.valueOf(sd.signalcurrentvalue[barchannel+4]),tempwidth*9,(2*heightcalibration+ barheight)/2);
  g.drawString(sd.processlow[barchannel+4],2*widthcalibration+tempwidth*9,height-heightcalibration);
  g.drawString(sd.processhigh[barchannel+4],2*widthcalibration+tempwidth*9,2*heightcalibration);
  g.drawString(sd.processunit[barchannel+4],widthcalibration+tempwidth*9,height-heightcalibration/2);
  g.drawString(sd.signaltagname[barchannel+4],widthcalibration+tempwidth*8,height-heightcalibration/2);
// Alarm Mark

  int bar1low, bar2low, bar3low, bar4low,bar5low;
  int bar1high, bar2high, bar3high, bar4high,bar5high;
  double temp1,temp2,temp3;
  
  bar1low = barheight/(Integer.parseInt(sd.processhigh[barchannel])-Integer.parseInt(sd.processlow[barchannel]));
  bar1high = bar1low;
  bar1low = bar1low*Integer.parseInt(sd.processalarmlow[barchannel]);
  bar1high = bar1high*Integer.parseInt(sd.processalarmhigh[barchannel]);


  bar2low = barheight/(Integer.parseInt(sd.processhigh[barchannel+1])-Integer.parseInt(sd.processlow[barchannel+1]));
  bar2high = bar2low;
  bar2low = bar2low*Integer.parseInt(sd.processalarmlow[barchannel+1]);
  bar2high = bar2high*Integer.parseInt(sd.processalarmhigh[barchannel+1]);


  bar3low = barheight/(Integer.parseInt(sd.processhigh[barchannel+2])-Integer.parseInt(sd.processlow[barchannel+2]));
  bar3high = bar3low;
  bar3low = bar3low*Integer.parseInt(sd.processalarmlow[barchannel+2]);
  bar3high = bar3high*Integer.parseInt(sd.processalarmhigh[barchannel+2]);

  bar4low = barheight/(Integer.parseInt(sd.processhigh[barchannel+3])-Integer.parseInt(sd.processlow[barchannel+3]));
  bar4high = bar4low;
  bar4low = bar4low*Integer.parseInt(sd.processalarmlow[barchannel+3]);
  bar4high = bar4high*Integer.parseInt(sd.processalarmhigh[barchannel+3]);


  temp1 = (Integer.parseInt(sd.processhigh[barchannel])-Integer.parseInt(sd.processlow[barchannel]));
  temp2 = barheight;
  temp3 = temp2/temp1;
  temp1 = Integer.parseInt(sd.processalarmlow[barchannel]);
  temp2 = temp3 * temp1;
  Double dbl1 = new Double(temp2);
  bar1low = dbl1.intValue();
  temp1 = Integer.parseInt(sd.processalarmhigh[barchannel]);
  temp2 = temp3 * temp1;
  Double dbh1 = new Double(temp2);
  bar1high = dbh1.intValue();

  temp1 = (Integer.parseInt(sd.processhigh[barchannel+1])-Integer.parseInt(sd.processlow[barchannel+1]));
  temp2 = barheight;
  temp3 = temp2/temp1;
  temp1 = Integer.parseInt(sd.processalarmlow[barchannel+1]);
  temp2 = temp3 * temp1;
  Double dbl2 = new Double(temp2);
  bar2low = dbl2.intValue();
  temp1 = Integer.parseInt(sd.processalarmhigh[barchannel+1]);
  temp2 = temp3 * temp1;
  Double dbh2 = new Double(temp2);
  bar2high = dbh2.intValue();


  temp1 = (Integer.parseInt(sd.processhigh[barchannel+2])-Integer.parseInt(sd.processlow[barchannel+2]));
  temp2 = barheight;
  temp3 = temp2/temp1;
  temp1 = Integer.parseInt(sd.processalarmlow[barchannel+2]);
  temp2 = temp3 * temp1;
  Double dbl3 = new Double(temp2);
  bar5low = dbl3.intValue();
  temp1 = Integer.parseInt(sd.processalarmhigh[barchannel+2]);
  temp2 = temp3 * temp1;
  Double dbh3 = new Double(temp2);
  bar3high = dbh3.intValue();


  temp1 = (Integer.parseInt(sd.processhigh[barchannel+3])-Integer.parseInt(sd.processlow[barchannel+3]));
  temp2 = barheight;
  temp3 = temp2/temp1;
  temp1 = Integer.parseInt(sd.processalarmlow[barchannel+3]);
  temp2 = temp3 * temp1;
  Double dbl4 = new Double(temp2);
  bar4low = dbl4.intValue();
  temp1 = Integer.parseInt(sd.processalarmhigh[barchannel+3]);
  temp2 = temp3 * temp1;
  Double dbh4 = new Double(temp2);
  bar4high = dbh4.intValue();



  temp1 = (Integer.parseInt(sd.processhigh[barchannel+4])-Integer.parseInt(sd.processlow[barchannel+4]));
  temp2 = barheight;
  temp3 = temp2/temp1;
  temp1 = Integer.parseInt(sd.processalarmlow[barchannel+4]);
  temp2 = temp3 * temp1;
  Double dbl5 = new Double(temp2);
  bar5low = dbl5.intValue();
  temp1 = Integer.parseInt(sd.processalarmhigh[barchannel+4]);
  temp2 = temp3 * temp1;
  Double dbh5 = new Double(temp2);
  bar5high = dbh5.intValue();




// runtime part of bar
  barchannelvalue1 = sd.signalcurrentvalue[barchannel+1]/barresolution;
  barchannelvalue2 = sd.signalcurrentvalue[barchannel+1]/barresolution;
  barchannelvalue3 = sd.signalcurrentvalue[barchannel+2]/barresolution;
  barchannelvalue4 = sd.signalcurrentvalue[barchannel+3]/barresolution;
  barchannelvalue5 = sd.signalcurrentvalue[barchannel+4]/barresolution;

  g.setColor(Color.red);
  g.drawString(strvalue.valueOf(sd.signalprocessvalue[barchannel]),tempwidth,barheight-barchannelvalue1+2*heightcalibration);
  g.fillRect(widthcalibration, barheight-barchannelvalue1+2*heightcalibration, widthcalibration+tempwidth, barchannelvalue1);
  g.setColor(Color.blue);
  g.drawString(strvalue.valueOf(sd.signalprocessvalue[barchannel+1]),tempwidth*3,barheight-barchannelvalue2+2*heightcalibration);
  g.fillRect(widthcalibration+tempwidth*2,barheight-barchannelvalue2+2*heightcalibration,widthcalibration+tempwidth,barchannelvalue2);
  g.setColor(Color.green);
  g.drawString(strvalue.valueOf(sd.signalprocessvalue[barchannel+2]),tempwidth*5,barheight-barchannelvalue3+2*heightcalibration);
  g.fillRect(widthcalibration+tempwidth*4,barheight-barchannelvalue3+2*heightcalibration,widthcalibration+tempwidth,barchannelvalue3);
  g.setColor(Color.gray);
  g.drawString(strvalue.valueOf(sd.signalprocessvalue[barchannel+3]),tempwidth*7,barheight-barchannelvalue4+2*heightcalibration);
  g.fillRect(widthcalibration+tempwidth*6,barheight-barchannelvalue4+2*heightcalibration,widthcalibration+tempwidth,barchannelvalue4);
  g.setColor(Color.cyan);
  g.drawString(strvalue.valueOf(sd.signalprocessvalue[barchannel+4]),tempwidth*9,barheight-barchannelvalue5+2*heightcalibration);
  g.fillRect(widthcalibration+tempwidth*8,barheight-barchannelvalue5+2*heightcalibration,widthcalibration+tempwidth,barchannelvalue5);

  g.setColor(Color.black);
  g.drawString(sd.processalarmlow[barchannel],widthcalibration+widthcalibration+tempwidth,barheight+2*heightcalibration-bar1low);
  g.drawString(sd.processalarmhigh[barchannel],widthcalibration+widthcalibration+tempwidth,barheight+2*heightcalibration-bar1high);
  g.drawString(sd.processalarmlow[barchannel+1],widthcalibration+tempwidth+widthcalibration+tempwidth*2,barheight+2*heightcalibration-bar2low);
  g.drawString(sd.processalarmhigh[barchannel+1],widthcalibration+tempwidth+widthcalibration+tempwidth*2,barheight+2*heightcalibration-bar2high);
  g.drawString(sd.processalarmlow[barchannel+2],widthcalibration+tempwidth+widthcalibration+tempwidth*4,barheight+2*heightcalibration-bar3low);
  g.drawString(sd.processalarmhigh[barchannel+2],widthcalibration+tempwidth+widthcalibration+tempwidth*4,barheight+2*heightcalibration-bar3high);
  g.drawString(sd.processalarmlow[barchannel+3],widthcalibration+tempwidth+widthcalibration+tempwidth*6,barheight+2*heightcalibration-bar4low);
  g.drawString(sd.processalarmhigh[barchannel+3],widthcalibration+tempwidth+widthcalibration+tempwidth*6,barheight+2*heightcalibration-bar4high);
  g.drawString(sd.processalarmlow[barchannel+4],widthcalibration+tempwidth+widthcalibration+tempwidth*8,barheight+2*heightcalibration-bar5low);
  g.drawString(sd.processalarmhigh[barchannel+4],widthcalibration+tempwidth+widthcalibration+tempwidth*8,barheight+2*heightcalibration-bar5high);

  g.drawLine(widthcalibration,barheight+2*heightcalibration-bar1low,widthcalibration+widthcalibration+tempwidth,barheight+2*heightcalibration-bar1low);
  g.drawLine(widthcalibration+tempwidth*2,barheight+2*heightcalibration-bar2low,widthcalibration+tempwidth+widthcalibration+tempwidth*2,barheight+2*heightcalibration-bar2low);
  g.drawLine(widthcalibration+tempwidth*4,barheight+2*heightcalibration-bar3low,widthcalibration+tempwidth+widthcalibration+tempwidth*4,barheight+2*heightcalibration-bar3low);
  g.drawLine(widthcalibration+tempwidth*6,barheight+2*heightcalibration-bar4low,widthcalibration+tempwidth+widthcalibration+tempwidth*6,barheight+2*heightcalibration-bar4low);
  g.drawLine(widthcalibration+tempwidth*8,barheight+2*heightcalibration-bar5low,widthcalibration+tempwidth+widthcalibration+tempwidth*8,barheight+2*heightcalibration-bar5low);

  g.drawLine(widthcalibration,barheight+2*heightcalibration-bar1high,widthcalibration+widthcalibration+tempwidth,barheight+2*heightcalibration-bar1high);
  g.drawLine(widthcalibration+tempwidth*2,barheight+2*heightcalibration-bar2high,widthcalibration+tempwidth+widthcalibration+tempwidth*2,barheight+2*heightcalibration-bar2high);
  g.drawLine(widthcalibration+tempwidth*4,barheight+2*heightcalibration-bar3high,widthcalibration+tempwidth+widthcalibration+tempwidth*4,barheight+2*heightcalibration-bar3high);
  g.drawLine(widthcalibration+tempwidth*6,barheight+2*heightcalibration-bar4high,widthcalibration+tempwidth+widthcalibration+tempwidth*6,barheight+2*heightcalibration-bar4high);
  g.drawLine(widthcalibration+tempwidth*8,barheight+2*heightcalibration-bar5high,widthcalibration+tempwidth+widthcalibration+tempwidth*8,barheight+2*heightcalibration-bar5high);



}

public void stop()
{
   Thread th = new Thread(this);
   th.stop();
}

public boolean handleEvent(Event evt){

 if(evt.id == Event.WINDOW_DESTROY)
             {
                     sd.signalgroupenable[barchannel] = 0;
                     hide();
 //                    ThreadGroup th = new ThreadGroup("Test");
//                     th = th.getParent();
                     this.stop();

             } 
 return super.handleEvent(evt);
}
}






