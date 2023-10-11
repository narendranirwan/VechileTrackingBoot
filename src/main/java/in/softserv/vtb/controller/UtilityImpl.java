package in.softserv.vtb.controller;

import in.softserv.vtb.db.DBConnection;
import in.softserv.vtb.dto.LocationDTO;
import in.softserv.vtb.dto.VertexDTO;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;



public class UtilityImpl {

	public void setLocationInDB(LocationDTO lDTO) {
		// TODO Auto-generated method stub
	
		 try (Connection connection = DBConnection.getConnection();
				   PreparedStatement statement = 
				     connection.prepareStatement("UPDATE MAPINFO SET longitude = ?, latitude = ?, status = ? WHERE truckId = ?")) {
				  statement.setString(1, lDTO.getLongitude());
				  statement.setString(2, lDTO.getLatitude());
				  statement.setString(3, lDTO.getStatus());
				  statement.setString(4, lDTO.getId());
				  statement.executeUpdate();
				 } catch (SQLException ex) {
				  ex.printStackTrace();
				 } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public void setSourceOutInDB(LocationDTO lDTO) {
		// TODO Auto-generated method stub
	
		 try (Connection connection = DBConnection.getConnection();
				   PreparedStatement statement = 
				   connection.prepareStatement("UPDATE VEHICLE SET SourceOut = ? WHERE rego = ?")) {
				  statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				  statement.setString(2, lDTO.getId());
				  statement.executeUpdate();
				 } catch (SQLException ex) {
				  ex.printStackTrace();
				 } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 } 
	 
	public void setDestinationInInDB(LocationDTO lDTO) {
		// TODO Auto-generated method stub
	
		 try (Connection connection = DBConnection.getConnection();
				   PreparedStatement statement = 
				   connection.prepareStatement("UPDATE VEHICLE SET DestinationIn = ? WHERE rego = ?")) {
				  statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				  statement.setString(2, lDTO.getId());
				  statement.executeUpdate();
				 } catch (SQLException ex) {
				  ex.printStackTrace();
				 } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 } 
	
	  public void setPolygonVerticsInDB(LocationDTO lDTO) {
		// TODO Auto-generated method stub
	
		 try (Connection connection = DBConnection.getConnection();	
				 
				 
				  PreparedStatement statement = 
				  connection.prepareStatement("INSERT INTO ZONE (ID,PolygonVertices,numberOfVertices,name) VALUES (?,?,?,?)")) {
				// Sample list of vertices
		            List<VertexDTO> vertices = lDTO.getPolygonVertices(); 
		            StringBuilder formattedVertices = new StringBuilder();
		            for (VertexDTO vertex : vertices) {
		                double lat = vertex.getLat();
		                double lng = vertex.getLng();

		                System.out.println("Lat: " + lat + ", Lng: " + lng);
		             // Append the formatted vertex to the StringBuilder
		                formattedVertices.append("(")
		                                .append(lat)
		                                .append(", ")
		                                .append(lng)
		                                .append("),");
		            } 
		         // Remove the trailing comma, if any
		            if (formattedVertices.length() > 0) {
		                formattedVertices.deleteCharAt(formattedVertices.length() - 1);
		            }
		            
		                              
		            PreparedStatement IDStatement = connection.prepareStatement("SELECT NEXT VALUE FOR BAS_IDGEN_SEQ");
		            // Execute the query and retrieve the result
		            ResultSet resultSet =  IDStatement.executeQuery();

		            // Get the maximum ID
		            int maxId = 0;
		            if (resultSet.next()) {
		                maxId = resultSet.getInt(1);
		            }

		            // Calculate the next ID (increment by 1)
		            //int nextId = maxId + 1;
		            
		            String formattedString = formattedVertices.toString();
		            System.out.println(formattedString);
			            
				  statement.setInt(1, maxId);
				  statement.setString(2, formattedString);
				  statement.setString(3, lDTO.getNumberOfVertices());
				  statement.setString(4, lDTO.getZoneName()); 
				  statement.executeUpdate();
				 } catch (SQLException ex) {
				  ex.printStackTrace();
				 } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	   }
	  
	  public void setEntryInZoneInDB(LocationDTO lDTO) {
			// TODO Auto-generated method stub
		 
			 try (Connection connection = DBConnection.getConnection();
					   PreparedStatement statement = 
					   connection.prepareStatement("INSERT INTO ZONEENTRY(ID,BASVERSION,BASTIMESTAMP,Vehicle_REN,Vehicle_RID,Vehicle_RMA,Time,Zone_REN,Zone_RID,Zone_RMA) VALUES(?,?,?,?,?,?,?,?,?,?)")) {
					  //statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				 
					   PreparedStatement IDStatement = connection.prepareStatement("SELECT NEXT VALUE FOR BAS_IDGEN_SEQ");
				            // Execute the query and retrieve the result
				            ResultSet resultSet =  IDStatement.executeQuery();
		
				            // Get the maximum ID
				       int maxId = 0;
				       if (resultSet.next()) {
				              maxId = resultSet.getInt(1);
				       }
				      statement.setInt(1, maxId);
					  statement.setString(2, "1");
					  statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
					  statement.setString(4, "Vehicle");
					  statement.setString(5, lDTO.getId());
					  statement.setString(6, "NULL");
					  statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
					  statement.setString(8, "Zone");
					  statement.setInt(9, lDTO.getZoneID());
					  statement.setString(10, "NULL");
					  statement.executeUpdate();
					 } catch (SQLException ex) {
					  ex.printStackTrace();
					 } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 }
	  
	  public void updateVehicleLatLongInDB(LocationDTO lDTO) {
			// TODO Auto-generated method stub
		
			 try (Connection connection = DBConnection.getConnection();
					  PreparedStatement statement = 
					  connection.prepareStatement("UPDATE MGEOLOCATION SET LATITUDE=?, LONGITUDE=? where id = ?")) {
					  
				      System.out.println("INNN====>"); 
					  PreparedStatement CurrentLocStatement = connection.prepareStatement("SELECT CurrentLocation_RID FROM VEHICLE WHERE ID = ?");
					  CurrentLocStatement.setString(1, lDTO.getId());
			            // Execute the query and retrieve the result
			          ResultSet resultSet =  CurrentLocStatement.executeQuery();
			          
			          int CurrentLocation_RID = 0;
			          
			          if(resultSet.next()) {
			        	  CurrentLocation_RID = resultSet.getInt(1);
			        	  System.out.println("CurrentLocation_RID==>"+CurrentLocation_RID);
			          }
	
			          System.out.println("getId==>"+lDTO.getId()); 
			          System.out.println("getLatitude==>"+lDTO.getLatitude()); 
			          System.out.println("getLongitude==>"+lDTO.getLongitude()); 
			          statement.setString(1, lDTO.getLatitude());
					  statement.setString(2, lDTO.getLongitude());
					  statement.setInt(3, CurrentLocation_RID);
					  
					  statement.executeUpdate();
					  
					 } catch (SQLException ex) {
					  ex.printStackTrace();
					 } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	      }  
	  	 
		  public void setEntryInDepotInDB(LocationDTO lDTO) {
				// TODO Auto-generated method stub
			  
				 try (Connection connection = DBConnection.getConnection();
						   PreparedStatement statement = 
						   connection.prepareStatement("INSERT INTO DEPOENTRY(ID, BASVERSION, BASTIMESTAMP, EntryExitTime, Vehicle_REN, Vehicle_RID, Vehicle_RMA, isEntry, EntryExit, Depo_REN, Depo_RID, Depo_RMA) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)")) {
						  //statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					 
						   PreparedStatement IDStatement = connection.prepareStatement("SELECT NEXT VALUE FOR BAS_IDGEN_SEQ");
					            // Execute the query and retrieve the result
					            ResultSet resultSet =  IDStatement.executeQuery();
			
					            // Get the maximum ID
					       int maxId = 0;
					       boolean isEntry=false;
					       System.out.println("getId==>"+lDTO.getId()); 
					       System.out.println("getIsEntry==>"+lDTO.getIsEntry()); 
					       System.out.println("getDepotID==>"+lDTO.getDepotID()); 
					       
					       if (resultSet.next()) {
					              maxId = resultSet.getInt(1);
					       }
					      statement.setInt(1, maxId);
						  statement.setString(2, "1");
						  statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
						  statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
						  statement.setString(5, "Vehicle");
						  statement.setString(6, lDTO.getId());
						  statement.setString(7, "NULL");
						  
						  if(lDTO.getIsEntry().equals("Yes")) {
							  isEntry = true;
						  }
						  if(lDTO.getIsEntry().equals("No")) {
							  isEntry = false;
						  }
						  statement.setBoolean(8, isEntry);
						  
						  String entryExit="";
						  if(isEntry==true) {
							  entryExit="Entry";
							  
						  }
						  if(isEntry==false) {
							  entryExit="Exit";
							  
						  }						  
						  statement.setString(9, entryExit);						  
						  statement.setString(10, "Depot");
						  statement.setInt(11, lDTO.getDepotID());
						  statement.setString(12, "NULL");
						  
						
				          
						  statement.executeUpdate();
						 } catch (SQLException ex) {
						  ex.printStackTrace();
						 } catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    }
		
}