import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

import java.rmi.*;
import java.io.*;
import java.util.*;

public class LogClient
{
  public static String getReadLine() //Reads a new line from Terminal promp
  {
    String getString="";
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    try{
      getString+=in.readLine();
    }
    catch(IOException e)
    {
      System.out.println("Excepcion 'IOException'");
    }

    return getString;
  }

  public static int choose()
  {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int choose=0;

    System.out.println("Cliente de Consulta:");
    System.out.println("\t1. Sensor\n\t2.Observador\n\t3.Salir");

    try{
      choose=Integer.parseInt(in.readLine()); //read it
    }
    catch (IOException e)
    {
      System.out.println("Error al capturar opcion");
    }
    catch(NumberFormatException e)
    {
      System.out.println("No se capturo ningun numero");
    }

    return choose;
  }

  public static String[] readLogData()
  {
    String[] data={"",""};

    System.out.println("Nombre del sensor:");
    data[0]=getReadLine();
    System.out.println("Dato:");
    do{
      System.out.println("Dato (flotante):");
      data[1]=getReadLine();
      try{
        Float.valueOf(data[1]);
      }
      catch(NumberFormatException e)
      {
        System.out.println("El dato no es numero flotante");
        data[1]="ERROR";
      }
    }while(data[1].contains("ERROR"));

    return data;
  }

  public static String[] readObsData()
  {
    String[] data={"",""};

    System.out.println("Nombre del observador:");
    data[0]=getReadLine();
    System.out.println("Nombre del sensor:");
    data[1]=getReadLine();

    return data;
  }

  public static String[] menu(LogService l)
  {
    int choice=0;
    String[] message={"Exit",""};

    choice=choose();
    switch(choice)
    {
      case 1:
        message=readLogData();
        System.out.println(l.createLog(message[0], Float.parseFloat(message[1])));
        break;
      case 2: 
        message=readObsData();
        System.out.println(l.getLastLogOf(message[0], message[1]));
        break;
      case 3:
      default: System.out.println("Esta opcion no esta disponible");
      return message;
    }
    return message;
  }

   public static void main(String[] args)
   {
      try{
        // create and initialize the ORB
       ORB orb = ORB.init(args, null);
       String[] option={"",""};
	    
        // get the root naming context
        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
	        
        // Use NamingContextExt instead of NamingContext, part of the Interoperable naming Service.  
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        // resolve the Object Reference in Naming
        LogService log = LogServiceHelper.narrow(ncRef.resolve_str("LogService"));

        //System.out.println(log.sayHello("Raj"));
        //Code here
        do{
          option=menu(log);
        }while(option[0]!="Exit");
      }catch(Exception e){}
  }
}
