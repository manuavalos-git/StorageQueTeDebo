package ar.com.quetedebo.implementatios;

import ar.com.quetedebo.storage.PaymentHistoryStorage;
import ar.com.quetedebo.domain.PaymentRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonPaymentHistoryStorage implements PaymentHistoryStorage {
    private final File file;
    private final ObjectMapper objectMapper;

    public JsonPaymentHistoryStorage(String filePath, ObjectMapper objectMapper) {
        this.file = new File(filePath);
        this.objectMapper = objectMapper != null ? objectMapper : createDefaultObjectMapper();
    }

    public JsonPaymentHistoryStorage(String filePath) {
        this(filePath, null);
    }

    private ObjectMapper createDefaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return mapper;
    }

    @Override
    public void saveRecord(PaymentRecord record) {
        List<PaymentRecord> history = getHistory();
        history.add(record);

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, history);
        } catch (IOException e) {
            throw new RuntimeException("Error saving payment history to file: " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public List<PaymentRecord> getHistory() {
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try {
            PaymentRecord[] recordsArray = objectMapper.readValue(file, PaymentRecord[].class);
            return new ArrayList<>(Arrays.asList(recordsArray));
        } catch (IOException e) {
            throw new RuntimeException("Error reading payment history from file: " + file.getAbsolutePath(), e);
        }
    }
}
