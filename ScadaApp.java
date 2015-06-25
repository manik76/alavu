/*
    Project: Alavu V2.0
    Date:    May 29, 2004, Saturday 8:56 AM IST
    Module:  ScadaApp
    Scope:   Starting class for the package from which
             communication module is started as a separate
             thread 
*/

import java.awt.*;

public class ScadaApp
{

Thread thmainwindow, thcommunication, thsplash;

ScadaApp()
{

//  Thread thsplash = new Thread(new Splash());
//  thsplash.start();
  Thread thcommunication = new Thread(new SocClient());
  thcommunication.start();

  Thread thmainwindow = new Thread(new MainWindow());
  thmainwindow.start();


  
}

public static void main(String strg[])
{

  ScadaApp sApp = new ScadaApp();

}

}

















