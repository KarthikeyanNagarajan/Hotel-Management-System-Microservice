package com.karthik.hotelmanagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.karthik.hotelmanagement.entity.Payment;
import com.karthik.hotelmanagement.feignclient.UserPaymentFeignClient;
import com.karthik.hotelmanagement.model.WalletDto;
import com.karthik.hotelmanagement.repository.PaymentRepository;
import feign.FeignException;

@Service
public class PaymentService {

	@Autowired
	private UserPaymentFeignClient userPaymentFeignClient;

	@Autowired
	private PaymentRepository paymentRepository;

	public List<Payment> getAllPayments() {
		return paymentRepository.findAll();
	}

	public Payment getPaymentById(int id) {
		return paymentRepository.findById(id).orElse(null);
	}

	public Payment savePayment(Payment payment) {
		return paymentRepository.save(payment);
	}

	public Payment makePayment(int userid, int amount) {

		Payment payment = new Payment();
		payment.setUserid(userid);
		payment.setAmount(amount);

		boolean debited = adjustWalletBalance(userid, amount);
		if (debited) {
			payment.setStatus("Payment Success");
		} else
			payment.setStatus("Payment Failed. Insufficient Balance");

		return savePayment(payment);
	}

	public Payment refundPayment(int id) {
		Payment payment = getPaymentById(id);
		if (payment != null) {
			adjustWalletBalance(payment.getUserid(), payment.getAmount());
			payment.setStatus("Payment Refunded");
			return savePayment(payment);
		}
		return null;
	}

	public boolean adjustWalletBalance(int userId, int delta) {
		try {
			WalletDto wallet = userPaymentFeignClient.adjustBalanceByUserId(userId, delta);
			return wallet != null;
		} catch (FeignException.Conflict e) {
			return false;
		}
	}
}
