package edu.sjsu.missingscoop.service;

import edu.sjsu.missingscoop.request.DeviceProductMappingRequest;
import edu.sjsu.missingscoop.response.DeviceProductMappingResponse;

/**
 * Interface for all services
 * @author Anushri Srinath Aithal
 *
 */
public interface MissingScoopService {

	DeviceProductMappingResponse saveDeviceProductMapping(DeviceProductMappingRequest request);

}
