import java.io.*;
import java.util.*;

public class LogServiceImpl extends LogServicePOA {
	public LogServiceImpl() {
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
}
