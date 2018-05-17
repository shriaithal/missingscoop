package edu.sjsu.missing.scoop.api.request;

import java.io.Serializable;

public class UserTokenRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	String userName;
    String tokenId;

	public UserTokenRequest() {
	}

	public UserTokenRequest(String userName, String tokenId) {
		super();
		this.userName = userName;
		this.tokenId = tokenId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Override
	public String toString() {
		return "UserTokenRequest [userName=" + userName + ", tokenId=" + tokenId + "]";
	}
}
