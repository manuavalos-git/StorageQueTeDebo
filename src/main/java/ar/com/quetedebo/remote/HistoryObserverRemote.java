package ar.com.quetedebo.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import ar.com.quetedebo.core.Debt;
import ar.com.quetedebo.core.remote.ObserverRemote;

public class HistoryObserverRemote extends UnicastRemoteObject implements ObserverRemote {
	private static final long serialVersionUID = 6716179050302800849L;

	protected HistoryObserverRemote() throws RemoteException {
		super();
	}

	@Override
	public void update(List<Debt> debts) throws RemoteException {
		System.out.println("Notificación recibida desde Core: " + debts.toString());
	}
}
