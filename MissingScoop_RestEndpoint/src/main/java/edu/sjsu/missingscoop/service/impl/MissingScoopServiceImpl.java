package edu.sjsu.missingscoop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amazonaws.util.CollectionUtils;

import edu.sjsu.missingscoop.dao.DeviceProductMappingDao;
import edu.sjsu.missingscoop.dao.DeviceWeightDao;
import edu.sjsu.missingscoop.dao.GroceryListDao;
import edu.sjsu.missingscoop.dao.UserTokenDao;
import edu.sjsu.missingscoop.model.DeviceProductMapping;
import edu.sjsu.missingscoop.model.GroceryList;
import edu.sjsu.missingscoop.model.UserToken;
import edu.sjsu.missingscoop.request.DeviceProductMappingRequest;
import edu.sjsu.missingscoop.request.GroceryListRequest;
import edu.sjsu.missingscoop.request.UserTokenRequest;
import edu.sjsu.missingscoop.response.DeviceProductListResponse;
import edu.sjsu.missingscoop.response.DeviceProductMappingResponse;
import edu.sjsu.missingscoop.response.DeviceWeightResponse;
import edu.sjsu.missingscoop.response.GenericResponse;
import edu.sjsu.missingscoop.response.GrocerListResponse;
import edu.sjsu.missingscoop.service.MissingScoopService;

/**
 * Business service class for all APIs
 * 
 * @author Anushri Srinath Aithal
 *
 */
@Service
public class MissingScoopServiceImpl implements MissingScoopService {

	private final String SUCCESS = "SUCCESS";

	@Autowired
	DeviceProductMappingDao deviceProductMappingDao;

	@Autowired
	DeviceWeightDao deviceWeightDao;

	@Autowired
	GroceryListDao groceryListDao;

	@Autowired
	UserTokenDao userTokenDao;

	@Override
	public DeviceProductMappingResponse saveDeviceProductMapping(DeviceProductMappingRequest request) {
		DeviceProductMapping deviceProductMap = new DeviceProductMapping();
		deviceProductMap.setDeviceId(request.getDeviceId());
		deviceProductMap.setLabel(request.getLabel());
		deviceProductMap.setThreshold(request.getThreshold());
		deviceProductMap.setUserName(request.getUserName());

		deviceProductMappingDao.save(deviceProductMap);

		DeviceProductMappingResponse response = new DeviceProductMappingResponse(deviceProductMap.getDeviceId(),
				deviceProductMap.getLabel(), deviceProductMap.getThreshold());
		response.setMessage(SUCCESS);
		response.setStatus(HttpStatus.OK.toString());
		return response;
	}

	@Override
	public DeviceProductListResponse getDeviceProductMappingByUserName(String userName) {
		List<DeviceProductMappingResponse> response = new ArrayList<>();

		List<DeviceProductMapping> deviceProductMappingList = deviceProductMappingDao
				.getDeviceProductMappingByUserName(userName);

		if (!CollectionUtils.isNullOrEmpty(deviceProductMappingList)) {
			for (DeviceProductMapping deviceProductMap : deviceProductMappingList) {
				DeviceProductMappingResponse deviceProductMapResponse = new DeviceProductMappingResponse(
						deviceProductMap.getDeviceId(), deviceProductMap.getLabel(), deviceProductMap.getThreshold());
				deviceProductMapResponse.setMessage(SUCCESS);
				deviceProductMapResponse.setStatus(HttpStatus.OK.toString());
				response.add(deviceProductMapResponse);
			}
		}

		DeviceProductListResponse returnVal = new DeviceProductListResponse(response);
		return returnVal;
	}

	@Override
	public GrocerListResponse getGroceryListByUserName(String userName) {
		GrocerListResponse response = new GrocerListResponse();

		GroceryList grocery = groceryListDao.getGroceriesByUserName(userName);
		if (null != grocery) {
			response.setUserName(grocery.getUserName());
			response.setGroceryList(grocery.getGrocery());
			response.setMessage(SUCCESS);
		} else {
			response.setMessage("No grocery found");
		}

		return response;
	}

	@Override
	public GrocerListResponse saveGroceryList(GroceryListRequest request) {
		List<String> grocery = new ArrayList<>();
		grocery.add(request.getGrocery());

		GroceryList groceryList = new GroceryList();
		groceryList.setUserName(request.getUserName());
		groceryList.setGrocery(grocery);
		groceryListDao.save(groceryList);
		return getGroceryListByUserName(request.getUserName());
	}

	@Override
	public GrocerListResponse removeGrocery(GroceryListRequest request) {
		GrocerListResponse response = new GrocerListResponse();
		GroceryList groceryList = groceryListDao.removeGrocery(request.getUserName(), request.getGrocery());
		if (null != groceryList) {
			response.setUserName(groceryList.getUserName());
			response.setGroceryList(groceryList.getGrocery());
			response.setMessage(SUCCESS);
		}
		return response;
	}

	public DeviceWeightResponse getDeviceWeightByDeviceId(String deviceId) {
		// TODO Auto-generated method stub

		DeviceWeightResponse deviceWeight = deviceWeightDao.getDeviceWeightByDeviceId(deviceId);

		return deviceWeight;

	}

	@Override
	public GenericResponse saveUserToken(UserTokenRequest request) {
		UserToken userToken = new UserToken();
		userToken.setUserName(request.getUserName());
		userToken.setTokenId(request.getTokenId());

		userTokenDao.saveUserToken(userToken);

		GenericResponse response = new GenericResponse();
		response.setMessage(SUCCESS);
		response.setStatus(HttpStatus.OK.toString());

		return response;
	}

	/*
	 * public CompletableFuture<String> send() {
	 * 
	 * try { String FIREBASE_SERVER_KEY = ""; String FIREBASE_API_URL =
	 * "https://fcm.googleapis.com/fcm/send";
	 * 
	 * RestTemplate restTemplate = new RestTemplate();
	 * 
	 * JSONObject body = new JSONObject(); body.put("to",
	 * "fAfnsWOukRE:APA91bG3sqqIPuo2ZK9fbrT3Y2mBAKAoJaJ2375-9hk60CnxUVEStlRvu5DRxhC5xssTrlL1MIuCN3jHr9hZP_ZM8c5TSSkECqunrAd6Z3FPer7OpUo-P2Gzz0vlzDGs1OSV_5iDM2U9"
	 * ); body.put("priority", "high");
	 * 
	 * JSONObject notification = new JSONObject(); notification.put("body",
	 * "body string here"); notification.put("title", "title string here");
	 * 
	 * JSONObject data = new JSONObject(); data.put("key1", "value1");
	 * data.put("key2", "value2");
	 * 
	 * body.put("notification", notification); body.put("data", data);
	 * 
	 * HttpEntity<String> entity = new HttpEntity<>(body.toString());
	 * 
	 * ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
	 * interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" +
	 * FIREBASE_SERVER_KEY)); interceptors.add(new
	 * HeaderRequestInterceptor("Content-Type", "application/json"));
	 * restTemplate.setInterceptors(interceptors);
	 * 
	 * String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL,
	 * entity, String.class);
	 * 
	 * return CompletableFuture.completedFuture(firebaseResponse); } catch
	 * (Exception e) { e.printStackTrace(); } return null; }
	 */
}
