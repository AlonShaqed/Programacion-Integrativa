import java.io.*;
import java.util.*;

public class Files
{
	public static boolean writeToCSV(String file, String line)
	{
		try{
      		FileWriter write=new FileWriter(file, true);
      		write.write(line+"\n");
      		write.close();

          return true;
     	}
     	catch (IOException e)
     	{
      		System.out.println("Error de registro"+"\n");
      		//e.printStackTrace();
    	}

      return false; 
	}

	public static String findLastInCSV(String file, String substring)
	{
		try{
      		File f=new File(file);
      		Scanner reader=new Scanner(f);
      		String data="", rec="No encontrado";

      		while(reader.hasNextLine())
      		{
        		data=reader.nextLine();

        		if(data.contains(substring))
        			rec=data;
      		}
      		reader.close();

      		return rec;

    	}
    	catch (FileNotFoundException e)
    	{
      		//e.printStackTrace();

      		return "Error en la lectura";
    	}
	}

	public static void main(String args[])
	{
		String file="test.csv";

		try{
			System.out.println(findLastInCSV(file, args[0]));
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("No se especifico cadena como argumento");
		}
	}
}