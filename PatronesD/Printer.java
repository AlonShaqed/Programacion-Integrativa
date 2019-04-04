public class Printer implements PrinterDevice
{
	int queue;

	Printer()
	{
		queue=0;
	}

	public void printType()
	{
		System.out.println("Printer");
	}

	public void print(String filename)
	{
		System.out.println("Printing "+filename+" now in printer...");
		this.queue++;
	}

	public void clearQueue()
	{
		this.queue=0;
	}

	public int printQueue()
	{
		return queue;
	}

	public void openCashier(){}
	public float totalDebt(){return 0;}
	public void stablishAmount(float amount){}
}