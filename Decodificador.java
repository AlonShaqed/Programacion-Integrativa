public class Decodificador
{
	public static String trimMessage(String message)
	{
		message=message.toLowerCase();
		message=message.trim();

		return message;
	}

	public static String[] decode(String message)
	{
		String [] update={"", "", "", "", ""};
		String [] review={"", "", ""};
		String [] devoided={"Error in message"};

		System.out.println("Decoder:"+message);

		try{
			int messageChecksum=Integer.parseInt(message.substring(message.length()-4));

			if(Codificador.checkSum(message,message.length()-4)==messageChecksum)
			{
				message=trimMessage(message);

				if(message.contains("error"))
					throw new RuntimeException("Message contains <error> susbtring");

				switch(message.charAt(0))
				{
					case 'u':
						if(message.length()==35)
						{
							update[0]="data.csv";
							update[1]=message.substring(1,9).trim();
							update[2]=message.substring(9, 17);
							update[3]=message.substring(17,19)+":"+message.substring(19,21)+":"+message.substring(21,23);
							update[4]=message.substring(23,25)+"-"+message.substring(25,27)+"-"+message.substring(27,31);

							return update;
						}
					case 'r':
						if(message.length()==21)
						{
							review[0]="transactionLog.csv";
							review[1]=message.substring(1,9).trim();
							review[2]=message.substring(9,17).trim();

							return review;
						}
					default: System.out.println("Codificacion desconocida. Mensaje descartado");
				}
			}
			else throw new RuntimeException("Checksum don't match");
		}
		catch(RuntimeException e)
		{
			System.out.println("Message Discarded:\n"+e.getMessage());
		}

		return devoided;
	}

	public static void main(String args[])
	{
		String [] str=decode(args[0]);

		for(int i=0; i<str.length; i++)
			System.out.println(str[i]);
	}
}