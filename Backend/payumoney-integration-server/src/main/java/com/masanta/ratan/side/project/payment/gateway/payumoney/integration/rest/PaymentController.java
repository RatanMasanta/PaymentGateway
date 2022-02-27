package com.masanta.ratan.side.project.payment.gateway.payumoney.integration.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.masanta.ratan.side.project.payment.gateway.payumoney.integration.persistence.PaymentCallback;
import com.masanta.ratan.side.project.payment.gateway.payumoney.integration.persistence.PaymentDetail;
import com.masanta.ratan.side.project.payment.gateway.payumoney.integration.persistence.PaymentMode;
import com.masanta.ratan.side.project.payment.gateway.payumoney.integration.service.PaymentServiceImpl;

@Controller
@RequestMapping("/payment")
public class PaymentController {


	@Autowired
	private PaymentServiceImpl paymentService;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/payment-details")
	public @ResponseBody PaymentDetail proceedPayment(@RequestBody PaymentDetail paymentDetail){
		return paymentService.proceedPayment(paymentDetail);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(path = "/payment-response", method = RequestMethod.POST)
	public @ResponseBody String payuCallback(@RequestParam String mihpayid, @RequestParam String status, @RequestParam PaymentMode mode, @RequestParam String txnid, @RequestParam String hash){
		PaymentCallback paymentCallback = new PaymentCallback();
		paymentCallback.setMihpayid(mihpayid);
		paymentCallback.setTxnid(txnid);
		paymentCallback.setMode(mode);
		paymentCallback.setHash(hash);
		paymentCallback.setStatus(status);
		return paymentService.payuCallback(paymentCallback);
	}


}
