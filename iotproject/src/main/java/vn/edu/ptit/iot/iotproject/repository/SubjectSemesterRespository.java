package vn.edu.ptit.iot.iotproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.edu.ptit.iot.iotproject.entity.SubjectSemester;

@Repository
public interface SubjectSemesterRespository extends JpaRepository<SubjectSemester, Long> {
	
	public List<SubjectSemester> findAllByStudentClasses_Account_Code(String code);
	
	@Query("SELECT ss FROM SubjectSemester ss WHERE ss.subjectSemesterId = :subjectSemesterId")
    public SubjectSemester findBySubjectSemesterId(@Param("subjectSemesterId") Long subjectSemesterId);
	
	@Query("SELECT ss.subject FROM SubjectSemester ss JOIN ss.schedules s WHERE s.scheduleId = :scheduleId")
	public String findBySubject(@Param("scheduleId") Long scheduleId);

	
}
