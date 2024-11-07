package ar.com.quetedebo.domain;

import java.time.LocalDateTime;

public class PaymentRecord {
	private String paymentMethod;
	private Float amount;
	private LocalDateTime paymentDate;
	private String addressPayment;

	public PaymentRecord() {
	}

	public PaymentRecord(String paymentMethod, Float amount, String addressPayment) {
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.paymentDate = LocalDateTime.now();
		this.addressPayment = addressPayment;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getAddressPayment() {
		return addressPayment;
	}

	public void setAddressPayment(String addressPayment) {
		this.addressPayment = addressPayment;
	}

	@Override
	public String toString() {
		return "Paid " + amount + " using " + paymentMethod + " on " + paymentDate + ". address payment: "
				+ addressPayment;
	}
}
