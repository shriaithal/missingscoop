package edu.sjsu.missingscoop.model;

public class Notification {

	String tokenId;
	String productName;

	public Notification(String tokenId, String productName) {
		super();
		this.tokenId = tokenId;
		this.productName = productName;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
