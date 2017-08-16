package org.agiledev.learnbydoing.travelrequestservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWireMock(port = 8081)
public class TravelRequestServiceApplicationTests {


	@Rule
	public StubRunnerRule rule = new StubRunnerRule()
			.downloadStub("org.agiledev.learnbydoing","approval-service")
			.workOffline(true)
			.withPort(8083);

	@Test
	public void test_should_return_all_approvals(){

		String json = "[\"Swaraj\",\"Ruchika\"]";
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/approvals"))
				.willReturn(WireMock.aResponse().withBody(json).withStatus(201)));

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8081/approvals", String.class);

		BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(201);
		BDDAssertions.then(entity.getBody()).isEqualTo(json);
	}

	@Test
	public void test_should_return_all_approvals_integration(){

		String json = "[\"Swaraj\",\"Ruchika\"]";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8082/approvals", String.class);

		BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(201);
		BDDAssertions.then(entity.getBody()).isEqualTo(json);
	}

	@Test
	public void test_should_return_all_approvals_using_stubs(){
		String json = "[\"Swaraj\",\"Ruchika\"]";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8083/approvals", String.class);

		BDDAssertions.then(entity.getStatusCodeValue()).isEqualTo(201);
		BDDAssertions.then(entity.getBody()).isEqualTo(json);
	}

}
