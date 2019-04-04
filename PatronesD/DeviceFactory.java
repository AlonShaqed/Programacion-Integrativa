public class DeviceFactory
{
	public static Device makeDevice(String device)
	{
		if(device.equals("Printer"))
			return new Printer();
		if(device.equals("Plotter"))
			return new Plotter();
		if(device.equals("Cashier"))
			return new Cashier();
		return new Printer();
	}
}