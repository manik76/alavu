/*
       Project:  Alavu V2.0
       Date:     May 29, 2004, Saturday, 8:53 AM IST
       Scope:    Displays About Dialog
*/

import java.awt.*;
import java.io.*;

public class About extends Frame{


About()
{
    setTitle("About");
}

public void paint(Graphics g)
{

  g.setFont(new Font("TimesNewRoman",Font.BOLD,24));
  setBackground(Color.gray);
  g.setColor(Color.orange);
  g.drawString("ALAVU V2.0",40,100);//3 row adjustment
  g.setColor(Color.green);
  g.drawString("NEUTECH",40,150);

}


public boolean handleEvent(Event evt){

 if(evt.id == Event.WINDOW_DESTROY)
    {
          hide();
    }
 return super.handleEvent(evt);
}
}





