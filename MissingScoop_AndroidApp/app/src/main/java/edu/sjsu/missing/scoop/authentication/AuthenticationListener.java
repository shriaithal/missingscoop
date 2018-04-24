package edu.sjsu.missing.scoop.authentication;

/**
 * Created by Shriaithal on 4/23/2018.
 */

public interface AuthenticationListener {

    public void onSuccess(String message);
    public void onFailure(String message);
}
