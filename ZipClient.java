import java.net.*;
import java.io.*;

public class ZipClient
{
	public static String zipClientSocket(String ipaddress, String sport, String message)
	{
		try{
			int port = Integer.parseInt(sport); 
		    String hostName = ipaddress; 

		    // Open your connection to a server
		    Socket s1 = new Socket(hostName,port);
		    // Get an input file handle from the socket and read the input

		    OutputStream s1out = s1.getOutputStream();
		    DataOutputStream dos = new DataOutputStream (s1out);
		    // Send a string!
		    String text = message;
		    
		     //   write(byte[] b, int off, int len)
		    dos.write(text.getBytes("utf-8"), 0, text.length());

		    InputStream s1In = s1.getInputStream();
		    DataInputStream dis = new DataInputStream(s1In);
		 
		   // read(byte[] b, int off, int len)
		    byte[] buffer = new byte[255];
		    dis.read(buffer,0,64);
		    String st = new String (buffer);
		    //System.out.println("Server: ["+st+"]");

		    // When done, just close the connection and exit
		    dis.close();
		    dos.close();
		    s1out.close();
		    s1In.close();
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
		if (args.length < 2)
         { System.out.println("Usage: String:args [IPAddress, portNumber1]");
            System.exit(1);}

		String message="null";
		String listen="";

		do{
			message=Codificador.secondMenu(message);
			if(message=="Exit") break;
			System.out.println("Sending...");
			zipClientSocket(args[0],args[1], message);
			if(message.charAt(0)=='r')
			{
				String got=zipClientSocket(args[0],args[1], "Wait message");
				System.out.println("got.length: "+got.length());
				String [] get=Decodificador.decode(got);

				System.out.println("Log: [");
		        for(int i=0; i<get.length;i++)
		        	System.out.println(get[i]);
		        System.out.println("\t]\n");
			}
		}while(message!="Exit");
	}
}
