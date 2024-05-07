package com.github.springprac.respository.payments;
public interface PaymentRepository {
    Boolean savePayment(Payment paymentNew);
}