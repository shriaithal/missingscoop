package edu.sjsu.missingscoop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amazonaws.util.CollectionUtils;

import edu.sjsu.missingscoop.dao.DeviceProductMappingDao;
import edu.sjsu.missingscoop.dao.GroceryListDao;
import edu.sjsu.missingscoop.model.DeviceProductMapping;
import edu.sjsu.missingscoop.model.GroceryList;
import edu.sjsu.missingscoop.request.DeviceProductMappingRequest;
import edu.sjsu.missingscoop.request.GroceryListRequest;
import edu.sjsu.missingscoop.response.DeviceProductListResponse;
import edu.sjsu.missingscoop.response.DeviceProductMappingResponse;
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
	GroceryListDao groceryListDao;

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
}
