package vn.edu.ptit.iot.iotproject.payload;

public class LoginRequest {
    private String code;
    private String password;
    
	public LoginRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginRequest(String code, String password) {
		super();
		this.code = code;
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
}

