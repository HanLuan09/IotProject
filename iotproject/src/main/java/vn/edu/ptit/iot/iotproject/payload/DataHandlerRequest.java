package vn.edu.ptit.iot.iotproject.payload;

public class DataHandlerRequest {
	private String code;
	private String img;
	private boolean message;
	
	public DataHandlerRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DataHandlerRequest(String code, String img, Boolean message) {
		super();
		this.code = code;
		this.img = img;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public String getImg() {
		return img;
	}
	public boolean isMessage() {
		return message;
	}
	
}
