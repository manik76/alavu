/*
       Project: Alavu V2.0
       Date:    May 29, 2004, Saturday, 8:53 AM IST
       Scope:   Displays help contents
       Uses:    Help.txt
*/

import java.awt.*;
import java.io.*;

public class Help extends Frame{

final int LINECOUNT = 1000;
final String strhelp = "help.txt";
String helpstr[];

String str1 = "Hello String";
int pageup = 20;
int pagedown = 0;
int noofLines = 0;
int rowcount = 0;

Help()
{
    setTitle("Help");
    show();
    helpstr = new String[LINECOUNT];

    String str = "";
    try{
    DataInputStream din = new DataInputStream(new FileInputStream(
             strhelp));
     while(true)
      {
       helpstr[rowcount] = din.readLine();
       System.out.println(helpstr[rowcount]);
       rowcount++;
       if(rowcount >= 1000)
         break;
       }
   }
   catch(FileNotFoundException e){;}
   catch(IOException e){ noofLines=rowcount;}
}

public void paint(Graphics g)
{
   int rowcount1 = 0;
   int nooflinesperPage =0;
   rowcount1=40;
   nooflinesperPage = 0;
   setBackground(Color.gray);
   String str= "";
   rowcount = 66;

   if(pagedown < 0)
      {
       pagedown = 0;
      }

   if(pagedown >= rowcount)
     {
       g.setColor(Color.red);
       pagedown -=20;
     }
  
   while(nooflinesperPage < 20)
   {
    g.setColor(Color.blue);
    g.drawString(helpstr[pagedown+nooflinesperPage],40,rowcount1);//3 row adjustment
    rowcount1 += 15;
    nooflinesperPage++;
    if(rowcount >= pagedown+nooflinesperPage)
      break;

   }

}

public boolean keyDown(Event evt, int key)

{

  if(key == Event.PGUP)
  {
      str1 = "Pageup";
      pagedown -= 20;
      repaint();
  }

  if(key == Event.PGDN)
  {
        str1 = "Pagedown";
        pagedown += 20;
        repaint();
  }

  return true;
}

public boolean handleEvent(Event evt)
{

 if(evt.id == Event.WINDOW_DESTROY)
    {
          hide();
    }
 return super.handleEvent(evt);
}

}





