package com.karthik.hotelmanagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.karthik.hotelmanagement.entity.Payment;
import com.karthik.hotelmanagement.service.PaymentService;

@RestController
@RequestMapping("/paymentservice")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping("/admin/getAllPayments")
	public ResponseEntity<List<Payment>> getAllPayments() {
		List<Payment> payment = paymentService.getAllPayments();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(payment);
	}

	@GetMapping("/useradmin/getPaymentById/{id}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable("id") int id) {
		Payment payment = paymentService.getPaymentById(id);
		if (payment != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(payment);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PostMapping("/user/makePaymentByUserId/{userid}/{amount}")
	public ResponseEntity<Payment> makePaymentByUserId(@PathVariable("userid") int userid,
			@PathVariable("amount") int amount) {
		Payment paymentDto = paymentService.makePayment(userid, amount);
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentDto);
	}

	@PostMapping("/useradmin/refundPaymentByPaymentId/{id}")
	public ResponseEntity<Payment> refundPaymentByPaymentId(@PathVariable("id") int id) {
		Payment paymentDto = paymentService.refundPayment(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentDto);
	}
}
