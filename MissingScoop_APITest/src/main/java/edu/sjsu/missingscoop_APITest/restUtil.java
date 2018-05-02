package edu.sjsu.missingscoop_APITest;

import java.io.IOException;
import java.util.Collection;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class restUtil {
	
	public HttpResponse doGet (String URI,Header header) throws Exception{
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(URI);
		 getRequest.addHeader(header);
		 HttpResponse response = httpClient.execute(getRequest);
		return response;
		
	}
	public HttpResponse doPost (String URI,String entity ,Header header,ContentType contentType) throws Exception{
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(URI);
		 postRequest.addHeader(header);
		 if(entity !=null){
			postRequest.setEntity(new StringEntity(entity,contentType)); 
		 }else{
			 postRequest.setEntity(new StringEntity(entity));
		 }
		 HttpResponse response = httpClient.execute(postRequest);
		return response;
		
	}
	
	
	

}
