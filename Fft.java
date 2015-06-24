/*
            Project: Alavu V2.0
            Date:    May 29, 2004, Saturday, 8:53 AM IST
            Scope:   Computes Fourier Coefficient for the
                     channel and complex number are approximated
                     to unit circle
            Uses:    SignalData
*/

import java.awt.*;

import java.io.*;

public class Fft extends Frame implements Runnable{

final int noofData         = 60;
final int pageSize         = 600;
final int heightcorrection = 50;
final int widthcorrection  = 15;
final int gridheight       = 3;
final int gridwidth        = 6;
final int noofCols         = 100;
final int noofRows         = 100;

SignalData sd1;

public int trendchannel;

public static int i;

float[]    fftarray;

float[][]  unitCircle;

int[]      redComp;

int[]      greenComp;

int[]      blueComp;

int[]      plotFft;

Button    prev, next;

int scrolldown = 0;

double ChannelX[];

double ChannelProcessX[];

int statChannelX;

String statChannelplX = "";
String statChannelphX = "";
String statChannelpuX = "";

Fft()
{

    int tempvalue = 10;

    String record = "";

    String fieldarr[]  = new String[20];

    String field = "";

    int fieldcount=0;

    int charcount=0;

    String globalchno = "";

    sd1 = new SignalData();

    Button prev = new Button("Previous");

    Button next = new Button("Next");

    fftarray    = new float[sd1.hourCount];

    unitCircle  = new float[360][10];

    redComp     = new int[10];

    greenComp   = new int[10];

    blueComp    = new int[10];

    plotFft     = new int[sd1.hourCount];

    redComp[0]=253;redComp[1]=248;redComp[2]=18;redComp[3]=17;redComp[4]=17;
    redComp[5]=254;redComp[6]=200;redComp[7] =199;redComp[8] =72;redComp[9]=138;
    greenComp[0]=40;greenComp[1]=254;greenComp[2] =252;greenComp[3] =253;greenComp[4] =47;
    greenComp[5]=16;greenComp[6]=77;greenComp[7] =196;greenComp[8] =132;greenComp[9]=71;
    blueComp[0]=17;blueComp[1]=16;blueComp[2]=24;blueComp[3]=247;blueComp[4]=253;
    blueComp[5]=254;blueComp[6]=70;blueComp[7]=71;blueComp[8]=98;blueComp[9]=199;
   
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

     if(fieldarr[1].equals(sd1.foursignal1))
         {
           statChannelX = Integer.parseInt(fieldarr[0].trim());
           statChannelplX = fieldarr[3].trim();
           statChannelphX = fieldarr[4].trim();
           statChannelpuX = fieldarr[8];
         }
     
    }
  }
  catch(FileNotFoundException e){;}
  catch(IOException e){;}

  ChannelX = new double[sd1.hourCount];
  ChannelProcessX = new double[sd1.hourCount];
   
  setBackground(Color.black); //backgroundcolor

  setTitle("Fourier Spectrum");

  float z3 = 0;

  for(int z1=0; z1<360; z1++)
    {
      for(int z2=0; z2<10; z2++)
         {
             unitCircle[z1][z2] = z1+z3;
             z3+=0.1;
         }
         z3 = 0;
    }

  setLayout(new FlowLayout(FlowLayout.CENTER));

  add(prev);

  add(next);

}

public void buildconstraints(GridBagConstraints constraint, int gridx, int gridy, int gridwidth, int gridheight,
                  int weightx, int weighty)
{

  constraint.gridx      = gridx;
  constraint.gridy      = gridy;
  constraint.gridwidth  = gridwidth;
  constraint.gridheight = gridheight;
  constraint.weightx    = weightx;
  constraint.weighty    = weighty;

};

public void convProcess()
{

   double temp =0, slope =0;

   double adcvalue = 4095;

       for(int count1=0; count1 < sd1.hourCount;count1++)
          {
               temp  = ChannelX[count1];
               slope = Integer.parseInt(statChannelphX)-Integer.parseInt(statChannelplX);
               temp  = slope * temp;
               ChannelProcessX[count1] = temp;
          } 
}

void FFT()
{

   double max = 0,tagno=1;
   double a,b,z1,z2;
   double incValue;

   double w;

   for(int count=0;count < sd1.hourCount;count++)
    {

          fftarray[count] = (float)0.0;

    }

   for(int count=0;count < sd1.hourCount;count++)
    {

     for(int count1=0; count1 < sd1.hourCount; count1++)
       {
          a  = Math.cos(2*3.14*count1*count/sd1.hourCount);
          b  = Math.sin(2*3.14*count1*count/sd1.hourCount);
          z1 = Math.sqrt(a*a + b*b); 
          z2 = Math.atan2(a,b);
          w  = unitCircle[Math.abs((int)z1)][Math.abs((int)z2)];
          fftarray[count] += ChannelProcessX[count1]*w;
        }
     }

   for(int count=0; count < sd1.hourCount; count++)
     {
         fftarray[count] = fftarray[count] / sd1.hourCount;
         if(max < fftarray[count])
            max = (int) fftarray[count];

     }


   incValue = max / 10;

   for(int count=0; count < sd1.hourCount; count++)
     {
         if(fftarray[count]>0 && fftarray[count]<=incValue)
         {
              plotFft[count] = 0;
         }
         else if(fftarray[count]>incValue && fftarray[count]<=2*incValue)
         {
              plotFft[count] = 1;
         }
         else if(fftarray[count]>2*incValue&& fftarray[count]<=3*incValue)
         {
              plotFft[count] = 2;
         }
         else if(fftarray[count]>3*incValue&& fftarray[count]<=4*incValue)
         {
              plotFft[count] = 3;
         }
         else if(fftarray[count]>4*incValue&& fftarray[count]<=5*incValue)
         {
              plotFft[count] = 4;
         }
         else if(fftarray[count]>5*incValue&& fftarray[count]<=6*incValue)
         {
              plotFft[count] = 5;
         }
         else if(fftarray[count]>6*incValue&& fftarray[count]<=7*incValue)
         {
              plotFft[count] = 6;
         }
         else if(fftarray[count]>7*incValue&& fftarray[count]<=8*incValue)
         {
              plotFft[count] = 7;
         }
         else if(fftarray[count]>8*incValue&& fftarray[count]<=9*incValue)
         {
              plotFft[count] = 8;
         }
         else if(fftarray[count]>9*incValue&& fftarray[count]<=10*incValue)
         {
              plotFft[count] = 9;
         }

     }
}

public void run()
{
 int tempvalue = 10;
 String str= new String();

try{
    DataInputStream ch1File = new DataInputStream(
                       new FileInputStream(sd1.fourlog1));
    for(int count=0; count <= statChannelX;count++)
     {
       for(int count1=0; count1 < sd1.hourCount;count1++)
          {
            sd1.foursignal1value[count][count1]=ch1File.readInt();
            if(count == statChannelX)
              {
               ChannelX[count1] = sd1.foursignal1value[count][count1];
              }

          } 
      }
    }
      catch(FileNotFoundException e){
      System.out.println("FourModule: File Not Found Error");
      }
      catch(IOException e){
      System.out.println("FourModule: IO Error");
      }
   convProcess();
   FFT();
   show();
}

public void paint(Graphics g)
{
    int rowcount , widthcount;
    int height = 0 , width = 0;
    int trendchannelinc1, trendchannelinc2;
    int countt1 = 0, countt2 = 0;

    String str = new String();

    Font fnt1 = new Font("TimesNewRoman",Font.BOLD,8);

    height = size().height;
    width  = size().width;

    g.setColor(Color.gray); // Grid Colour

    g.setFont(fnt1);

    int count1 = 100;
  
    for(rowcount = heightcorrection; rowcount <= noofRows*gridheight+heightcorrection; rowcount+= gridheight)
	{
             g.setColor(Color.gray);
             g.drawLine(widthcorrection,rowcount,widthcorrection+noofCols*gridwidth,rowcount);
             g.setColor(Color.yellow);

             if(count1%5 == 0)
             {
                g.drawString(str.valueOf(count1),widthcorrection/2,rowcount);//3 row adjustment
             }
             count1--;
	}

        count1 = scrolldown/gridwidth; //10 samples per second assumed each pixel
        //100 millisecond

    for(widthcount = widthcorrection; widthcount < noofCols*gridwidth+widthcorrection; widthcount+= gridwidth)
        {
            g.setColor(Color.gray); 
            g.drawLine(widthcount,heightcorrection,widthcount,rowcount-gridheight);
            g.setColor(Color.green);
            
            if(count1%10==0)
            {
              g.drawString(str.valueOf((count1)),widthcount,rowcount+heightcorrection/2);
            }
            count1++;
        }

    g.setColor(Color.blue);

    g.drawString("Frequency(Hz)",width/2,height-heightcorrection/8);

    g.drawString("FC",widthcorrection/4,height/3);

    g.setColor(Color.green);  
    g.drawString(str.valueOf((count1)),widthcount,rowcount+heightcorrection/2);
//    g.drawString("Frequency(Hz)",width/2,height-heightcorrection/8);
    g.setColor(Color.yellow);
//    g.drawString("FC",widthcorrection/4,height/3);
    g.setColor(Color.yellow);

    width = size().width;

    int currcount = 0;

    for(int count = scrolldown; count < pageSize/gridwidth; count++,currcount++)
      {

      if(count >= sd1.hourCount)
         break;

        for(int count2=0; count2 < plotFft[count];count2++)
        {
          g.setColor(new Color(redComp[count2],
                     greenComp[count2],
                     blueComp[count2]));

          g.fillRect(widthcorrection+currcount*5,
                     rowcount-count2*30-22,
                     4,20);
        }

   }


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

public boolean action(Event evt, Object obj)
{

 String evtname = (String) obj;

 if(evt.target instanceof Button)
 {

   if(evtname.equals("Previous"))
   {
     if(scrolldown >= pageSize)
        scrolldown -= pageSize;
   }

   if(evtname.equals("Next"))
   {
     if(scrolldown+pageSize <= sd1.hourCount)
        scrolldown += pageSize; 
   }
   repaint();
 }
 return true;
}
}





