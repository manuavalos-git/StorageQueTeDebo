package ar.com.quetedebo.storage;

public class PaymentHistoryStorageFactory {
    private final Discoverer<PaymentHistoryStorage> discoverer;

    public PaymentHistoryStorageFactory(String extensionsPath) {
        discoverer = new Discoverer<>(extensionsPath);
    }

    public PaymentHistoryStorage createPaymentHistoryStorage() {
        return discoverer.buildExtensions(PaymentHistoryStorage.class).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No implementation found"));
    }

}
