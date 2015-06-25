/*
      Project:  Alavu V2.0
      Date:     May 29, 2004, Saturday, 8:53 AM IST
      Scope:    Shows mimic with corresponding values
      Uses:     SignalData
*/

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;

public class Mimic extends Frame implements Runnable{

Image img1;
public static int i = 0;
public int mimicchannelcount;
SignalData sd;

public Mimic()
{
   
   sd = new SignalData();
   mimicchannelcount = sd.currentsignalcount;
   img1 = getImage(sd.mimicimage[mimicchannelcount],this);
   System.out.println(sd.mimicimage[mimicchannelcount]);
   setTitle("Mimic View");

}

public static Image getImage(String name, Component cmp)
{

  Image img = null;
  URLClassLoader urlLoader = (URLClassLoader)cmp.getClass().getClassLoader();
  URL fileLoc = urlLoader.findResource(name);
  img = cmp.getToolkit().createImage(fileLoc);
  return img;

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
  String str = new String();
  int xvalue, yvalue;
  g.drawImage(img1, 0,0,this);
  g.setColor(Color.green);
  String curvalue = "";

  for(int count = 0; count < 5; count++)
  {

   if(sd.xcord[mimicchannelcount+count].length() == 0)
    xvalue = 0;
   else
    xvalue = Integer.parseInt(sd.xcord[mimicchannelcount+count]);

   if(sd.ycord[mimicchannelcount+count].length() == 0)
    yvalue = 0;
   else
    yvalue = Integer.parseInt(sd.ycord[mimicchannelcount+count]);

    g.setColor(Color.green);
   if(xvalue == 0 && yvalue == 0)
   ;
   else
   {
    g.drawString(sd.signaltagname[mimicchannelcount+count],xvalue,yvalue);
    g.drawString(curvalue.valueOf(sd.signalprocessvalue[mimicchannelcount+count]),xvalue,yvalue+10);
    g.drawString(sd.processunit[mimicchannelcount+count],xvalue,yvalue+20);
   }

  };

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
           sd.signalgroupenable[mimicchannelcount] = 0;
           hide();
           ThreadGroup th = new ThreadGroup("Test");
           th = th.getParent();
           th.stop();
         }
  return true;
}
}













