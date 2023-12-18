package vn.edu.ptit.iot.iotproject.service;

import java.util.List;

import vn.edu.ptit.iot.iotproject.entity.Account;
import vn.edu.ptit.iot.iotproject.payload.AccountLogin;

public interface AccountService {
	public List<Account> getAllAccount();
	public AccountLogin accountLogin(String code, String password);
	
	public Account getStudentByRfid(String rfid);
}
