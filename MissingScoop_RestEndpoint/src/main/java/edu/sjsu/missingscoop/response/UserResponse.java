package edu.sjsu.missingscoop.response;

/**
 * Add user response
 * @author Anushri Srinath Aithal
 *
 */
public class UserResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;

	String userName;

	public UserResponse() {
	}

	public UserResponse(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserResponse [userName=" + userName + "]";
	}
}
