interface PrinterDevice extends Device
{
	void print(String filename);
	void clearQueue();
	int printQueue();
}