package vn.edu.ptit.iot.iotproject.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vn.edu.ptit.iot.iotproject.entity.Account;
import vn.edu.ptit.iot.iotproject.entity.Attendance;
import vn.edu.ptit.iot.iotproject.entity.Schedule;
import vn.edu.ptit.iot.iotproject.entity.Student;
import vn.edu.ptit.iot.iotproject.payload.DataHandlerRequest;
import vn.edu.ptit.iot.iotproject.service.AccountService;
import vn.edu.ptit.iot.iotproject.service.AttendanceService;
import vn.edu.ptit.iot.iotproject.service.MqttService;
import vn.edu.ptit.iot.iotproject.service.ScheduleService;
import vn.edu.ptit.iot.iotproject.service.SubjectSemesterService;

@Service
public class MqttServiceImpl implements MqttService {

	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private SubjectSemesterService subjectSemesterService;
	
	private static final String FLASK_SERVER_URL = "http://127.0.0.1:6868/receive";
	private static final String MQTT_PUBLISHER_ID = "192.168.43.134";
    private static final String MQTT_SERVER_ADDRES= "tcp://192.168.43.134:1883";
    private final String[] mqttTopics = {"rfid"};
    private MqttClient mqttClient;


    public MqttServiceImpl() {
        try {
            mqttClient = new MqttClient(MQTT_SERVER_ADDRES, MQTT_PUBLISHER_ID);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setAutomaticReconnect(true);
            connOpts.setCleanSession(false);
            connOpts.setConnectionTimeout(10);
            mqttClient.connect(connOpts);

            for (String topic : mqttTopics) {
                mqttClient.subscribe(topic);
            }

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("Connection to MQTT Broker lost!");
                }

                private String rfid = "";

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) {
                    System.out.println(topic);
                    String message = new String(mqttMessage.getPayload());
                    if (topic.equals("rfid")) rfid = message;
                    try {
                        if (rfid != null) saveAtt(rfid);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    rfid = "";
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    // This method is not used in this example
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void saveAtt(String rfid) throws ParseException {
        try {
//            LocalDate localDate = LocalDate.now();
            LocalTime localTime = LocalTime.now();

//            Date date = Date.valueOf(localDate);
            Time time = Time.valueOf(localTime);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse("2023-10-25");
            Date sqlDate = new Date(utilDate.getTime());

            Account account = accountService.getStudentByRfid(rfid);
            Student student = account.getStudent();

            List<Schedule> schedules = scheduleService.getStudentScheduleForToday(account.getCode(), sqlDate);
            
            if (schedules == null) {
            	System.out.println("Ban khong co lich hoc");
            	mqttPublishRfid("0", 1, false);
                return;
            } else {
                DataHandlerRequest dataHandler = postDataHandler(account.getCode());
                if (!dataHandler.isMessage()) {
                	System.out.println("Khuon mat khong dung");
                	
                	mqttPublishRfid("1", 1, false);
                    return;
                }
                String subject = subjectSemesterService.getSubject(schedules.get(0).getScheduleId());

                String desc = account.getName() + ";" + subject;
                Attendance checkAttendance = attendanceService.checkAttendanceOfScheduleAndStudent(schedules.get(0), student);
                if(checkAttendance!= null) {
                	mqttPublishRfid("2", 1, false);
					System.out.println("Da diem danh");
                	return;
                }
                String room = "Phòng 201-A2";
                Attendance attendance = new Attendance(sqlDate, time, dataHandler.getImg(), 1, desc, room, student, schedules.get(0));
                try {
                	 attendanceService.saveAttendance(attendance);
                	 mqttPublishRfid("3", 1, false);
                	 System.out.println("Diem danh thanh cong");
				} catch (Exception e) {
					mqttPublishRfid("4", 1, false);
					System.out.println("Error");
					// TODO: handle exception
				}
               
            }
        } catch (Exception e) {
        	
            e.printStackTrace();
        }
    }

    public DataHandlerRequest postDataHandler(String code) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(code, headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<DataHandlerRequest> responseEntity = restTemplate.exchange(
                    FLASK_SERVER_URL,
                    HttpMethod.POST,
                    requestEntity,
                    DataHandlerRequest.class
            );

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return new DataHandlerRequest(); 
        }
    }
    public void mqttPublishRfid(String mess, int qos, boolean retained) throws MqttException {
    	MqttMessage mqttMessage = new MqttMessage(mess.getBytes());
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);
        mqttClient.publish("pubrfid", mqttMessage);
    }
}
