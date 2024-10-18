package ar.com.quetedebo.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;

import ar.com.quetedebo.core.Debt;
import ar.com.quetedebo.core.remote.ObserverRemote;
import ar.com.quetedebo.domain.PaymentRecord;
import ar.com.quetedebo.storage.PaymentHistory;

public class HistoryObserverRemote extends UnicastRemoteObject implements ObserverRemote {
	private static final long serialVersionUID = 6716179050302800849L;
	private HistoryUI superIllegalMVCBreakingUI;

	protected HistoryObserverRemote() throws RemoteException {
		super();
		superIllegalMVCBreakingUI = new HistoryUI();
	}

	@Override
	public void update(List<Debt> debts) throws RemoteException {
		System.out.println("Notificación recibida desde Core: " + debts.toString());
		PaymentHistory paymentHistory = new PaymentHistory("json");
		for(Debt debt : debts) {
			PaymentRecord paymentRecord = new PaymentRecord();
			paymentRecord.setPaymentMethod("metodo de pago");
			paymentRecord.setAddressPayment(debt.getAddressPayment());
			paymentRecord.setAmount(debt.getAmount());
			paymentRecord.setPaymentDate(LocalDateTime.now());
			
			paymentHistory.addRecord(paymentRecord);
		}
		superIllegalMVCBreakingUI.addDebt(debts);
	}
}
