package in.softserv.vtb.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import in.softserv.vtb.dto.LocationDTO;

@Controller
public class LocationController {
	
 

	@MessageMapping("/loc")
	@SendTo("/topic/updateLoc") 
	public LocationDTO updateLoc(LocationDTO lDTO) throws InterruptedException {
		System.out.println("before sleep");
		System.out.println(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}
	
	 	
	@MessageMapping("/updateDB")
	public LocationDTO updateDB(LocationDTO lDTO){
		System.out.println("data updating");
		System.out.println("updateDB");
		System.out.println(lDTO);
		
		UtilityImpl utility= new UtilityImpl();
		utility.setLocationInDB(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}
	
	@MessageMapping("/updateSourceOut")
	public LocationDTO updateSourceOut(LocationDTO lDTO){
		System.out.println("data updating");
		System.out.println("updateSourceOut"); 
		System.out.println(lDTO);
		
		UtilityImpl utility= new UtilityImpl();
		utility.setSourceOutInDB(lDTO);
		//Thread.sleep(2000);
		return lDTO; 
	} 
	 
	@MessageMapping("/updateDestinationIn")
	public LocationDTO updateDestinationIn(LocationDTO lDTO){ 
		System.out.println("data updating");
		System.out.println("updateDestinationIn");
		System.out.println(lDTO);
		
		UtilityImpl utility= new UtilityImpl();
		utility.setDestinationInInDB(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	} 
	
	@MessageMapping("/updatePolygonVertics") 
	public LocationDTO updatePolygonVertics(LocationDTO lDTO){
		System.out.println("data updating");
		System.out.println("updatePolygonVertics");
		System.out.println(lDTO);
		 
		UtilityImpl utility= new UtilityImpl();
		utility.setPolygonVerticsInDB(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}
	   
	@MessageMapping("/updateZoneEntryIn")
	public LocationDTO updateZoneEntryIn(LocationDTO lDTO){ 
		System.out.println("data updating");
		System.out.println("updateZoneEntryIn");
		System.out.println(lDTO);
		
		UtilityImpl utility= new UtilityImpl();
		utility.setEntryInZoneInDB(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}  
	
	@MessageMapping("/updateVehicleLatLong")
	public LocationDTO updateVehicleLatLong(LocationDTO lDTO){ 
		System.out.println("data updating");
		System.out.println("updateVehicleLatLong");
		System.out.println(lDTO);
		
		UtilityImpl utility= new UtilityImpl();
		utility.updateVehicleLatLongInDB(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}  
	
	@MessageMapping("/updateDepotEntryIn")
	public LocationDTO updateDepotEntryIn(LocationDTO lDTO){ 
		System.out.println("data updating");
		System.out.println("updateDepotEntryIn");
		System.out.println(lDTO);
		
		UtilityImpl utility= new UtilityImpl();
		utility.setEntryInDepotInDB(lDTO); 
		//Thread.sleep(2000);
		return lDTO;
	}  
	
}