package com.masanta.ratan.side.project.payment.gateway.payumoney.integration.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends CrudRepository<Payment, String> {

    Payment findByTxnId(String txnId);
}