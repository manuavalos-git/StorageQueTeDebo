package ar.com.quetedebo.storage;

import ar.com.quetedebo.domain.PaymentRecord;

import java.util.List;

public interface PaymentHistoryStorage {
    void saveRecord(PaymentRecord record);
    List<PaymentRecord> getHistory();
}
