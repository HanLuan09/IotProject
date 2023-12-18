package vn.edu.ptit.iot.iotproject.entity;

import java.util.Collection;

import jakarta.persistence.*;

@Entity
@Table(name = "subjectsemester", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"subject", "semester", "schoolyear"})
})
public class SubjectSemester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subjectsemesterid")
    private Long subjectSemesterId;
    
    @Column(name = "subject", nullable = false)
    private String subject; // Môn học
    
    @Column(name = "semester", nullable = false)
    private String semester; // Kỳ học
    
    @Column(name = "schoolyear", nullable = false)
    private String schoolYear;
    
    @OneToMany(mappedBy = "subjectSemesterStudent")
	private Collection<StudentClass> studentClasses;
    
    @OneToMany(mappedBy = "subjectSemester")
	private Collection<Schedule> schedules;
    
	public SubjectSemester() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectSemester(Long subjectSemesterId, String subject, String semester, String schoolYear) {
		super();
		this.subjectSemesterId = subjectSemesterId;
		this.subject = subject;
		this.semester = semester;
		this.schoolYear = schoolYear;
	}

	public Long getSubjectSemesterId() {
		return subjectSemesterId;
	}

	public String getSubject() {
		return subject;
	}

	public String getSemester() {
		return semester;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public Collection<StudentClass> getStudentClasses() {
		return studentClasses;
	}

	public Collection<Schedule> getSchedules() {
		return schedules;
	}
	
}
