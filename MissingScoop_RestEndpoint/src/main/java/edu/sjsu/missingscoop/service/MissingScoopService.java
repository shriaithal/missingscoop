package edu.sjsu.missingscoop.service;

import edu.sjsu.missingscoop.request.DeviceProductMappingRequest;
import edu.sjsu.missingscoop.request.GroceryListRequest;
import edu.sjsu.missingscoop.response.DeviceProductListResponse;
import edu.sjsu.missingscoop.response.DeviceProductMappingResponse;
<<<<<<< Updated upstream
import edu.sjsu.missingscoop.response.GrocerListResponse;
=======
import edu.sjsu.missingscoop.response.DeviceWeightResponse;
>>>>>>> Stashed changes

/**
 * Interface for all services
 * @author Anushri Srinath Aithal
 *
 */
public interface MissingScoopService {

	DeviceProductMappingResponse saveDeviceProductMapping(DeviceProductMappingRequest request);

	DeviceProductListResponse getDeviceProductMappingByUserName(String userName);
	
	DeviceWeightResponse getDeviceWeightByDeviceId(String deviceId);

	GrocerListResponse getGroceryListByUserName(String userName);

	GrocerListResponse saveGroceryList(GroceryListRequest request);

	GrocerListResponse removeGrocery(GroceryListRequest request);

}
