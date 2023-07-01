package com.coins.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class CoinsStoreService {

	int coinTypesCount;
	static double[] denos = { 0.01, 0.05, 0.10, 0.25 };

	private Map<Double, Integer> denominations;
	private Map<Double, Integer> initialDenominations;

	@PostConstruct
	public void display() {
		initialDenominations = new HashMap<>();
		initialDenominations.putAll(denominations);
	}
	
	public void resetCoins() {
		this.denominations.clear();
		this.denominations.putAll(this.initialDenominations);
	}

	public MinCoinsResponse fetchMinCoinsInfo(BigDecimal value) {
		BigDecimal requestedAmount = BigDecimal.valueOf(value.doubleValue());
		int counter = 0;
		Map<Double, Integer> currMap = new HashMap<>();

		for (int i = denos.length - 1; i >= 0; i--) {
			while (value.doubleValue() >= denos[i] && denominations.get(denos[i]) > 0) {
				value = value.subtract(BigDecimal.valueOf(denos[i]));				
				counter++;
				denominations.put(denos[i], denominations.get(denos[i]) - 1);

				if (currMap.containsKey(denos[i])) {
					currMap.put(denos[i], currMap.get(denos[i]) + 1);
				} else {
					currMap.put(denos[i], 1);
				}

			}
			if (value.doubleValue() == 0.0) {
				break;
			}
		}
		if (value.doubleValue() == 0.0) {
			MinCoinsResponse minCoinsResponse = new MinCoinsResponse();
			minCoinsResponse.setCoins(currMap);
			minCoinsResponse.setTotal(counter);
			minCoinsResponse.setAmount(requestedAmount.doubleValue());
			return minCoinsResponse;
		} else {
			for (Map.Entry<Double, Integer> entry : currMap.entrySet()) {
				Double denomination = entry.getKey();
				Integer count = entry.getValue();
				denominations.put(denomination, denominations.get(denomination) + count);
			}
			return null;
		}
	}

	public Map<Double, Integer> getDenominations() {
		return denominations;
	}

	public void setDenominations(Map<Double, Integer> denominations) {
		this.denominations = denominations;
	}
}

/*
 * 
 * 
 * // m is size of coins array // (number of different coins) static int
 * minCoins(int coins[], int m, int V) { // table[i] will be storing // the
 * minimum number of coins // required for i value. So // table[V] will have
 * result int table[] = new int[V + 1];
 * 
 * // Base case (If given value V is 0) table[0] = 0;
 * 
 * // Initialize all table values as Infinite for (int i = 1; i <= V; i++)
 * table[i] = Integer.MAX_VALUE;
 * 
 * // Compute minimum coins required for all // values from 1 to V for (int i =
 * 1; i <= V; i++) { // Go through all coins smaller than i for (int j = 0; j <
 * m; j++) if (coins[j] <= i) { int sub_res = table[i - coins[j]]; if (sub_res
 * != Integer.MAX_VALUE && sub_res + 1 < table[i]) table[i] = sub_res + 1;
 * 
 * 
 * }
 * 
 * }
 * 
 * if(table[V]==Integer.MAX_VALUE) return -1;
 * 
 * return table[V];
 * 
 * }
 * 
 * 
 */