package com.karthik.hotelmanagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.karthik.hotelmanagement.entity.Payment;
import com.karthik.hotelmanagement.feignclient.UserPaymentFeignClient;
import com.karthik.hotelmanagement.model.UserDto;
import com.karthik.hotelmanagement.repository.PaymentRepository;

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

		UserDto userDto = userPaymentFeignClient.getUserById(userid);
		int balance = userDto.getWallet().getBalance();
		if (balance >= amount) {
			balance -= amount;
			userPaymentFeignClient.updateBalanceByUserId(userid, balance);
			payment.setStatus("Payment Success");
		} else
			payment.setStatus("Payment Failed. Insufficient Balance");

		return savePayment(payment);
	}

	public Payment refundPayment(int id) {
		Payment payment = getPaymentById(id);
		if (payment != null) {

			UserDto userDto = userPaymentFeignClient.getUserById(payment.getUserid());

			int balance = userDto.getWallet().getBalance();
			balance += payment.getAmount();

			userPaymentFeignClient.updateBalanceByUserId(payment.getUserid(), balance);

			payment.setStatus("Payment Refunded");

			return savePayment(payment);
		}
		return null;
	}
}
