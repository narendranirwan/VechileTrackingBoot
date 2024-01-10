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
		UtilityImpl utility= new UtilityImpl();
		utility.updateVehicleLatLongInDB(lDTO);
		utility.setSourceOutDestinationInDB(lDTO);
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
		System.out.println("data Zone Inserting");
		System.out.println("updatePolygonVertics");
		System.out.println(lDTO);
		 
		UtilityImpl utility= new UtilityImpl();
		utility.setPolygonVerticsInDB(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}
	
	@MessageMapping("/updateZonePolygonVertics") 
	public LocationDTO updateZonePolygonVertics(LocationDTO lDTO){
		System.out.println("data zone Updating");
		System.out.println("updateZonePolygonVertics");
		System.out.println(lDTO);
		 
		UtilityImpl utility= new UtilityImpl();
		utility.updateZonePolygonVerticsInDB(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}
	
	@MessageMapping("/insertDepotIntoDB") 
	public LocationDTO insertDepotIntoDB(LocationDTO lDTO){
		System.out.println("Depot insert into DB");
		
		System.out.println(lDTO);
		 
		UtilityImpl utility= new UtilityImpl();
		utility.setDepotIntoDB(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}
	
	@MessageMapping("/updateDepotIntoDB") 
	public LocationDTO updateDepotIntoDB(LocationDTO lDTO){
		System.out.println("Depot update into DB");
		
		System.out.println(lDTO);
		 
		UtilityImpl utility= new UtilityImpl();
		utility.updateDepotIntoDB(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}
	
	@MessageMapping("/insertDepotIntoDBPolygon") 
	public LocationDTO insertDepotIntoDBPolygon(LocationDTO lDTO){
		System.out.println("Depot insert into DB As a PloyGon");
		
		System.out.println(lDTO);
		 
		UtilityImpl utility= new UtilityImpl();
		utility.setDepotIntoDBAsPloygon(lDTO);
		//Thread.sleep(2000);
		return lDTO;
	}
	
	@MessageMapping("/updateDepotIntoDBPolygon") 
	public LocationDTO updateDepotIntoDBPolygon(LocationDTO lDTO){
		System.out.println("Depot Update into DB As a PloyGon");
		
		System.out.println(lDTO);
		 
		UtilityImpl utility= new UtilityImpl();
		utility.updateDepotIntoDBAsPloygon(lDTO);
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