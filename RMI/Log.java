import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Log extends Remote
{
	public String createLog(String sensor, float data) throws RemoteException;
	public String getLastLogOf(String observer, String sensor) throws RemoteException;
}