interface Device
{
	void print(String filename);
	void clearQueue();
	int printQueue();

	void openCashier();
	float totalDebt();
	void stablishAmount(float amount);
}