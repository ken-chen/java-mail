package au.com.generic.beans;

public class CustomerCareForm {

	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String preferredContactTime;
	private String comments;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPreferredContactTime() {
		return preferredContactTime;
	}
	public void setPreferredContactTime(String preferredContactTime) {
		this.preferredContactTime = preferredContactTime;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "CustomerCareForm [firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", phone=" + phone
				+ ", preferredContactTime=" + preferredContactTime
				+ ", comments=" + comments + "]";
	}



}
