package vn.edu.ptit.iot.iotproject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import vn.edu.ptit.iot.iotproject.entity.Account;
import vn.edu.ptit.iot.iotproject.payload.DataHandlerRequest;
import vn.edu.ptit.iot.iotproject.payload.mqtt.MqttSubscribeModel;
import vn.edu.ptit.iot.iotproject.service.AccountService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MqttController {

	@Autowired
	private AccountService accountService;
	
    private String flaskServerUrl = "http://127.0.0.1:6868/receive";
    
    
    @GetMapping("/receive")
    public DataHandlerRequest receiveTestDataFromFlask() {

        MqttSubscribeModel mSubscribeModel = new MqttSubscribeModel("67140226246", 1, 2);

        try {
            
            Account account = accountService.getStudentByRfid(mSubscribeModel.getMessage());

            // Set up the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Set up the request body
            HttpEntity<String> requestEntity = new HttpEntity<>(account.getCode(), headers);

            // Create RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Send POST request to Flask server with request body
            ResponseEntity<DataHandlerRequest> responseEntity = restTemplate.exchange(
                    flaskServerUrl,
                    HttpMethod.POST,
                    requestEntity,
                    DataHandlerRequest.class  // Expecting DataHandler in the response body
            );

            DataHandlerRequest dataHandler = responseEntity.getBody();

            return dataHandler;
        } catch (Exception e) {
			// TODO: handle exception
        	return null;
		}
    }
    
}

