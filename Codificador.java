import java.io.*;
import java.util.*;

public class Codificador
{
	public static String getReadLine(String getString) //Reads a new line from Terminal promp
	{
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

	public static int checkSum(String text, int endIndex) //Calculates the checksum of the string. An end index can be specified
	{
		endIndex=endIndex!=0?endIndex:text.length();

		int checksum=0;

		for(int i=0; i<endIndex; i++)
			checksum+=(int)text.charAt(i);

		return checksum;
	}

	public static String completeBytesOf(String character, String text, int bytes) //Fills a string to complete the total number of bytes
	{																					//required by the user
		int spaces=bytes-text.length();

		try{
			if(spaces>=0)
				for(int i=0;i<spaces; i++)
					text=character+text;
			else throw new RuntimeException("Size");
		}
		catch (RuntimeException e)
		{
			System.out.println("El tamano de (String)<"+text+"> excede los bytes permitidos ("+bytes+" < "+text.length()+")");

			return "ERROR";
		}

		return text;
	}

	public static int mainMenu()
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

	public static String getSensorMessage() //Formulates sensor message
	{
		String message="u", sensor, data;
		Calendar date=Calendar.getInstance();

		System.out.println("Actualizacion de Sensores:\nEscriba los siguientes datos como se piden:");

		do{
			sensor="sensor";
			System.out.println("Numero de sensor (#):");
			sensor=getReadLine(sensor);
			sensor=completeBytesOf(" ",sensor, 8);
		}while(sensor=="ERROR");

		message+=sensor;

		do{
			data="";
			System.out.println("Dato (flotante < 8 caracteres):");
			data=getReadLine(data);
			try{
				Float.valueOf(data);
			}
			catch(NumberFormatException e)
			{
				System.out.println("El dato no es numero flotante");
				data="ERROR";
			}
			data=completeBytesOf("0",data, 8);
		}while(data.contains("ERROR"));
		message+=data;

		message+=completeBytesOf("0",Integer.toString(date.get(Calendar.HOUR_OF_DAY)),2)+
			completeBytesOf("0",Integer.toString(date.get(Calendar.MINUTE)),2)+
			completeBytesOf("0",Integer.toString(date.get(Calendar.SECOND)),2);

		message+=completeBytesOf("0",Integer.toString(date.get(Calendar.DAY_OF_MONTH)),2)+
			completeBytesOf("0",Integer.toString(date.get(Calendar.MONTH)+1),2)+
			completeBytesOf("0",Integer.toString(date.get(Calendar.YEAR)),4);

		message+=completeBytesOf("0",Integer.toString(checkSum(message, 0)),4);

		return message;
	}

	public static String codeSMFromLog(String log)
	{
		if(log.contains("No encontrado") || log.contains("ERROR"))
			return "ERROR";

		String message="u";
		String[] pack=log.split(",");

		message+=completeBytesOf(" ", pack[0], 8);
		message+=completeBytesOf("0", pack[1], 8);
		message+=pack[2].replaceAll(":", "");
		message+=pack[3].replaceAll("-", "");

		message+=completeBytesOf("0",Integer.toString(checkSum(message, 0)),4);

		return message;
	}

	public static String getObserverMessage() //Formulates Observer message
	{
		String message="r", observer, sensor;

		do{
			observer="";
			System.out.println("Nombre del Observador (< 8 caracteres):");
			observer=getReadLine(observer);
			observer=completeBytesOf(" ",observer, 8);
		}while(observer=="ERROR");

		message+=observer;

		do{
			sensor="sensor";
			System.out.println("Numero de sensor (#):");
			sensor=getReadLine(sensor);
			sensor=completeBytesOf(" ",sensor, 8);
		}while(sensor=="ERROR");

		message+=sensor;

		message+=completeBytesOf("0",Integer.toString(checkSum(message, 0)),4);

		return message;
	}

	public static String secondMenu(String message)
	{
		int choice=0;

		choice=mainMenu();

		switch(choice)
		{
			case 1: message=getSensorMessage(); return message;
			case 2: message=getObserverMessage(); return message;
			case 3:	return "Exit";
			default: System.out.println("Esta opcion no esta disponible");
		}

		return "Exit";
	}

	public static void main (String args[]) //MAIN
	{
		String listen="";

		System.out.println(secondMenu(listen));
	}
}
