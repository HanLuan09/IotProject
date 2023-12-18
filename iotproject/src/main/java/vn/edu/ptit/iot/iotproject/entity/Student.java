package vn.edu.ptit.iot.iotproject.entity;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student implements Serializable {
	
	private static final long serialVersionUID = -297553281792804396L;
	
	@Id
	@Column(name = "rfid")
	private String rfid;
	
	@OneToOne
	@JsonIgnoreProperties("student")
    @JoinColumn(name = "studentcode", referencedColumnName = "code", unique = true, nullable = false)
    private Account account;

	@OneToMany(mappedBy = "studentAttendance")
	private Collection<Attendance> attendances;
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(String rfid) {
		super();
		this.rfid = rfid;
	}

	public String getRfid() {
		return rfid;
	}
	
}
