public class Plotter implements PrinterDevice
{
	int queue;

	Plotter()
	{
		queue=0;
	}

	public void printType()
	{
		System.out.println("Plotter");
	}

	public void print(String filename)
	{
		System.out.println("Printing "+filename+" in plotter now...");
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