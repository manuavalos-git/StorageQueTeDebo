package ar.com.quetedebo.implementatios;

import ar.com.quetedebo.storage.PaymentHistoryStorage;
import ar.com.quetedebo.domain.PaymentRecord;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPaymentHistoryStorage implements PaymentHistoryStorage {
    private final List<PaymentRecord> history = new ArrayList<>();

    @Override
    public void saveRecord(PaymentRecord record) {
        history.add(record);
    }

    @Override
    public List<PaymentRecord> getHistory() {
        return new ArrayList<>(history);
    }
}