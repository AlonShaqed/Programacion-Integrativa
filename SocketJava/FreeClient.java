public class FreeClient
{
	public static void main(String args[])
	{
		if (args.length < 2)
         { System.out.println("Usage: String:args [IPAddress, portNumber1]");
            System.exit(1);}
        String send="";

        while(true)
        {
        	send="";
        	System.out.println("Entre texto:");
        	send=Codificador.getReadLine(send);
        	send+=Codificador.completeBytesOf("0",Integer.toString(Codificador.checkSum(send, 0)),4);

        	ZipClient.zipClientSocket(args[0],args[1], send);
        }
	}
}
