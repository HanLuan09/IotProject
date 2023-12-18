package vn.edu.ptit.iot.iotproject.payload;

public class SubjectAccountResponse {
	private Long subjectId;
	private String subjectName;
	public SubjectAccountResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SubjectAccountResponse(Long subjectId, String subjectName) {
		super();
		this.subjectId = subjectId;
		this.subjectName = subjectName;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	
}
