package ar.com.quetedebo.storage;


import ar.com.quetedebo.core.QueTeDebo;
import ar.com.quetedebo.domain.PaymentRecord;
import ar.com.quetedebo.domain.RecordMapper;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PaymentHistory implements Observer {
    private final PaymentHistoryStorage storage;
    private final RecordMapper recordMapper;

    public PaymentHistory(String storagePath, QueTeDebo queTeDebo) {
        this.storage= getPaymentHistoryStorage(storagePath);
        this.recordMapper=new RecordMapper();
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

    private static PaymentHistoryStorage getPaymentHistoryStorage(String storagePath) {
        return new PaymentHistoryStorageFactory(storagePath).createPaymentHistoryStorage();
    }

    @Override
    public void update(Observable o, Object arg) {
            QueTeDebo queTeDebo = (QueTeDebo) o;
            queTeDebo.getDebts().forEach(debt ->this.addRecord(recordMapper.mapPaymentRecord(String.valueOf(arg), debt)));
    }

}
