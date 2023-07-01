package com.coins.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coins.service.CoinsStoreService;
import com.coins.service.MinCoinsResponse;

@RestController
@RequestMapping("/coins")
public class CoinsController {

	@Autowired
	private CoinsStoreService coinsStoreService;

	@GetMapping(value = "/all")
	public ResponseEntity<Map<Double, Integer>> fetchCurrentAvailableCoinsList() {
		return new ResponseEntity<>(coinsStoreService.getDenominations(), HttpStatus.OK);
	}

	@GetMapping(value = "/mincoins/{amount}/info")
	public ResponseEntity<Object> fetchMinCoinsInfo(@PathVariable("amount") Double amount) {
		MinCoinsResponse response = coinsStoreService.fetchMinCoinsInfo(BigDecimal.valueOf(amount));
		if (response == null)
			return new ResponseEntity<>(amount + " cannot be formed with the given denominations",
					HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/reset")
	public ResponseEntity<String> resetCoins() {
		coinsStoreService.resetCoins();
		return new ResponseEntity<>("coins reset happend succesfully", HttpStatus.OK);
	}

}
