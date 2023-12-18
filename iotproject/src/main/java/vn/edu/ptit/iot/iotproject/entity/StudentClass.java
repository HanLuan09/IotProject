package vn.edu.ptit.iot.iotproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "studentclass")
public class StudentClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentclassid")
	private Long studentClassId;
	
	@ManyToOne
	@JoinColumn(name = "code")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "subjectsemesterid")
	private SubjectSemester subjectSemesterStudent;
	
	public StudentClass() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentClass(Long studentClassId, Account account) {
		super();
		this.studentClassId = studentClassId;
		this.account = account;
	}

	public Long getStudentClassId() {
		return studentClassId;
	}

	public Account getAccount() {
		return account;
	}
	
}
