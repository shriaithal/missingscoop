package edu.sjsu.missingscoop_APITest;
import  edu.sjsu.missingscoop_APITest.restUtil;
import org.testng.Assert;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @Author MojdehKeykhanzadeh
 */
public class TestPOSTScenarios extends restUtil{
	private String envURL = "<environment url>";
	 private final ObjectMapper mapper = new ObjectMapper();
	 private String deviceId = null;
//	private final restUtil restUtil;
	public TestPOSTScenarios(){
		
	}
	@Test(description= "POST /map/device/product",priority=0)
	 public void saveDeviceProductMap() throws Exception {
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("userName","anushri");
		requestBody.put("deviceId", "testec2");
		requestBody.put("label", "testec2");
		requestBody.put("threshold", "4");
	    HttpResponse response = doPost(envURL+"/map/device/product",mapper.writeValueAsString(requestBody),null,ContentType.APPLICATION_JSON);
	/*  HttpEntity res =response.getEntity();
	    JSONObject obj = new JSONObject(res);
	    deviceId = obj.getString("deviceId");
	    System.out.println("deviceId "+deviceId);
	    Assert.assertEquals(deviceId , "testec2");*/
	    HttpEntity res =response.getEntity();
	    System.out.println("RESPONSE :::"+response.getEntity());
	    int statusCode =response.getStatusLine().getStatusCode();
	     Assert.assertEquals(statusCode,Status.OK.getStatusCode());
	 }
		
}
