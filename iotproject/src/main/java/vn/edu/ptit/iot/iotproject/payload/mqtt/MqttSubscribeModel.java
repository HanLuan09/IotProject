package vn.edu.ptit.iot.iotproject.payload.mqtt;

public class MqttSubscribeModel {

    private String message;
    private Integer qos;
    private Integer id;

    
    public MqttSubscribeModel() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	public MqttSubscribeModel(String message, Integer qos, Integer id) {
		super();
		this.message = message;
		this.qos = qos;
		this.id = id;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
