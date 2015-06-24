/*
      Project:  Alavu V2.0
      Date:     May 29, 2004, Saturday, 8:53 AM IST
      Scope:    Collects Channel and Logname for Fourier Analysis
      Uses:     SignalData, Fft
*/
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.io.*;

public class FourDlg extends JFrame implements ItemListener, ActionListener{

Label  signalname1Label, logname1Label;
Choice signalname1Choice; 
Button okButton, cancelButton;
TextField logname1Text;
SignalData sd;
Thread fft1;

FourDlg(){

  sd = new SignalData();

  okButton              = new Button("OK");
  cancelButton          = new Button("Cancel");
  signalname1Choice     = new Choice();
  logname1Text          = new TextField(20);
  signalname1Label      = new Label("SignalName1");
  logname1Label         = new Label("LogName1");

  okButton.addActionListener(this);
  cancelButton.addActionListener(this);
  signalname1Choice.addItemListener(this);
  logname1Text.addActionListener(this);
  
  setTitle("FFTDialog");

  String record = "";
  String fieldarr[]  = new String[20];
  String field = "";
  int fieldcount=0;
  int charcount=0;

  try
  {
    DataInputStream din = new DataInputStream(
                          new FileInputStream("tbsignal.db"));
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

      signalname1Choice.addItem(fieldarr[1]);

    }

  }
  catch(FileNotFoundException e){;}
  catch(IOException e){;}


  GridBagLayout gridbag          = new GridBagLayout();
  GridBagConstraints constraints = new GridBagConstraints();

  getContentPane().setLayout(gridbag);

buildconstraints(constraints,0,0,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(signalname1Label,constraints);
getContentPane().add(signalname1Label);

buildconstraints(constraints,1,0,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(signalname1Choice,constraints);
getContentPane().add(signalname1Choice);

buildconstraints(constraints,0,1,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(logname1Label,constraints);
getContentPane().add(logname1Label);

buildconstraints(constraints,1,1,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(logname1Text,constraints);
getContentPane().add(logname1Text);

buildconstraints(constraints,0,4,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(okButton,constraints);
getContentPane().add(okButton);

buildconstraints(constraints,1,4,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(cancelButton,constraints);
getContentPane().add(cancelButton);

validate();

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


public void paint(Graphics g)
{

}

public void itemStateChanged(ItemEvent e)
{

if(e.getSource().equals(signalname1Choice))
{
   sd.foursignal1 = signalname1Choice.getSelectedItem();

}


}


public void actionPerformed(ActionEvent e){


    if(e.getSource().equals(okButton))
    {
       sd.foursignal1 = signalname1Choice.getSelectedItem();
       sd.fourlog1     = logname1Text.getText();
       fft1     = new Thread(new Fft());
       fft1.start();
       hide();
    };

    if(e.getSource().equals(cancelButton))
    {
      hide();
    };

    validate();

}


public boolean handleEvent(Event evt){

 if(evt.id == Event.WINDOW_DESTROY)
             {
                     hide();
             }
 
 return true;
}

}







