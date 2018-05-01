package edu.sjsu.missingscoop_APITest;
import  edu.sjsu.missingscoop_APITest.restUtil;
import org.testng.Assert;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import javax.ws.rs.core.Response.Status;
import org.apache.http.HttpResponse;
import org.testng.annotations.Test;
/**
 * @Author MojdehKeykhanzadeh
 */
public class TestGETSenarios extends restUtil{
	private String envURL = "<environment url>";
//	private final restUtil restUtil;
	public TestGETSenarios(){
		
	}
 @Test(description= "GET /fetch/device/product?userName=?",priority=0)
 public void getDeviceProductMap() throws Exception {
	 HttpResponse response = doGet(envURL+"/fetch/device/product?userName=<username>", null);
	 int statusCode =response.getStatusLine().getStatusCode();
     Assert.assertEquals(statusCode,Status.OK.getStatusCode());
 }

}
