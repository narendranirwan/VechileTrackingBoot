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
import java.time.LocalDateTime;
import java.util.List;
import com.google.maps.model.LatLng;

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
				  connection.prepareStatement("UPDATE VEHICLE SET SourceOut = ? WHERE ID = ?")) {
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
				  connection.prepareStatement("UPDATE VEHICLE SET DestinationIn = ? WHERE ID = ?")) {
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
	 
	   public void updateZonePolygonVerticsInDB(LocationDTO lDTO) {
			// TODO Auto-generated method stub
		
			 try (Connection connection = DBConnection.getConnection();						 
					 
					  PreparedStatement statement = 
					  connection.prepareStatement("UPDATE ZONE SET PolygonVertices=?, numberOfVertices=?  WHERE ID = ?")) {
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
			                                       
			           
			            
			            String formattedString = formattedVertices.toString();
			            System.out.println(formattedString);
				           
					  
			            statement.setString(1, formattedString);
			            statement.setString(2, lDTO.getNumberOfVertices());
			            statement.setInt(3, lDTO.getZoneID()); 
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
	  
	  public void setDepotIntoDB(LocationDTO lDTO) {
			// TODO Auto-generated method stub
		    
			 try (Connection connection = DBConnection.getConnection();
					   PreparedStatement statement = 
					   connection.prepareStatement("INSERT INTO Geofence(ID, BASVERSION, BASTIMESTAMP, Name, Location_REN, Location_RID, Location_RMA, Radius, EventType, numberofvertices) VALUES(?,?,?,?,?,?,?,?,?,?)")) {
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
					  statement.setString(4, lDTO.getDepotName());
					  statement.setString(5, "MGeoLocation");
					  statement.setString(6, "1");
					  statement.setString(7, "NULL");
					  statement.setString(8, lDTO.getDepotRadius());
					  statement.setString(9, "Circle");
					  statement.setString(10, "0");
					  
					  statement.executeUpdate();
					  
					  PreparedStatement locationIDStatement = connection.prepareStatement("SELECT NEXT VALUE FOR BAS_IDGEN_SEQ");
				       // Execute the query and retrieve the result
				      ResultSet locationIDResultSet =  locationIDStatement.executeQuery();
				      
				      // Get the locationID
				      int locationID = 0;
				      if (locationIDResultSet.next()) {
				    	  locationID = locationIDResultSet.getInt(1);
				      }
				      
				      PreparedStatement mgeoLocationStatement = connection.prepareStatement("INSERT INTO MGEOLOCATION(ID, BASVERSION, BASTIMESTAMP, WATCHID, Latitude, Longitude) VALUES(?,?,?,?,?,?)");
					  
				      mgeoLocationStatement.setInt(1, locationID);
				      mgeoLocationStatement.setString(2, "1");
				      mgeoLocationStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				      mgeoLocationStatement.setString(4, "0");
				      mgeoLocationStatement.setString(5, lDTO.getDepotLat());
				      mgeoLocationStatement.setString(6, lDTO.getDepotLng());
					  
					  
				      mgeoLocationStatement.executeUpdate();
				      
				      PreparedStatement updateDepotLocationRIDStatement = connection.prepareStatement("UPDATE Geofence SET LOCATION_RID = ? WHERE ID = ?");
					  
				      updateDepotLocationRIDStatement.setInt(1, locationID);
				      updateDepotLocationRIDStatement.setInt(2, maxId);				     
					  
				      updateDepotLocationRIDStatement.executeUpdate();
					  
					 } catch (SQLException ex) {
					  ex.printStackTrace();
					 } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 }
	  
	 public void updateDepotIntoDB(LocationDTO lDTO) {
			// TODO Auto-generated method stub
		 
			 try (Connection connection = DBConnection.getConnection();
					   PreparedStatement statement = 
					   connection.prepareStatement("Update GEOFENCE set numberOfVertices=?,polygonVertices=?,EventType=?,Radius=? where id = ?")) {
					  				   				       
				      statement.setInt(1, 0);
					  statement.setString(2, "NULL");
					  statement.setString(3, "Circle");
					  statement.setString(4, lDTO.getDepotRadius());
					  statement.setInt(5, lDTO.getDepotID());					  
					  					  
					  statement.executeUpdate();
					  
					  System.out.println("lDTO.getDepotID()):::===>"+lDTO.getDepotID());
                      
			          PreparedStatement psIDStatement = connection.prepareStatement("select Location_RID from GEOFENCE where id=?");
			          psIDStatement.setInt(1, lDTO.getDepotID());
			            // Execute the query and retrieve the result
			          ResultSet resultSetRs =  psIDStatement.executeQuery();
	
			          // Get the maximum ID
			          int Location_RID = 0;
			          if (resultSetRs.next()) {
			           	Location_RID = resultSetRs.getInt(1);
			          }
			          System.out.println("lDTO.Location_RID==CIRCLE UPDATE=>"+Location_RID);
				      
			          PreparedStatement mgeoLocationStatement = connection.prepareStatement("Update MGEOLOCATION set Latitude=?,Longitude=? where id = ?");		  
				      mgeoLocationStatement.setString(1, lDTO.getDepotLat());
				      mgeoLocationStatement.setString(2, lDTO.getDepotLng());
				      mgeoLocationStatement.setInt(3, Location_RID);					  
				  
				      mgeoLocationStatement.executeUpdate();     
					  
					 } catch (SQLException ex) {
					  ex.printStackTrace();
					 } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 }
	  
	  public void setDepotIntoDBAsPloygon(LocationDTO lDTO) {
			// TODO Auto-generated method stub
		  
			 try (Connection connection = DBConnection.getConnection();
					 	 PreparedStatement statement = 
					 	 connection.prepareStatement("INSERT INTO Geofence(ID, BASVERSION, BASTIMESTAMP, Name, Location_REN, Location_RID, Location_RMA, Radius, PolygonVertices, numberOfVertices, EventType) VALUES(?,?,?,?,?,?,?,?,?,?,?)")) {
					  
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
					  statement.setString(2, "1");
					  statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
					  statement.setString(4, lDTO.getDepotName());
					  statement.setString(5, "MGeoLocation");
					  statement.setString(6, "1");
					  statement.setString(7, "NULL");
					  statement.setString(8, "0");
					  statement.setString(9, formattedString);
					  statement.setString(10, lDTO.getNumberOfVertices());
					  statement.setString(11, "Polygon");
					  
					  
					  statement.executeUpdate();
					  
					  PreparedStatement locationIDStatement = connection.prepareStatement("SELECT NEXT VALUE FOR BAS_IDGEN_SEQ");
				       // Execute the query and retrieve the result
				      ResultSet locationIDResultSet =  locationIDStatement.executeQuery();
				      
				      // Get the locationID
				      int locationID = 0;
				      if (locationIDResultSet.next()) {
				    	  locationID = locationIDResultSet.getInt(1);
				      }
				      
				      PreparedStatement mgeoLocationStatement = connection.prepareStatement("INSERT INTO MGEOLOCATION(ID, BASVERSION, BASTIMESTAMP, WATCHID, Latitude, Longitude) VALUES(?,?,?,?,?,?)");
					  
				      mgeoLocationStatement.setInt(1, locationID);
				      mgeoLocationStatement.setString(2, "1");
				      mgeoLocationStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				      mgeoLocationStatement.setString(4, "0");
				      mgeoLocationStatement.setString(5, lDTO.getDepotLat());
				      mgeoLocationStatement.setString(6, lDTO.getDepotLng());
					  
					  
				      mgeoLocationStatement.executeUpdate();
				      
				      PreparedStatement updateDepotLocationRIDStatement = connection.prepareStatement("UPDATE Geofence SET LOCATION_RID = ? WHERE ID = ?"); 
					  
				      updateDepotLocationRIDStatement.setInt(1, locationID);
				      updateDepotLocationRIDStatement.setInt(2, maxId);				     
					  
				      updateDepotLocationRIDStatement.executeUpdate();
					  
					 } catch (SQLException ex) {
					  ex.printStackTrace();
					 } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 }
	  
	 public void updateDepotIntoDBAsPloygon(LocationDTO lDTO) {
			// TODO Auto-generated method stub
		  
			 try (Connection connection = DBConnection.getConnection();
					 	 PreparedStatement statement = 
					 	 connection.prepareStatement("Update GEOFENCE set numberOfVertices=?,polygonVertices=?,EventType=?,Radius=? where id = ?")) {
					  
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
				            
				            System.out.println("lDTO.getDepotID()):::===>"+lDTO.getDepotID());
				                              
				            PreparedStatement IDStatement = connection.prepareStatement("select Location_RID from GEOFENCE where id=?");
				            IDStatement.setInt(1, lDTO.getDepotID());
				            // Execute the query and retrieve the result
				            ResultSet resultSet =  IDStatement.executeQuery();
		
				            // Get the maximum ID
				            int Location_RID = 0;
				            if (resultSet.next()) {
				            	Location_RID = resultSet.getInt(1);
				            }
				            System.out.println("lDTO.Location_RID==POLYGON UPDATE=>"+Location_RID);
				            // Calculate the next ID (increment by 1)
				            //int nextId = maxId + 1;
				            
				            String formattedString = formattedVertices.toString();
				            System.out.println(formattedString);				 
					
						    statement.setString(1, lDTO.getNumberOfVertices());
							//statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
						    statement.setString(2, formattedString);
						    statement.setString(3, "Polygon");
							statement.setString(4, "NULL");	
							statement.setInt(5, lDTO.getDepotID());	
					  
					        statement.executeUpdate();
					  
					  	      
					        PreparedStatement mgeoLocationStatement = connection.prepareStatement("Update MGEOLOCATION set Latitude=?,Longitude=? where id = ?");		  
					        mgeoLocationStatement.setString(1, lDTO.getDepotLat());
					        mgeoLocationStatement.setString(2, lDTO.getDepotLng());
					        mgeoLocationStatement.setInt(3, Location_RID);					  
					  
					        mgeoLocationStatement.executeUpdate();     
				      
					  
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
						  statement.setString(10, "Geofence");
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
		  
		  
		  
		  public void setSourceOutDestinationInDB(LocationDTO lDTO) {
			  System.out.println("IN BACKEND====>"); 
			  try (Connection connection = DBConnection.getConnection();
					  PreparedStatement SourceStatement = 
					  connection.prepareStatement("select v.id AS vehicleID, d.id AS depotID, m.ID AS Location_RID, m.Latitude AS StartLat, m.Longitude AS StartLng, d.Radius, d.EventType, d.numberOfVertices, d.polygonVertices from VEHICLE v join Geofence d on v.StartDepo_RID=d.ID left join MGeoLocation m on m.id = d.Location_RID where v.id=?")) {
					  
				      SourceStatement.setString(1,lDTO.getId());
				     					 
			            // Execute the query and retrieve the result
			          ResultSet sourceResultSet =  SourceStatement.executeQuery();
			          
			          double srcLat = 0;
			          double srcLng = 0;
			          double srcRadius = 0;
			          String srcEventType ="";
			          int srcNumberOfVertices = 0;
			          String srcPolygonVertices = "";
			          
			          if(sourceResultSet.next()) {
			        	  srcLat = sourceResultSet.getDouble(4);
			        	  srcLng = sourceResultSet.getDouble(5);
			        	  srcRadius = sourceResultSet.getDouble(6);
			        	  srcEventType = sourceResultSet.getString(7);
			        	  System.out.println("srcLat==>"+srcLat);
			        	  System.out.println("srcLng==>"+srcLng);
			        	  System.out.println("srcRadius==>"+srcRadius);
			        	  System.out.println("srcEventType==>"+srcEventType);
			        	  
			        	  if(srcEventType.equals("Polygon")) {
			        		  srcNumberOfVertices = sourceResultSet.getInt(8);
			        		  srcPolygonVertices = sourceResultSet.getString(9);
				        	  System.out.println("srcNumberOfVertices=>"+srcNumberOfVertices);
				        	  System.out.println("srcPolygonVertices=>"+srcPolygonVertices);
			        	  } 			        	  
			        	 
			          }
			          			
			          if(srcEventType.equals("Circle")) {
				          boolean isInGeofence = isInsideGeofence(srcLat, srcLng, srcRadius, Double.parseDouble(lDTO.getLatitude()), Double.parseDouble(lDTO.getLongitude()));
				          
				          /*if (isInGeofence) {
								System.out.println("================>Source Inside");
				                setSourceOutInDB(lDTO);
				          }
						  else {
								System.out.println("================>Source Outside");
						  }*/
				       		          
				       
				          LocalDateTime inTime = null;				         
				          //Check if the vehicle is inside the geofence
				          if (isInGeofence) {
				              if (inTime == null) {
				                  inTime = LocalDateTime.now();
				                  System.out.println("====>Source Inside Circle");
					              setSourceOutInDB(lDTO);
				              }
				          } 	          
			          }
			          
			          if(srcEventType.equals("Polygon")) {
			        	  
			        	  // Remove parentheses and split into individual coordinates
			              String[] coordinatePairs = srcPolygonVertices.replaceAll("[()]", "").split(",");

			              // Convert to separate x and y arrays
			              double[] xPoints = new double[coordinatePairs.length / 2];
			              double[] yPoints = new double[coordinatePairs.length / 2];

			              for (int i = 0; i < coordinatePairs.length; i += 2) {
			              	xPoints[i / 2] = Double.parseDouble(coordinatePairs[i].trim());
			              	yPoints[i / 2] = Double.parseDouble(coordinatePairs[i + 1].trim());
			              }
			              
			              // Check if the vehicle is inside or outside the geofence
			              boolean isInside = isInsidePolygonGeofence(xPoints, yPoints, Double.parseDouble(lDTO.getLatitude()), Double.parseDouble(lDTO.getLongitude()), srcNumberOfVertices);

			              if (isInside) {
			            	  LocalDateTime inTime = null;
			            	  //System.out.println("Vehicle is " + (isInside ? "inside" : "outside") + " the geofence."); 
			            	  if (inTime == null) {
				                  inTime = LocalDateTime.now();
				                  System.out.println("====>Source Inside PolyGon");
					              setSourceOutInDB(lDTO);
				              }
			            	  
			              }			        	  
			          
			          }
			          
			          			          
			          PreparedStatement EndStatement = 
							  connection.prepareStatement("select v.id AS vehicleID, d.id AS depotID, m.ID AS Location_RID, m.Latitude AS EndLat, m.Longitude AS EndLng, d.Radius, d.EventType, d.numberOfVertices, d.polygonVertices from VEHICLE v join Geofence d on v.EndDepot_RID=d.ID left join MGeoLocation m on m.id = d.Location_RID where v.id=?");
			          
			          EndStatement.setString(1,lDTO.getId());
			          
			          // Execute the query and retrieve the result
			          ResultSet endResultSet =  EndStatement.executeQuery();
			          
			          double endLat = 0;
			          double endLng = 0;
			          double endRadius = 0;
			          String endEventType = "";
			          int endNumberOfVertices = 0;
			          String endcPolygonVertices = "";
			          
			          if(endResultSet.next()) {
			        	  endLat = endResultSet.getDouble(4);
			        	  endLng = endResultSet.getDouble(5);
			        	  endRadius = endResultSet.getDouble(6);
			        	  endEventType = endResultSet.getString(7);
			        	  System.out.println("endLat==>"+endLat);
			        	  System.out.println("endLng==>"+endLng);
			        	  System.out.println("endRadius==>"+endRadius);
			        	  System.out.println("endEventType==>"+endEventType);
			        	  
			        	  if(endEventType.equals("Polygon")) {
			        		  endNumberOfVertices = endResultSet.getInt(8);
			        		  endcPolygonVertices = endResultSet.getString(9);
				        	  System.out.println("endNumberOfVertices=>"+endNumberOfVertices);
				        	  System.out.println("endcPolygonVertices=>"+endcPolygonVertices);
			        	  } 			        	
			          }  			          
			    	          
			          if(endEventType.equals("Circle")) {
				          boolean isInDestiGeofence = isInsideGeofence(endLat, endLng, endRadius, Double.parseDouble(lDTO.getLatitude()), Double.parseDouble(lDTO.getLongitude()));
				          LocalDateTime inTime = null;	
				          if (isInDestiGeofence) {
				        	  if (inTime == null) {
				        		  inTime = LocalDateTime.now();
				        		  System.out.println("====>Destination Inside Circle");
								  setDestinationInInDB(lDTO);
				        	  }								
				          }						  
			           }
			          
			          
			          if(endEventType.equals("Polygon")) {
			        	  
			        	// Remove parentheses and split into individual coordinates
			              String[] coordinatePairs = endcPolygonVertices.replaceAll("[()]", "").split(",");

			              // Convert to separate x and y arrays
			              double[] xPoints = new double[coordinatePairs.length / 2];
			              double[] yPoints = new double[coordinatePairs.length / 2];

			              for (int i = 0; i < coordinatePairs.length; i += 2) {
			              	xPoints[i / 2] = Double.parseDouble(coordinatePairs[i].trim());
			              	yPoints[i / 2] = Double.parseDouble(coordinatePairs[i + 1].trim());
			              }
			              
			              // Check if the vehicle is inside or outside the geofence
			              boolean isInside = isInsidePolygonGeofence(xPoints, yPoints, Double.parseDouble(lDTO.getLatitude()), Double.parseDouble(lDTO.getLongitude()), endNumberOfVertices);

			              if (isInside) {
			            	  LocalDateTime inTime = null;
			            	  if (inTime == null) {
				                  inTime = LocalDateTime.now();
				                  System.out.println("====>Destination Inside PolyGon");
				                  setDestinationInInDB(lDTO);
				              }
			            	  
			              }
			          
			        	  
			        	  
			          }
			          
			          
			          
			          
			          
				          
			  		}catch (SQLException ex) {
						  ex.printStackTrace();
					} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}	
			  		
			  
			  
		  
		  }
		  
		  static boolean isInsideGeofence(double geofenceCenterLat, double geofenceCenterLng,
                double geofenceRadius, double vehicleLat, double vehicleLng) {
				// Calculate the distance between geofence center and vehicle
				double distance = calculateDistance(geofenceCenterLat, geofenceCenterLng, vehicleLat, vehicleLng);				
				// Check if the distance is less than or equal to the geofence radius
				return distance <= geofenceRadius;
		  }

		  static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
			// Vincenty formula to calculate distance between two points on the Earth's surface
			double a = 6378137; // equatorial radius in meters
			double f = 1 / 298.257223563; // flattening
			double b = (1 - f) * a; // polar radius
			
			double phi1 = Math.toRadians(lat1);
			double phi2 = Math.toRadians(lat2);
			double lambda1 = Math.toRadians(lon1);
			double lambda2 = Math.toRadians(lon2);
			
			double U1 = Math.atan((1 - f) * Math.tan(phi1));
			double U2 = Math.atan((1 - f) * Math.tan(phi2));
			double L = lambda2 - lambda1;
			double sinU1 = Math.sin(U1);
			double cosU1 = Math.cos(U1);
			double sinU2 = Math.sin(U2);
			double cosU2 = Math.cos(U2);
			
			double sinLambda, cosLambda, sinSigma, cosSigma, sigma, sinAlpha, cosSqAlpha, cos2SigmaM;
			
			double lambda_prev;
			do {
			sinLambda = Math.sin(L);
			cosLambda = Math.cos(L);
			sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda) +
			 (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
			if (sinSigma == 0) {
			return 0; // coincident points
			}
			cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
			cosSqAlpha = 1 - sinAlpha * sinAlpha;
			cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
			double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
			lambda_prev = L;
			L = (lambda2 - lambda1) + (1 - C) * f * sinAlpha *
			 (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
			} while (Math.abs(L - lambda_prev) > 1e-12);
			
			double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
			double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
			double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
			double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) -
			B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
			
			double distance = b * A * (sigma - deltaSigma);
			return distance;
		}	  
		  
		private static boolean isInsidePolygonGeofence(double[] xPoints, double[] yPoints, double vehicleLat, double vehicleLng, int numberOfVertices) {
		        int j = numberOfVertices - 1;
		        boolean isInside = false;

		        for (int i = 0; i < numberOfVertices; i++) {
		            if ((yPoints[i] < vehicleLng && yPoints[j] >= vehicleLng
		                    || yPoints[j] < vehicleLng && yPoints[i] >= vehicleLng)
		                    && (xPoints[i] <= vehicleLat || xPoints[j] <= vehicleLat)) {
		                if (xPoints[i] + (vehicleLng - yPoints[i]) / (yPoints[j] - yPoints[i]) * (xPoints[j] - xPoints[i]) < vehicleLat) {
		                    isInside = !isInside;
		                }
		            }
		            j = i;
		        }

		        return isInside;
		}  
		  
		
}
