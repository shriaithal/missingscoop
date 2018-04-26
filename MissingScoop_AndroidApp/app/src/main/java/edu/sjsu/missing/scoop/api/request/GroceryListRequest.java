package edu.sjsu.missing.scoop.api.request;

import java.io.Serializable;

public class GroceryListRequest implements Serializable {

	private static final long serialVersionUID = 1L;
    private String userName;
	private String grocery;

	public GroceryListRequest() {
	}

	public GroceryListRequest(String userName, String grocery) {
		super();
		this.userName = userName;
		this.grocery = grocery;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGrocery() {
		return grocery;
	}

	public void setGrocery(String grocery) {
		this.grocery = grocery;
	}

	@Override
	public String toString() {
		return "GroceryListRequest [userName=" + userName + ", grocery=" + grocery + "]";
	}

}
