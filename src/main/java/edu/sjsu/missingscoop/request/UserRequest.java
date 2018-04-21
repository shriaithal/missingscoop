package edu.sjsu.missingscoop.request;

import java.io.Serializable;

/**
 * Add user request
 * @author Anushri Srinath Aithal
 *
 */
public class UserRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	String userName;

	public UserRequest() {
	}

	public UserRequest(String userName) {
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
		return "UserRequest [userName=" + userName + "]";
	}
}
