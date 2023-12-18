package vn.edu.ptit.iot.iotproject.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.ptit.iot.iotproject.entity.Schedule;
import vn.edu.ptit.iot.iotproject.entity.StudentClass;
import vn.edu.ptit.iot.iotproject.entity.SubjectSemester;
import vn.edu.ptit.iot.iotproject.payload.AttendanceResponse;
import vn.edu.ptit.iot.iotproject.payload.SubjectAccountResponse;
import vn.edu.ptit.iot.iotproject.payload.SubjectResponse;
import vn.edu.ptit.iot.iotproject.payload.attendance.AttendanceSchedule;
import vn.edu.ptit.iot.iotproject.payload.attendance.AttendanceStudent;
import vn.edu.ptit.iot.iotproject.repository.SubjectSemesterRespository;
import vn.edu.ptit.iot.iotproject.service.SubjectSemesterService;

@Service
public class SubjectSemesterServiceImpl implements SubjectSemesterService{
	
	@Autowired
	private SubjectSemesterRespository subjectSemesterRespository;
	
	@Override
	public List<SubjectSemester> getAllSubjectSemester() {
		return subjectSemesterRespository.findAll();
	}
	
	@Override
	public String getSubject(Long id) {
		// TODO Auto-generated method stub
		return subjectSemesterRespository.findBySubject(id);
	}
	@Override
	public List<SubjectAccountResponse> getSubjectAccount(String code) {
		List<SubjectSemester> subjectSemesters = subjectSemesterRespository.findAllByStudentClasses_Account_Code(code);
		List<SubjectAccountResponse> subjects = new ArrayList<>();
		for(SubjectSemester s: subjectSemesters) {
			subjects.add(new SubjectAccountResponse(s.getSubjectSemesterId(), s.getSubject()));
		}
		
		return subjects;
	}
	
	@Override
	public List<SubjectResponse> getSubjectSemesterByCode(String code) {
	    try {
	        List<SubjectSemester> subjectSemesters = subjectSemesterRespository.findAllByStudentClasses_Account_Code(code);
	        Date currentDate = Date.valueOf(LocalDate.now());
	        List<SubjectResponse> subjectResponses = new ArrayList<>();
	        
	        for (SubjectSemester ss : subjectSemesters) {
	            List<Schedule> schedules = (List<Schedule>) ss.getSchedules();
	            long pastSchedulesCount = schedules.stream()
	                    .filter(schedule -> !schedule.getScheduleDate().after(currentDate))
	                    .count();
	            long totalSchedulesCount = schedules.size();
	            int progress = (int) Math.round(((double) pastSchedulesCount * 100) / totalSchedulesCount);

	            SubjectResponse subjectResponse = new SubjectResponse(
	                    ss.getSubjectSemesterId(),
	                    ss.getSubject(),
	                    ss.getSemester(),
	                    ss.getSchoolYear(),
	                    progress
	            );
	            subjectResponses.add(subjectResponse);
	        }

	        return subjectResponses;
	    } catch (Exception e) {
	        
	        e.printStackTrace(); 
	        return Collections.emptyList(); 
	    }
	}

	@Override
	public AttendanceResponse getSubjectSemesterById(Long id) {
	    SubjectSemester subjectSemesters = subjectSemesterRespository.findBySubjectSemesterId(id);

	    List<StudentClass> studentClasses = (List<StudentClass>) subjectSemesters.getStudentClasses();
	    List<Schedule> schedules = (List<Schedule>) subjectSemesters.getSchedules();

	    List<AttendanceStudent> listAStu = studentClasses.stream()
	            .filter(sc -> sc.getAccount().getStudent() != null)
	            .map(sc -> {
	                List<AttendanceSchedule> listASch = schedules.stream()
	                        .filter(s -> s.getAttendances() != null)
	                        .map(s -> {
	                            int status = s.getAttendances().stream()
	                                    .filter(a -> sc.getAccount().getStudent().getRfid().equals(a.getStudent().getRfid()))
	                                    .findFirst()
	                                    .map(a -> a.getAttendanceStatus())
	                                    .orElse(0);
	                            Time time = s.getAttendances().stream()
	                            		.filter(a -> a.getAttendanceTime() != null)
	                                    .findFirst()
	                                    .map(a -> a.getAttendanceTime())
	                                    .orElse(null);
	                            String img = s.getAttendances().stream()
	                            		.filter(a -> a.getAttendanceImg() != null)
	                                    .findFirst()
	                                    .map(a -> a.getAttendanceImg())
	                                    .orElse(null);
	                            return new AttendanceSchedule(s.getScheduleId(), s.getScheduleDate(), time, img, status);
	                        })
	                        .collect(Collectors.toList());

	                Collections.sort(listASch);

	                // Tính toán tổng số buổi vắng từ ngày hiện tại về các ngày trước đó
	                long totalAbsentSessions = listASch.stream()
	                        .filter(as -> as.getStatus() == 0)
	                        .filter(as -> !as.getScheduleDate().after(Date.valueOf(LocalDate.now())))
	                        .count();

	                return new AttendanceStudent(sc.getAccount().getName(),
	                        sc.getAccount().getCode(),
	                        sc.getAccount().getImg(),
	                        sc.getAccount().getStudent().getRfid(),
	                        totalAbsentSessions,
	                        listASch);
	            })
	            .collect(Collectors.toList());
	    		Collections.sort(listAStu);

	    return new AttendanceResponse(subjectSemesters.getSubject(), listAStu);
	}
	 
}
