package ar.com.quetedebo.storage;

import ar.com.quetedebo.core.Debt;
import ar.com.quetedebo.core.QueTeDebo;
import ar.com.quetedebo.domain.PaymentRecord;
import ar.com.quetedebo.implementatios.InMemoryPaymentHistoryStorage;
import ar.com.quetedebo.implementatios.JsonPaymentHistoryStorage;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PaymentHistory implements Observer {
    private final PaymentHistoryStorage storage;

    public PaymentHistory(String storageType, QueTeDebo queTeDebo) {
        this.storage=getPaymentHistoryStorage(storageType);
        queTeDebo.addObserver(this);
    }

    public void addRecord(PaymentRecord record) {
        storage.saveRecord(record);
    }

    public List<PaymentRecord> getHistory() {
        return storage.getHistory();
    }

    public void printHistory() {
        List<PaymentRecord> history = getHistory();
        if (history.isEmpty()) {
            System.out.println("No payment history available.");
        } else {
            for (PaymentRecord record : history) {
                System.out.println(record);
            }
        }
    }

    private PaymentHistoryStorage getPaymentHistoryStorage(String storageType) {
        switch (storageType.toLowerCase()) {
            case "json":
                return new JsonPaymentHistoryStorage("src/main/resources/paymentHistory.json");
            case "memory":
                return new InMemoryPaymentHistoryStorage();
            default:
                throw new IllegalArgumentException("Invalid storage type: " + storageType);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof List<?>) {
            List<?> objList = (List<?>) arg;

            for (Object obj : objList) {
                if (obj instanceof Debt) {
                    Debt debt = (Debt) obj;
                    System.out.println(debt);
                }
            }
        } else {
            System.out.println("El argumento no es una lista de deudas.");
        }
    }
}
