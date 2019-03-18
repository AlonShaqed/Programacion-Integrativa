import java.rmi.*;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;

import java.io.*;
import java.util.*;

public class LogImpl extends UnicastRemoteObject  implements Log {
	public LogImpl() throws RemoteException {
		super();
	}
	
	public String createLog(String sensor, float data)
	{
		Calendar date=Calendar.getInstance();
		String u="";

		u=sensor+","+Float.toString(data);
		u+=","+Integer.toString(date.get(Calendar.HOUR_OF_DAY))+":"+Integer.toString(date.get(Calendar.MINUTE))+":"+Integer.toString(date.get(Calendar.HOUR_OF_DAY));
		u+=","+Integer.toString(date.get(Calendar.DAY_OF_MONTH))+"-"+Integer.toString(date.get(Calendar.MONTH))+"-"+Integer.toString(date.get(Calendar.YEAR));
		if(Files.writeToCSV("data.csv", u))
			return "Updated";

		return "Error in writing file";
	}

	public String getLastLogOf(String observer, String sensor)
	{
		Files.writeToCSV("transactionLog.csv", observer+","+sensor);

		return Files.findLastInCSV("data.csv",sensor+",");
	}

	public static void main(String[] args) {
		String hostName = "localhost";
		String serviceName = "LogService";

		System.setProperty("java.security.policy","file:./security.policy");
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
                    
        System.setSecurityManager(new RMISecurityManager());

		try{
			Log l = new LogImpl();
			while(true)
			{
				Naming.rebind("rmi://"+hostName+"/"+serviceName, l);
				//System.out.println("Log RMI Server is running...");
			}
		}catch(Exception e){}
	}
}
