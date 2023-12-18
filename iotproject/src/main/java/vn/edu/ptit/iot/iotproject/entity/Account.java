package vn.edu.ptit.iot.iotproject.entity;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account implements Serializable {
	
	private static final long serialVersionUID = -297553281792804396L;
	
	@Id
	@Column(name = "code")
	private String code;
	
	@Column (name ="name", nullable = false)
    private String name;
	
	@Column (name ="email", nullable = false)
    private String email;
	
	@Column (name ="password", nullable = false)
    private String password;
	
	@Column (name ="img")
    private String img;

	@OneToOne(mappedBy = "account")
	@JsonIgnoreProperties("account")
	private Student student;
	
	@OneToMany(mappedBy = "account")
	private Collection<StudentClass> studentClasses;
    
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Student getStudent() {
		return student;
	}

	public String getImg() {
		return img;
	}
	
}
