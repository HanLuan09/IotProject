package vn.edu.ptit.iot.iotproject.payload.mqtt;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MqttPublishModel {

    @NotNull
    @Size(min = 1,max = 255)
    private String topic;

    @NotNull
    @Size(min = 1,max = 255)
    private String message;

    @NotNull
    private Boolean retained;

    @NotNull
    private Integer qos;

    
    public MqttPublishModel() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	public MqttPublishModel(String topic, String message, Boolean retained, Integer qos) {
		super();
		this.topic = topic;
		this.message = message;
		this.retained = retained;
		this.qos = qos;
	}

	public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRetained() {
        return retained;
    }

    public void setRetained(Boolean retained) {
        this.retained = retained;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }
}
