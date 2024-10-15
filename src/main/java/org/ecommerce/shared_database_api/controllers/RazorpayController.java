package org.ecommerce.shared_database_api.controllers;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.ecommerce.shared_database_api.dto.*;
import org.ecommerce.shared_database_api.models.JsonDataModel;
import org.ecommerce.shared_database_api.services.CheckoutService;
import org.ecommerce.shared_database_api.services.JsonFileReader;
import org.ecommerce.shared_database_api.services.OrderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
public class RazorpayController {



	private final JsonFileReader jsonFileReader;

	private final CheckoutService checkoutService;

	private final OrderService orderService;



	public RazorpayController(CheckoutService checkoutService,JsonFileReader jsonFileReader,OrderService orderService) {
		this.checkoutService = checkoutService;
		this.jsonFileReader = jsonFileReader;
		this.orderService = orderService;
	}



	@PostMapping("/create_order")
	//@RequestMapping(path = "/createOrder", method = RequestMethod.POST)
	public RozerPayOderResponseModelDTO createOrder(@RequestBody Purchase purchase) {
		RozerPayOderResponseModelDTO response = new RozerPayOderResponseModelDTO();
		try {

			JsonDataModel jsonDataModel = jsonFileReader.readJsonFile();

			String SECRET_KEY1=jsonDataModel.getKeyId();
			String SECRET_ID1 =jsonDataModel.getKetSecret();

			//JsonDataModel jsonDataModel = null;
			RazorpayClient client = new RazorpayClient(jsonDataModel.getKeyId(), jsonDataModel.getKetSecret());

			Double totalPrice = purchase.getOrder().getTotalPrice();

			//client = new RazorpayClient(SECRET_ID2, SECRET_KEY2);
			Order order = createRazorPayOrder(totalPrice,client);
			System.out.println("---------------------------");
			String orderId = (String) order.get("id");
			System.out.println("Order ID: " + orderId);
			System.out.println("---------------------------");
			response.setRazorpayOrderId(orderId);
			response.setApplicationFee("" + totalPrice);

			response.setSecretKey(SECRET_KEY1);
			response.setSecretId(SECRET_ID1);
			response.setPgName("razor1");

			purchase.getOrder().setRazorpayOrderId(orderId);

			saveOrderDb(purchase);

			return response;
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}


	public PurchaseResponse saveOrderDb(Purchase purchase){
		PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

		return purchaseResponse;
	}


	private Order createRazorPayOrder(Double amount, RazorpayClient client) throws RazorpayException {

		//convert amount in paise
		amount=amount*100.00;

		JSONObject options = new JSONObject();
		options.put("amount", amount);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		options.put("payment_capture", 1); // You can enable this if you want to do Auto Capture.
		return client.orders.create(options);
	}




	@PostMapping("/update_payment_status")
	//@RequestMapping(path = "/createOrder", method = RequestMethod.POST)
	public RozerPayPaymentResponseModelDTO updatePaymentStatus(@RequestBody RozerPayPaymentRequestModelDTO rozerPayPaymentRequestModelDTO) {
		RozerPayPaymentResponseModelDTO response = new RozerPayPaymentResponseModelDTO();
		String s = orderService.updateOrderPaymentStatus(rozerPayPaymentRequestModelDTO);
		response.setUpdateStatus(s);
		return response;

	}


}
