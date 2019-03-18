import java.rmi.*;
import java.io.*;
import java.util.*;

public class RMIClient {
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

	public static String[] menu(Log l)
	{
		int choice=0;
		String[] message={"Exit",""};

		choice=choose();
		try{
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
		}
		catch(RemoteException e)
		{
			System.out.println("Conexion remota fallo");
		}
		return message;
	}

	public static void main(String[] args) {
		String hostName = "localhost";
		String serviceName = "LogService";
		String[] option={"",""};
		//String who = "Alon";

		System.setProperty("java.security.policy","file:./security.policy");
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
                    
        System.setSecurityManager(new RMISecurityManager());

		try{
		    Log l = (Log)Naming.lookup("rmi://"+hostName+"/"+serviceName);
		    //write code here!!!
		    do{
		    	option=menu(l);
		    }while(option[0]!="Exit");

		}catch(Exception e){
		    e.printStackTrace();
		}
	}
}
