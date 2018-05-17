package edu.sjsu.missingscoop.dao;

import edu.sjsu.missingscoop.model.UserToken;

public interface UserTokenDao {

	public void saveUserToken(UserToken userToken);

	public String getToken(String userName);
}
