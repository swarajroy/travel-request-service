package org.agiledev.learnbydoing.travelrequestservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableBinding(Sink.class)
public class TravelRequestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelRequestServiceApplication.class, args);
	}
}

@Slf4j
@Component
class ApprovalMessagesListener{

	String approvalStatus;

	@StreamListener(value = Sink.INPUT)
	public void onApprovalMessageArrival(ApprovalMessage m){
		this.approvalStatus = m.getApprovalStatus();
		log.info("approvalStatus = {}", approvalStatus);
	}


}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ApprovalMessage {
	private String approvalStatus;
}