class Cashier implements Device
{
	float total;

	public void printType()
	{
		System.out.println("Cashier");
	}

	public void openCashier()
	{
		System.out.println("Cashier open...");
	}

	public float totalDebt()
	{
		return total;
	}

	public void stablishAmount(float amount)
	{
		this.total=amount;
		System.out.println("Monto establecido en: "+this.total);
	}

	public void print(String filename){}
	public void clearQueue(){}
	public int printQueue(){return 0;}
}