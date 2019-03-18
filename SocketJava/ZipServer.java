import java.net.*;
import java.io.*;

public class ZipServer
{
	public static String zipServerSocket(String sport, String message)
	{
		try{
		    int port = Integer.parseInt(sport);        
		    ServerSocket s = new ServerSocket(port);

	        Socket s1=s.accept(); // Wait and accept a connection
	        // Get a communication stream associated with the socket
	        
	        InputStream s1In = s1.getInputStream();
	        DataInputStream dis = new DataInputStream(s1In);

	        // read(byte[] b, int off, int len)
	        byte[] buffer = new byte[255];
	        dis.read(buffer,0,64);
	        String st = new String (buffer);
	        System.out.println("Client: ["+st+"]");



	        OutputStream s1out = s1.getOutputStream();
	        DataOutputStream dos = new DataOutputStream (s1out);
	        // Send a string!
	        String text = message;
	         //   write(byte[] b, int off, int len)
	        dos.write(text.getBytes("utf-8"), 0, text.length());
	       
	        // Close the connection
	        dos.close();
	        dis.close();
	        s1In.close();
	        s1out.close();
	        s1.close();

	        return st=st.substring(0, st.indexOf(0));
   		}
    catch(IOException e)
		{
			System.out.println("IOException "+e.getMessage());
		}
	catch(UnsupportedOperationException e)
		{
			System.out.println("UnsupportedEncodingException "+e.getMessage());
		}

		return "ERROR";
	}

	public static String loopZipServerSocket(ServerSocket s, String message)
	{
		try{
	        Socket s1=s.accept(); // Wait and accept a connection
	        // Get a communication stream associated with the socket
	        
	        InputStream s1In = s1.getInputStream();
	        DataInputStream dis = new DataInputStream(s1In);

	        // read(byte[] b, int off, int len)
	        byte[] buffer = new byte[255];
	        dis.read(buffer,0,64);
	        String st = new String (buffer);
	        //System.out.println("Buffer: "+st);



	        OutputStream s1out = s1.getOutputStream();
	        DataOutputStream dos = new DataOutputStream (s1out);
	        // Send a string!
	        String text = message;
	         //   write(byte[] b, int off, int len)
	        dos.write(text.getBytes("utf-8"), 0, text.length());
	       
	        // Close the connection
	        dos.close();
	        dis.close();
	        s1In.close();
	        s1out.close();
	        s1.close();

	        return st=st.substring(0, st.indexOf(0));
   		}
    catch(IOException e)
		{
			System.out.println("IOException "+e.getMessage());
		}
	catch(UnsupportedOperationException e)
		{
			System.out.println("UnsupportedEncodingException "+e.getMessage());
		}

		return "ERROR";
	}

	public static void main(String args[])
	{
		if (args.length < 1)
         { System.out.println("Usage: String:args [portNumber1]");
            System.exit(1);}

        int port = Integer.parseInt(args[0]); 
        String message="";

        try{
        	ServerSocket t=new ServerSocket(port);

	        while(true)
	        {
		        message=loopZipServerSocket(t, "Recibido");

		        String [] get=Decodificador.decode(message);

		        System.out.println("Writing: [");
		        for(int i=0; i<get.length;i++)
		        	System.out.println(get[i]);
		        System.out.println("\t]\n");

		        if(get.length>1)
		        {
		        	String write="";
		        	boolean doit=true;

		        	for(int j=1; j<get.length;j++)
		        		write+=j==get.length-1?get[j]:get[j]+",";

		        	if(get.length==3)
		        		if(loopZipServerSocket(t,
		        		Codificador.codeSMFromLog(Files.findLastInCSV("data.csv", get[2]+",")))=="ERROR")
		        			doit=false;

		       		if(doit)
		       			Files.writeToCSV(get[0], write);
		    	}
		    }
		}
        catch(IOException e)
		{
			System.out.println("IOException "+e.getMessage());
		} 
	}
}
