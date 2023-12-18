package vn.edu.ptit.iot.iotproject.payload;

import java.io.Serializable;

public class SubjectResponse implements Serializable  {
	
	private static final long serialVersionUID = -297553281792804396L;
	
	private Long subjectId;
    private String subject;
    private String semester;
    private String schoolYear;
    private int progress;
    
	public SubjectResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectResponse(Long subjectId, String subject, String semester, String schoolYear, int progress) {
		super();
		this.subjectId = subjectId;
		this.subject = subject;
		this.semester = semester;
		this.schoolYear = schoolYear;
		this.progress = progress;
	}

	public Long getSubjectId() {
		return subjectId;
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

	public int getProgress() {
		return progress;
	}
    
}
