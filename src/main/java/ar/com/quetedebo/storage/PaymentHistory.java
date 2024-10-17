package ar.com.quetedebo.storage;

import ar.com.quetedebo.domain.PaymentRecord;
import ar.com.quetedebo.implementatios.InMemoryPaymentHistoryStorage;
import ar.com.quetedebo.implementatios.JsonPaymentHistoryStorage;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PaymentHistory implements Observer {
    private final PaymentHistoryStorage storage;

    public PaymentHistory(String storageType) {
        this.storage=getPaymentHistoryStorage(storageType);
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
        System.out.println("PAGO");
    }
}
