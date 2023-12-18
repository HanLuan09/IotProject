package vn.edu.ptit.iot.iotproject.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.ptit.iot.iotproject.entity.Account;
import vn.edu.ptit.iot.iotproject.entity.Student;
import vn.edu.ptit.iot.iotproject.payload.AccountLogin;
import vn.edu.ptit.iot.iotproject.payload.AttendanceResponse;
import vn.edu.ptit.iot.iotproject.payload.HistoryAttendanceResponse;
import vn.edu.ptit.iot.iotproject.payload.LoginRequest;
import vn.edu.ptit.iot.iotproject.payload.HistoryAttendanceRequest;
import vn.edu.ptit.iot.iotproject.payload.SubjectAccountResponse;
import vn.edu.ptit.iot.iotproject.payload.SubjectResponse;
import vn.edu.ptit.iot.iotproject.repository.StudentRepository;
import vn.edu.ptit.iot.iotproject.service.AccountService;
import vn.edu.ptit.iot.iotproject.service.AttendanceService;
import vn.edu.ptit.iot.iotproject.service.SubjectSemesterService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class IotController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private SubjectSemesterService subjectSemesterService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	
	@GetMapping("/account")
	public ResponseEntity<List<Account>> getUserAll(){
		List<Account> account = accountService.getAllAccount();
		return new ResponseEntity<>(account, HttpStatus.OK);
	}
	
	@GetMapping("/student")
	public ResponseEntity<List<Student>> getStudentAll(){
		List<Student> s = studentRepository.findAll();
		return new ResponseEntity<>(s, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AccountLogin> accountLogin(@RequestBody LoginRequest loginRequest) {
	    try {
	        
	        if (loginRequest == null) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        String username = loginRequest.getCode();
	        String password = loginRequest.getPassword();
	        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        AccountLogin accountIdentityAvailability = accountService.accountLogin(username, password);
	        if (accountIdentityAvailability == null) {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
	        }

	        return new ResponseEntity<>(accountIdentityAvailability, HttpStatus.OK);
	    } catch (Exception e) {
	        
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@GetMapping("/subject")
	public ResponseEntity<List<SubjectResponse>> getStudentsBySubject(@RequestParam String code){
		if (code == null) {	        
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		List<SubjectResponse> s = subjectSemesterService.getSubjectSemesterByCode(code);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}
	
	@GetMapping("/account/subject")
	public ResponseEntity<List<SubjectAccountResponse>> getAccountBySubject(@RequestParam String code){
		if (code == null) {	        
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		List<SubjectAccountResponse> s = subjectSemesterService.getSubjectAccount(code);
		return new ResponseEntity<>(s, HttpStatus.OK);
	}
	
	@GetMapping("/subject/attendance")
	public ResponseEntity<AttendanceResponse> getSubjectSemesterBySubjectId(@RequestParam String code) {
	    if (code == null) {	        
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    try {
	        Long subjectId = Long.parseLong(code);
	        AttendanceResponse a = subjectSemesterService.getSubjectSemesterById(subjectId);
	        return new ResponseEntity<>(a, HttpStatus.OK);
	    } catch (NumberFormatException e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}
	
	@PostMapping("/attendance")
    public ResponseEntity<List<HistoryAttendanceResponse>> getAttendanceByCode(@RequestBody HistoryAttendanceRequest attendanceRequest) throws ParseException{
		
    	return new ResponseEntity<>(attendanceService.getAttendance(attendanceRequest), HttpStatus.OK);
    	
    }
	
}
