/*
     Project: Alavu V2.0
     Date:    May 29, 2004, Saturday, 8:53 AM IST
     Scope:   Shows status of 100 channels
     Uses:    SignalData
*/

import java.applet.Applet;
import java.awt.*;

public class Status extends Frame implements Runnable{

SignalData sd;

int statuschannel = 0;

final int heightcorrection = 30;
final int widthcorrection  = 20;
final int noofChannel      = 10;

int gridheight       = 40;
int gridwidth        = 40;


Font fnt1;
Font fnt2;

int displaytype = 0;

Status()
{

  sd = new SignalData();
  statuschannel = sd.currentsignalcount;
  fnt1 = new Font("TimesRoman",Font.PLAIN,12);
  fnt2 = new Font("TimesRoman",Font.PLAIN,12);
  setTitle("Status Panel");

}

public void run()
{

 show();
 for(;;)
 {
        try{Thread.sleep(sd.waittime);}
        catch(InterruptedException e){}
        repaint();
 }
}

public void paint(Graphics g)
{


    String str=new String();
    g.setColor(Color.blue); // Grid Colour
    int height;
    int width;
    int channelcount     = 0;
    int count    = 0,    count1 = 0;

    height       = size().height;
    width        = size().width;
    gridheight   = (height-2*heightcorrection)/ noofChannel;
    gridwidth    = (width-2*widthcorrection)  / noofChannel;
    channelcount = statuschannel;
    setBackground(Color.yellow);
    
    for(int rowcount = heightcorrection; count <= noofChannel; count++,rowcount+= gridheight)
       {            
          g.setColor(Color.green);  
          //g.drawLine(widthcorrection,rowcount,width-widthcorrection,rowcount);
          if(count != 10) 
          for(int widthcount = widthcorrection; count1 < noofChannel; count1++,widthcount+= gridwidth,channelcount++)
          {

               if(sd.signalprocessvalue[channelcount] <= Integer.parseInt(sd.processalarmlow[channelcount]))
                 {
                      g.setColor(Color.red);
                      g.fillRect(widthcount,rowcount,gridwidth,gridheight);
                 }
               else if(sd.signalprocessvalue[channelcount] >= Integer.parseInt(sd.processalarmhigh[channelcount]))
                 {
                      g.setColor(Color.blue);
                      g.fillRect(widthcount,rowcount,gridwidth,gridheight);
                  }
               else 
                 {
                      g.setColor(Color.white);
                      g.fillRect(widthcount,rowcount,gridwidth,gridheight);
                 }


              g.setColor(Color.green);  
              g.drawRect(widthcount,rowcount,gridwidth,gridheight);

              g.setColor(Color.black);
              if(displaytype == 0)
              {
              g.setFont(fnt1);
              g.drawString(str.valueOf(sd.signalprocessvalue[channelcount]),widthcount+gridwidth/3,rowcount+gridheight/3);
              g.setFont(fnt2);
              //g.drawString(sd.processunit[channelcount]    ,widthcount,rowcount+gridheight);
              g.drawString(sd.signaltagname[channelcount],widthcount,rowcount+gridheight);
              }
              else
              {
              g.setFont(fnt1);
              g.drawString(sd.processunit[channelcount],widthcount+gridwidth/3,rowcount+gridheight/3);
              //g.setFont(fnt2);
              g.drawString(sd.processlow[channelcount] ,widthcount,rowcount+gridheight);
              g.drawString(sd.processhigh[channelcount],widthcount+gridwidth/2,rowcount+gridheight);

              }
           }
           count1 = 0;

	}

      count1 = 0;
      for(int widthcount = widthcorrection; count1 <= 20; count1++,widthcount+= gridwidth)
        {
            g.setColor(Color.green);
//            g.drawLine(widthcount,heightcorrection,widthcount,height-heightcorrection);
        }

}

public boolean mouseDown(Event evt, int x, int y)
{

 if(displaytype == 0)
  displaytype = 1;
 else
  displaytype = 0;
  return true;
  
}

public void stop()
{
  Thread th = new Thread(this);
  th.stop();
}

public boolean handleEvent(Event evt)
{

 if(evt.id == Event.WINDOW_DESTROY)
             {
                     sd.signalgroupenable[statuschannel] = 0;
                     hide();
//                     ThreadGroup th = new ThreadGroup("Test");
//                     th = th.getParent();
                     this.stop();
             } 
 return super.handleEvent(evt);
 }
}




