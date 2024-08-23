package com.karthik.hotelmanagement.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.karthik.hotelmanagement.model.PaymentDto;

@FeignClient(name = "payment-service", url = "http://localhost:8088", path = "/paymentservice")
public interface PaymentBookingFeignClient {

	@PostMapping("/user/makePaymentByUserId/{userid}/{amount}")
	PaymentDto makePaymentByUserId(@PathVariable("userid") int userid, @PathVariable("amount") int amount);

	@PostMapping("/useradmin/refundPaymentByPaymentId/{id}")
	PaymentDto refundPaymentByPaymentId(@PathVariable("id") int id);
}
