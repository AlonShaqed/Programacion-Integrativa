public class Main
{
	public static void main(String args[])
	{
		Device printer=DeviceFactory.makeDevice("Printer");
		Device cash=DeviceFactory.makeDevice("Cashier");
		Device plotter=DeviceFactory.makeDevice("Plotter");

		printer.print("Hola.odt");
		cash.stablishAmount((float)3.44);
		System.out.println(plotter.printQueue());
	}
}