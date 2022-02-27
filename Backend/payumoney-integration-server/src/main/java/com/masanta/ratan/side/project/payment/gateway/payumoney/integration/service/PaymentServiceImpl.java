package com.masanta.ratan.side.project.payment.gateway.payumoney.integration.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masanta.ratan.side.project.payment.gateway.payumoney.integration.persistence.Payment;
import com.masanta.ratan.side.project.payment.gateway.payumoney.integration.persistence.PaymentCallback;
import com.masanta.ratan.side.project.payment.gateway.payumoney.integration.persistence.PaymentDetail;
import com.masanta.ratan.side.project.payment.gateway.payumoney.integration.persistence.PaymentRepo;
import com.masanta.ratan.side.project.payment.gateway.payumoney.integration.persistence.PaymentStatus;

@Service
public class PaymentServiceImpl {
	
	@Autowired
    private PaymentRepo paymentRepository;

    public PaymentDetail proceedPayment(PaymentDetail paymentDetail) {
        PaymentUtil paymentUtil = new PaymentUtil();
        paymentDetail = paymentUtil.populatePaymentDetail(paymentDetail);
        savePaymentDetail(paymentDetail);
        return paymentDetail;
    }

    public String payuCallback(PaymentCallback paymentResponse) {
        String msg = "Transaction failed.";
        Payment payment = paymentRepository.findByTxnId(paymentResponse.getTxnid());
        if(payment != null) {
            //TODO validate the hash
            PaymentStatus paymentStatus = null;
            if(paymentResponse.getStatus().equals("failure")){
                paymentStatus = PaymentStatus.Failed;
            }else if(paymentResponse.getStatus().equals("success")) {
                paymentStatus = PaymentStatus.Success;
                msg = "Transaction success";
            }
            payment.setPaymentStatus(paymentStatus);
            payment.setMihpayId(paymentResponse.getMihpayid());
            payment.setMode(paymentResponse.getMode());
            paymentRepository.save(payment);
        }
        return msg;
    }

    private void savePaymentDetail(PaymentDetail paymentDetail) {
        Payment payment = new Payment();
        payment.setAmount(Double.parseDouble(paymentDetail.getAmount()));
        payment.setEmail(paymentDetail.getEmail());
        payment.setName(paymentDetail.getName());
        payment.setPaymentDate(new Date());
        payment.setPaymentStatus(PaymentStatus.Pending);
        payment.setPhone(paymentDetail.getPhone());
        payment.setProductInfo(paymentDetail.getProductInfo());
        payment.setTxnId(paymentDetail.getTxnId());
        paymentRepository.save(payment);
    }


}
