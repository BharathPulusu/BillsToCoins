package com.coins;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CoinsStoreApplicationTests {

	@LocalServerPort
	private int port;

	private String uri;

	@PostConstruct
	public void init() {
		uri = "http://localhost:" + port;
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void whenGetCoinsAll_thenOK() {
		get(uri + "/coins/all").then().assertThat().statusCode(200);
	}

	@Test
	public void whenGetInvalidMinCoinsCount_thenBadReqest() {
		get(uri + "/coins/mincoins/5000/info").then().assertThat().statusCode(400);
	}

	@Test
	public void whenGetMinCoinsInfo_thenOK() {
		get(uri + "/coins/mincoins/10/info").then().assertThat().statusCode(200);
	}

	@Test
	public void whenGetMinCoinsInfoValid_thenOK() {
		String populationiCount = get(uri + "/coins/mincoins/2/info").then().assertThat().statusCode(200).extract()
				.path("total").toString();
		BigInteger expectedCount = new BigInteger("8");
		assertThat(expectedCount.equals(new BigInteger(populationiCount)));
	}

	@Test
	public void whenCoinsReset_thenOK() {
		get(uri + "/coins/reset").then().assertThat().statusCode(200);
	}

}
