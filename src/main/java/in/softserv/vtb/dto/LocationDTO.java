package in.softserv.vtb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
 
@Data
public class LocationDTO {

	private String latitude; 
	private String longitude; 
	private String id;
	private String status;
	private String rego; 
	private String zoneName;
	@JsonProperty("polygonVertices")
    private List<VertexDTO> polygonVertices; 
	private String numberOfVertices;  
	private int zoneID;  
	private int depotID; 
	private String  isEntry;   
	    
	  
}
    