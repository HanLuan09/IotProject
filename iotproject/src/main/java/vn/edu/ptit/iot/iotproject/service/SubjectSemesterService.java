package vn.edu.ptit.iot.iotproject.service;

import java.util.List;
import vn.edu.ptit.iot.iotproject.entity.SubjectSemester;
import vn.edu.ptit.iot.iotproject.payload.AttendanceResponse;
import vn.edu.ptit.iot.iotproject.payload.SubjectAccountResponse;
import vn.edu.ptit.iot.iotproject.payload.SubjectResponse;

public interface SubjectSemesterService {
	List<SubjectSemester> getAllSubjectSemester();
	
	List<SubjectResponse>getSubjectSemesterByCode(String code);
	
	public AttendanceResponse getSubjectSemesterById(Long id);
	
	public String getSubject(Long id);
	
	public List<SubjectAccountResponse> getSubjectAccount(String code);
}
