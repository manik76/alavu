/*
           Project: Alavu V2.0
           Date:    May 29, 2004, Saturday, 8:53 AM IST
           Scope:   Dialog collects channel name and log file name
           Uses:    SignalData, Statistics
*/

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.io.*;

public class StatDlg extends JFrame implements ItemListener, ActionListener{

Label  signalname1Label, signalname2Label, logname1Label, logname2Label;
Choice signalname1Choice, signalname2Choice;
Button okButton, cancelButton;
TextField logname1Text, logname2Text;
SignalData sd;
StatDlg(){
  sd = new SignalData();
  okButton              = new Button("OK");
  cancelButton          = new Button("Cancel");
  signalname1Choice     = new Choice();
  signalname2Choice     = new Choice();
  logname1Text          = new TextField(20);
  logname2Text          = new TextField(20);
  signalname1Label      = new Label("SignalName1");
  signalname2Label      = new Label("SignalName2");
  logname1Label         = new Label("LogName1");
  logname2Label         = new Label("LogName2");

  okButton.addActionListener(this);
  cancelButton.addActionListener(this);
  signalname1Choice.addItemListener(this);
  signalname2Choice.addItemListener(this);
  logname1Text.addActionListener(this);
  logname2Text.addActionListener(this);
  
  setTitle("StatisticsDialog");

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
      signalname2Choice.addItem(fieldarr[1]);

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


buildconstraints(constraints,0,2,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(signalname2Label,constraints);
getContentPane().add(signalname2Label);

buildconstraints(constraints,1,2,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(signalname2Choice,constraints);
getContentPane().add(signalname2Choice);

buildconstraints(constraints,0,3,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(logname2Label,constraints);
getContentPane().add(logname2Label);

buildconstraints(constraints,1,3,1,1,50,20);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(logname2Text,constraints);
getContentPane().add(logname2Text);

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
   sd.statsignal1 = signalname1Choice.getSelectedItem();

}

if(e.getSource().equals(signalname2Choice))
{
   sd.statsignal2 = signalname2Choice.getSelectedItem();

}

}


public void actionPerformed(ActionEvent e){

    if(e.getSource().equals(okButton))
    {
       sd.statsignal1 = signalname1Choice.getSelectedItem();
       sd.statsignal2 = signalname2Choice.getSelectedItem();
       sd.statlog1 = logname1Text.getText();
       sd.statlog2 = logname2Text.getText();
       System.out.println(sd.statsignal1+" "+sd.statlog1);
       Thread st1  = new Thread(new Statistics());
       st1.start();
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







