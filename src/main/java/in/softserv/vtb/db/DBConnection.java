package in.softserv.vtb.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;


public class DBConnection {
	
	private static DBConnection instance;
	
	private Connection con;


	/**
	 * Instantiates a new DBConnection.
	 */
	private DBConnection(){
				
		try {
    	
	    	// GeoFenceDbName Initialization with Database Name
	    	String GeoFenceDbName = "GeoFenceDbName";	    	
			
			HashMap temp=getValues();
						
			
			Class.forName((String) temp.get("driver"));
			
			
			String url=(String)temp.get("url")+GeoFenceDbName;
			String uid=(String)temp.get("uid");
			String pass=(String)temp.get("password");
			
			System.out.println(url+"-"+uid+"-"+pass);
			
			Connection conDB=DriverManager.getConnection(url,uid,pass);
		
			String dbName="";
			PreparedStatement DBNameStatement = conDB.prepareStatement("SELECT DBNAME FROM DatabaseDetail");	
			
		    ResultSet DBNameResultSet =  DBNameStatement.executeQuery();	    
		    
		    if(DBNameResultSet.next()){		    	
		    	dbName = DBNameResultSet.getString(1);	    	
		    }	    
		    
		    System.out.println("dbName:==>"+dbName);
		    
		    Class.forName((String) temp.get("driver"));
		    
			url=(String)temp.get("url")+dbName;
			uid=(String)temp.get("uid");
			pass=(String)temp.get("password");
			System.out.println(url+"-"+uid+"-"+pass);
			
			con=DriverManager.getConnection(url,uid,pass);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		//return con;
		
    	//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=VT", "sa", "Password!@#");
    	//Connection con = DriverManager.getConnection("jdbc:sqlserver://go-dev-server.cywja9nnzazr.ap-southeast-2.rds.amazonaws.com;databaseName=VT", "admin", "f08XQOraEyNN");
        //  return con;
    }

	
	
	
	public Connection getConnection() {
	    return con;
	}
  	
	HashMap details = new HashMap();
	public HashMap getValues(){
		HashMap hm = new HashMap();
		try {
			//String path="C:/AwareIM/bin";
			//String path=System.getProperty("user.dir").replace("\\", "/");
			String path=System.getProperty("catalina.home").replace("\\", "/").replace("/Tomcat", "")+"/bin";
			System.out.println(path);
			File file = new File(path+"/BASServer.props");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				hm.put(key, value);
				
			}
			String fullURL=(String) hm.get("BootstrapURL");
			if(((String) hm.get("DriverClassName")).equals("com.mysql.jdbc.Driver")){
				details.put("driver", (String) hm.get("DriverClassName"));
				details.put("url", (String)fullURL.split("\\?")[0]);
				details.put("uid",(String) fullURL.split("\\?")[1].split("=")[1].split("&")[0]);
				details.put("password", (String)fullURL.split("\\?")[1].split("=")[2]);
				
			}
			else if(((String) hm.get("DriverClassName")).equals("com.microsoft.sqlserver.jdbc.SQLServerDriver")){
				details.put("driver", (String) hm.get("DriverClassName"));
				details.put("url", (String)fullURL.split(";")[0]+";databaseName=");
				details.put("uid", (String)fullURL.split(";")[1].split("=")[1]);
				details.put("password", (String)fullURL.split(";")[2].split("=")[1]);
				
			}else{
				System.out.println("Database not identified or error in reading Basserver.props file");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return details;
	}
	
	
	public static DBConnection getInstance() throws SQLException {
		if (instance == null) {
			System.out.println("instance IS NULL ");
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
		System.out.println("instance IS NOT NULL ");
        return instance;
	 }
	
	/*
    public Connection getConnection() throws ClassNotFoundException, SQLException { 
    	
    	Connection con=null;    	
    	
    	// GeoFenceDbName Initialization with Database Name
    	String GeoFenceDbName = "GeoFenceDbName";
    	
		DBConnection dbc=new DBConnection(); 
		HashMap temp=dbc.getValues();
		
		Class.forName((String) temp.get("driver"));
		
		String url=(String)temp.get("url")+GeoFenceDbName;
		String uid=(String)temp.get("uid");
		String pass=(String)temp.get("password");
		
		System.out.println(url+"-"+uid+"-"+pass);
		
		con=DriverManager.getConnection(url,uid,pass);
		
		
		/*String dbName="";
		PreparedStatement DBNameStatement = con.prepareStatement("SELECT DBNAME FROM DatabaseDetail");	    
	    ResultSet DBNameResultSet =  DBNameStatement.executeQuery();	    
	    if(DBNameResultSet.next()){		    	
	    	dbName = DBNameResultSet.getString(1);	    	
	    }	    
	    System.out.println("dbName:==>"+dbName);
	    Class.forName((String) temp.get("driver"));
		url=(String)temp.get("url")+dbName;
		uid=(String)temp.get("uid");
		pass=(String)temp.get("password");
		System.out.println(url+"-"+uid+"-"+pass);
		con=DriverManager.getConnection(url,uid,pass);*/
	    
		//return con;
		
    	//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=VT", "sa", "Password!@#");
    	//Connection con = DriverManager.getConnection("jdbc:sqlserver://go-dev-server.cywja9nnzazr.ap-southeast-2.rds.amazonaws.com;databaseName=VT", "admin", "f08XQOraEyNN");
        //  return con;
   // } 
      
       
	
	public static void main(String[] args) throws Exception
    {        
		    Connection conn = null;
		    DBConnection db =  DBConnection.getInstance();
	        try {   
	        	conn = db.getConnection();
	            if (conn != null) {
	                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
	                System.out.println("Driver name: " + dm.getDriverName());
	                System.out.println("Driver version: " + dm.getDriverVersion());
	                System.out.println("Product name: " + dm.getDatabaseProductName());
	                System.out.println("Product version: " + dm.getDatabaseProductVersion());
	            } 
	      
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (conn != null && !conn.isClosed()) {
	                    conn.close();
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
    }

}