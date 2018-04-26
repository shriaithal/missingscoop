package edu.sjsu.missingscoop.response;

import java.util.List;

public class GrocerListResponse extends GenericResponse {

	private static final long serialVersionUID = 3543385351755692064L;
	private String userName;
	private List<String> groceryList;

	public GrocerListResponse() {
	}

	public GrocerListResponse(String userName, List<String> groceryList) {
		super();
		this.userName = userName;
		this.groceryList = groceryList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getGroceryList() {
		return groceryList;
	}

	public void setGroceryList(List<String> groceryList) {
		this.groceryList = groceryList;
	}

	@Override
	public String toString() {
		return "GrocerListResponse [userName=" + userName + ", groceryList=" + groceryList + "]";
	}

}
