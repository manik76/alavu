/*

    Project: Alavu V2.0
    Date:    May 29,2004, Saturday, 8:53 AM IST
    Scope:   Display Menus and Activate Each Window
    Uses:    SignalData, Trend, Bar, Status, Mimic,
             Group, StatDlg, FourDlg, Help, About
*/

import java.applet.Applet;

import java.awt.*;

public class MainWindow extends Frame implements Runnable{

Thread thclass;

Thread thtrend, thbar, thstatus, thmimic, thgroup; 

MenuBar mnubar;

Menu mnuTrend, mnuBar, mnuMimic, mnuStatus, mnuGroup, mnuRec, mnuAnalysis;

Menu mnuHelp, mnuExit, mnuInstant;

MenuItem[] submnuTrend, submnuBar, submnuMimic, submnuStatus;

MenuItem   submnuStat,  submnuFour, submnuNurb, submnuHelp, submnuAbout;

SignalData sd;
FourDlg frdlg1;
Help hlp;
StatDlg stdlg1;
About abt;

String str = new String("Hello, World");

int trendcount =0, barcount =0, statuscount=0, mimiccount=0, count=0;

MainWindow()

{

sd           = new SignalData();

mnuTrend     = new Menu("IntervalMap");

mnuInstant   = new Menu("InstantMap");

mnuBar       = new Menu("Bar");

mnuStatus    = new Menu("Status");

mnuMimic     = new Menu("VirtualMap");

mnuGroup     = new Menu("ClassEdit");

mnuAnalysis  = new Menu("Analysis");

mnuHelp      = new Menu("Help");

mnuExit      = new Menu("Exit");

mnubar       = new MenuBar();

mnubar.add(mnuTrend);

mnubar.add(mnuInstant);

mnuInstant.add(mnuBar);

mnuInstant.add(mnuStatus);

mnubar.add(mnuMimic);

mnubar.add(mnuGroup);

mnubar.add(mnuAnalysis);

mnubar.add(mnuHelp);

mnubar.add(mnuExit);

MenuItem submnuGroup1    = new MenuItem("Group");

MenuItem submnuStat      = new MenuItem("Statistics");

MenuItem submnuFour      = new MenuItem("Fourier");

MenuItem submnuHelp      = new MenuItem("Help...");

MenuItem submnuAbout     = new MenuItem("About");

MenuItem submnuExit      = new MenuItem("Exit");

mnuGroup.add(submnuGroup1);

mnuAnalysis.add(submnuStat);

mnuAnalysis.add(submnuFour);

mnuHelp.add(submnuHelp);

mnuHelp.add(submnuAbout);

mnuExit.add(submnuExit);

for( count = 0; count < sd.signaldatacount; count+=5)
{  
   if(count == 0)
	{ 
         if(sd.signalgrouptype[count].equals("1") )
            mnuTrend.add(new MenuItem(sd.signalgroupname[count]));  
         else if(sd.signalgrouptype[count].equals("2") )
            mnuBar.add(new MenuItem(sd.signalgroupname[count]));  
         else if(sd.signalgrouptype[count].equals("3") )
            mnuStatus.add(new MenuItem(sd.signalgroupname[count]));  
         else if(sd.signalgrouptype[count].equals("4") )
            mnuMimic.add(new MenuItem(sd.signalgroupname[count]));  
        }
   else { 
         if(sd.signalgroupname[count].equals(sd.signalgroupname[count-1]))
         {
              ;
         }
         else 
         {
           if(sd.signalgrouptype[count].equals("1") )
           {
            mnuTrend.add(new MenuItem(sd.signalgroupname[count]));
            trendcount++;
           }
           else if(sd.signalgrouptype[count].equals("2"))
           {
            mnuBar.add(new MenuItem(sd.signalgroupname[count]));
            barcount++;
           }
           else if(sd.signalgrouptype[count].equals("3"))
           {
            mnuStatus.add(new MenuItem(sd.signalgroupname[count]));
            statuscount++;
           }
           else if(sd.signalgrouptype[count].equals("4"))
           {
            mnuMimic.add(new MenuItem(sd.signalgroupname[count]));
            mimiccount++;
           }

         }
      }
}

setMenuBar(mnubar);

}

public void run()
{
 show();     
 for(;;)
      {
	try{Thread.sleep(1000);}	         
	catch(InterruptedException e){}
      }
}

public void paint(Graphics g)
{
   setBackground(Color.gray);
   setTitle("Alavu V2.0");
}

public boolean action(Event evt, Object obj){
        String label = (String)obj;
	if(evt.target instanceof MenuItem)
        {
            for(count=0; count<sd.signaldatacount; count+=5)
              {
                 if(label.equals(sd.signalgroupname[count]))
                  {
                     str = sd.signalgroupname[count];
                     sd.currentsignalcount = count;
                     if(sd.signalgrouptype[count].equals("1"))
                       {
                         if(sd.signalgroupenable[count] == 0)
                           { 
                             thtrend = new Thread(new Trend());
                             thtrend.start();
                             sd.signalgroupenable[count] = 1;
                           }
                       }
                     else if(sd.signalgrouptype[count].equals("2"))
                       {
                         if(sd.signalgroupenable[count] == 0)
                           { 
                             thbar = new Thread(new Bar());
                             thbar.start();
                             sd.signalgroupenable[count] = 1;
                           }

                       }
                     else if(sd.signalgrouptype[count].equals("3"))
                       {
                         if(sd.signalgroupenable[count] == 0)
                           { 
                             thstatus = new Thread(new Status());
                             thstatus.start();
                             sd.signalgroupenable[count] = 1;
                           }
                        
                       }
                     else if(sd.signalgrouptype[count].equals("4"))
                       {
                         if(sd.signalgroupenable[count] == 0)
                           { 
                             thmimic = new Thread(new Mimic());
                             thmimic.start();
                             sd.signalgroupenable[count] = 1;
                           }
                        }
                     break;
                  }
              }
              if(label.equals("Group"))
              {
                 thgroup = new Thread(new Group());
                 thgroup.start();
              }
              if(label.equals("Fourier"))
               {
                 sd.currentsignalcount = 1;
                 frdlg1  = new FourDlg();
                 frdlg1.show();
               }
              if(label.equals("Statistics"))
               {
                 sd.currentsignalcount = 1;
                 stdlg1  = new StatDlg();
                 stdlg1.show();
               }
              if(label.equals("Help..."))
               {
                 hlp = new Help();
                 hlp.show();
               }

              if(label.equals("About"))
               {
                   abt = new About();
                   abt.setSize(300,200);
                   abt.show();
               }
              if(label.equals("Exit"))
               {
                 hide();
                 System.exit(0);
               }
         }
         else
           {
                //  hide();
           }
return true;

}

public boolean handleEvent(Event evt){
 if(evt.id == Event.WINDOW_DESTROY)
             {
                hide();
                System.exit(0);
             } 
 return super.handleEvent(evt);
}

}

