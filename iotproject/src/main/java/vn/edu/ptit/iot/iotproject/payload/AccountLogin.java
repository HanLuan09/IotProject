package vn.edu.ptit.iot.iotproject.payload;

public class AccountLogin {
	
	private String name;
	private String token;
	private Boolean isAdmin;
	private Boolean success;
	public AccountLogin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccountLogin(String name, String token, Boolean isAdmin, Boolean success) {
		super();
		this.name = name;
		this.token = token;
		this.isAdmin = isAdmin;
		this.success = success;
	}
	public String getName() {
		return name;
	}
	public String getToken() {
		return token;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public Boolean getSuccess() {
		return success;
	}
}
