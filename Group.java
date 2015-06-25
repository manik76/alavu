/*
     Project:  Alavu V2.0
     Date:     May 29, 2004, Saturday, 8:53 AM IST
     Scope:    Used for grouping of Channels
     Uses:     SignalData
*/

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.io.*;

public class Group extends JFrame implements Runnable, ItemListener, ActionListener{

Connection con,con1;
Statement stmt1,stmt2,stmt3;
ResultSet rtst1;

Button addbutton, deletebutton, insertbutton, updatebutton, cancelbutton;
List signalList, groupsignalList;
Choice formatChoice, groupformatChoice;
TextField groupsignalnameText;
Label  groupsignalnameLabel,groupformatLabel,signallistLabel,groupsignallistLabel,formatLabel;

String preresult,curresult;
boolean updateflag = false;

SignalData sd;

Group(){

  sd = new SignalData();
  addbutton             = new Button("AddList");
  deletebutton          = new Button("DeleteList");
  insertbutton          = new Button("Insert");
  updatebutton          = new Button("Update");
  cancelbutton          = new Button("Cancel");
  signalList            = new List();//300,true);
  groupsignalList       = new List();
  formatChoice          = new Choice();
  groupformatChoice     = new Choice();
  groupsignalnameText   = new TextField(20);
  groupsignalnameLabel  = new Label("Signal Group Name:");
  formatLabel           = new Label("Group Type");
  groupformatLabel      = new Label("Group Name");
  signallistLabel       = new Label("Signal List");
  groupsignallistLabel  = new Label("Group Signal List");
  addbutton.addActionListener(this);
  deletebutton.addActionListener(this);
  insertbutton.addActionListener(this);
  updatebutton.addActionListener(this);
  cancelbutton.addActionListener(this);
  signalList.addActionListener(this);
  groupsignalList.addActionListener(this);
  //formatChoice.addActionListener(this);
  //groupformatChoice.addActionListener(this);
  groupformatChoice.addItemListener(this);
  groupsignalnameText.addActionListener(this);
  setTitle("ClassEdit");

  String record = "";
  String fieldarr[]  = new String[20];
  String field = "";
  int fieldcount=0;
  int charcount=0;

  try
  {
    DataInputStream din = new DataInputStream(
                          new FileInputStream(sd.sigfilename));
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

      signalList.addItem(fieldarr[1]);
    }

  }
  catch(FileNotFoundException e){;}
  catch(IOException e){;}

  String record1 = "";
  String fieldarr1[]  = new String[30];
  String field1 = "";
  int fieldcount1=0;
  int charcount1=0;
  boolean groupswitch;

  try
  {
    DataInputStream din1 = new DataInputStream(
                          new FileInputStream(sd.siggrpfilename));
    groupswitch = true;
    while(true)
    {
      record1 = din1.readLine();

      try{
       record1.length();  
      }
      catch(NullPointerException e){break;}
      field1 ="";
      charcount1 = 0;
      fieldcount1 = 0;

      while(charcount1 < record1.length())
      {
          if(record1.charAt(charcount1) == '\n')//fielddelimiter
          {
            break;
          }
          
          if(record1.charAt(charcount1) == ' ')//fielddelimiter
          {
            fieldarr1[fieldcount1] = field1;
            field1="";
            fieldcount1++;
          }

          field1 += record1.charAt(charcount1);
          charcount1++;
      }
      if(groupswitch)
       {
         preresult = fieldarr1[0];
         groupformatChoice.addItem(preresult);
         groupswitch = false;
       }
     else
       {
         curresult = fieldarr1[0];
        if(preresult.compareTo(curresult)==0)
         {
       
         }
        else
         {
          groupformatChoice.addItem(curresult);
         };
         preresult = curresult;
        }
   }

  }
  catch(FileNotFoundException e){;}
  catch(IOException e){;}

  GridBagLayout gridbag = new GridBagLayout();
  GridBagConstraints constraints = new GridBagConstraints();

  getContentPane().setLayout(gridbag);
//    setLayout(gridbag);

buildconstraints(constraints,0,0,1,1,20,15);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(formatLabel,constraints);
getContentPane().add(formatLabel);

buildconstraints(constraints,1,0,1,1,30,15);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
formatChoice.addItem("Trend");
formatChoice.addItem("Bar");
formatChoice.addItem("Mimic");
formatChoice.addItem("Status");
gridbag.setConstraints(formatChoice,constraints);
getContentPane().add(formatChoice);

buildconstraints(constraints,3,0,1,1,20,15);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(groupsignalnameLabel,constraints);
getContentPane().add(groupsignalnameLabel);

buildconstraints(constraints,4,0,1,1,30,15);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(groupsignalnameText,constraints);
getContentPane().add(groupsignalnameText);

buildconstraints(constraints,0,1,1,1,20,15);
constraints.fill   = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(signallistLabel,constraints);
getContentPane().add(signallistLabel);

buildconstraints(constraints,1,1,1,2,20,40);
constraints.fill   = GridBagConstraints.VERTICAL;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(signalList,constraints);
getContentPane().add(signalList);

buildconstraints(constraints,2,1,1,1,20,15);
constraints.fill = GridBagConstraints.HORIZONTAL;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(addbutton,constraints);
getContentPane().add(addbutton);

buildconstraints(constraints,3,1,1,1,20,15);
constraints.fill   = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(groupsignallistLabel,constraints);
getContentPane().add(groupsignallistLabel);

buildconstraints(constraints,4,1,1,2,20,40);
constraints.fill   = GridBagConstraints.VERTICAL;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(groupsignalList,constraints);
getContentPane().add(groupsignalList);

buildconstraints(constraints,2,2,1,1,20,15);
constraints.fill = GridBagConstraints.HORIZONTAL;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(deletebutton,constraints);
getContentPane().add(deletebutton);

buildconstraints(constraints,1,3,1,1,20,15);
constraints.fill   = GridBagConstraints.HORIZONTAL;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(groupformatLabel,constraints);
getContentPane().add(groupformatLabel);

buildconstraints(constraints,2,3,1,1,40,15);
constraints.fill   = GridBagConstraints.HORIZONTAL;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(groupformatChoice,constraints);
getContentPane().add(groupformatChoice);

buildconstraints(constraints,1,4,1,1,20,15);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(insertbutton,constraints);
getContentPane().add(insertbutton);

buildconstraints(constraints,2,4,1,1,20,15);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(updatebutton,constraints);
getContentPane().add(updatebutton);

buildconstraints(constraints,3,4,1,1,20,15);
constraints.fill = GridBagConstraints.CENTER;
constraints.anchor = GridBagConstraints.CENTER;
gridbag.setConstraints(cancelbutton,constraints);
getContentPane().add(cancelbutton);

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

}

public void itemStateChanged(ItemEvent e)
{

if(e.getSource().equals(groupformatChoice))
{

  groupsignalList.removeAll();
  String querystr = "";
  int listcount = 0;
  String record1 = new String();
  String record = "";
  String fieldarr[] = new String[120];
  String field = "";
  int fieldcount = 0;
  int charcount = 0;


try{

DataInputStream din = new DataInputStream(
                      new FileInputStream(sd.siggrpfilename));

record1 = groupformatChoice.getSelectedItem();

while(true)
 {

  record = din.readLine();
  fieldcount = 0;
  charcount  = 0;
        fieldarr[2]="";
        fieldarr[3]="";
        fieldarr[4]="";
        fieldarr[5]="";
        fieldarr[6]="";



  try{
       record.length();
     }
  catch(NullPointerException e1){break;}

  while(charcount < record.length())
    {
      if(record.charAt(charcount) == ' ')
        {
         fieldarr[fieldcount] = field;
         field = "";
         fieldcount++;
        }
        field += record.charAt(charcount);
        charcount++;
    }
      fieldarr[0]=fieldarr[0].trim();
      record1 = record1.trim();

      if(record1.equals(fieldarr[0]))
      {
       
        groupsignalList.addItem(fieldarr[2]);
        groupsignalList.addItem(fieldarr[3]);
        groupsignalList.addItem(fieldarr[4]);
        groupsignalList.addItem(fieldarr[5]);
        groupsignalList.addItem(fieldarr[6]);

      

      }
      else
      {
      }
 }

}
catch(FileNotFoundException e1){;}
catch(IOException e1){;};
 
}
}

public void insertgroup(){

String querystr=new String();
String choicestr="";
int listcount = 0;
int count = 0;
String record = "";
String record1 = "";
String fieldarr[] = new String[20];
String field = "";
int fieldcount = 0;
int charcount = 0;


listcount = groupsignalList.countItems();


try{

DataInputStream din = new DataInputStream(
                      new FileInputStream(sd.siggrpfilename));

record1 = groupformatChoice.getSelectedItem();

while(true)
 {
  record = din.readLine();
  try{
       record.length();
     }
  catch(NullPointerException e){break;}

          querystr += record+" \n";

 }

}
catch(FileNotFoundException e){;}
catch(IOException e){;};

try
{
DataOutputStream dout = new DataOutputStream(
                        new FileOutputStream(sd.siggrpfilename));
querystr += groupsignalnameText.getText()+" ";
choicestr = formatChoice.getSelectedItem();
if(choicestr.compareTo("Trend")==0)
    querystr += "1";
else if(choicestr.compareTo("Bar")==0)
    querystr += "2";
else if(choicestr.compareTo("Status")==0)
    querystr += "3";
else if(choicestr.compareTo("Mimic")==0)
    querystr += "4";
querystr += "";
if(choicestr.compareTo("Bar")==0 || choicestr.compareTo("Trend")==0)
{
  for(count =0; count < listcount; count++)
    {
      querystr += groupsignalList.getItem(count)+"";
    }
}
else
{
for(count =0; count < listcount; count++)
{
  querystr +=  groupsignalList.getItem(count)+"";
  if((count+1)%5 == 0)
    {
       try{
       dout.writeBytes(querystr+" \n");
       }
       catch(IOException e){;}
       querystr = "";
       querystr += groupsignalnameText.getText()+" ";
       choicestr = formatChoice.getSelectedItem();
       if(choicestr.compareTo("Trend")==0)
         querystr += "1";
       else if(choicestr.compareTo("Bar")==0)
         querystr += "2";
       else if(choicestr.compareTo("Status")==0)
         querystr += "3";
       else if(choicestr.compareTo("Mimic")==0)
         querystr += "4";
         querystr += "";
   }
 }
}
 dout.writeBytes(querystr+" \n");
}
catch(FileNotFoundException e){;}
catch(IOException e){;}

}

public void updategroup()
{

String querystr = "";
int listcount = 0;
String record1 = "";
String record = "";
String fieldarr[] = new String[120];
String field = "";
int fieldcount = 0;
int charcount = 0;

listcount = groupsignalList.countItems();


try{

DataInputStream din = new DataInputStream(
                      new FileInputStream(sd.siggrpfilename));

record1 = groupformatChoice.getSelectedItem();

while(true)
 {
  record = din.readLine();
  fieldcount = 0;
  charcount = 0;
  try{
       record.length();
     }
  catch(NullPointerException e){break;}

  while(charcount < record.length())
    {
      if(record.charAt(charcount) == ' ')
        {
          fieldarr[fieldcount] = field;
          field = "";
          fieldcount++;
        }
        field += record.charAt(charcount);
        charcount++;
    }
   record1 = record1.trim();
   fieldarr[0] = fieldarr[0].trim();
   if(fieldarr[0].equals(record1))
      {
      }
   else
      {
          querystr += record + "\n";
      }
 }

}
catch(FileNotFoundException e){;}
catch(IOException e){;};

try
{
  DataOutputStream dout = new DataOutputStream(
                   new FileOutputStream(sd.siggrpfilename));
  dout.writeBytes(querystr);
}
catch(FileNotFoundException e){;}
catch(IOException e){;}

if(listcount != 0)
  insertgroup();

}

public boolean checkfields()
{


  String record1 = new String();

  String record = "";

  String fieldarr[] = new String[120];

  String field = "";

  int fieldcount = 0;

  int charcount = 0;

  int listcount = 0;

  boolean flag = true;

  String querystr = "",choicestr = "", grouptype = "";

  listcount = groupsignalList.countItems();

  querystr  = groupsignalnameText.getText();

  choicestr = formatChoice.getSelectedItem();

  querystr  = querystr.trim();

  if(querystr.length() == 0)
   {
     if(!updateflag)
        flag = false;
   }
  else
   {
  try{

   DataInputStream din = new DataInputStream(
                      new FileInputStream(sd.siggrpfilename));


   while(true)
    {

          record = din.readLine();
          fieldcount = 0;
          charcount  = 0;
          fieldarr[2]="";
          fieldarr[3]="";
          fieldarr[4]="";
          fieldarr[5]="";
          fieldarr[6]="";

        try{
           record.length();
           }
        catch(NullPointerException e1){break;}

        while(charcount < record.length())
         {
           if(record.charAt(charcount) == ' ')
           {
             fieldarr[fieldcount] = field;
             field = "";
             fieldcount++;
           }
             field += record.charAt(charcount);
             charcount++;
         }
      fieldarr[0]=fieldarr[0].trim();

      if(querystr.equals(fieldarr[0]))
      {
         flag = false;
         break;
      }
 }

}
catch(FileNotFoundException e1){;}
catch(IOException e1){;};

}

 if(choicestr.compareTo("Trend")==0)
    {
       if(listcount != 2)
        flag = false;
    }
 else if(choicestr.compareTo("Bar")==0)
    {
       if(listcount != 5)
        flag = false;
    }
        
 else if(choicestr.compareTo("Status")==0)
    {
       if(listcount > 100)
        flag = false;
    }
    
 else
    {
       if(listcount == 0)
        flag = false;
    }



  return flag;


}

public void actionPerformed(ActionEvent e){
    if(e.getSource().equals(addbutton))
    {
       groupsignalList.addItem(signalList.getSelectedItem());
    };
    if(e.getSource().equals(deletebutton))
    {
       groupsignalList.delItem(groupsignalList.getSelectedIndex());
    };
    if(e.getSource().equals(insertbutton))
    {
       updateflag = false;

       if(checkfields())
       {
         insertgroup();
         hide();
        }
    };               
    if(e.getSource().equals(updatebutton))
    {

      updateflag = true;

      if(checkfields())
      {
       updategroup();
       hide();
      }
      updateflag = false;
    };
    if(e.getSource().equals(cancelbutton))
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







