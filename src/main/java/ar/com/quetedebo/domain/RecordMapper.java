package ar.com.quetedebo.domain;

import ar.com.quetedebo.core.Debt;

public class RecordMapper {
   public PaymentRecord mapPaymentRecord(String paymentMethodname,Debt debt){
       return new PaymentRecord(paymentMethodname, debt.getAmount(), debt.getAddressPayment());
   }
}
