import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import in.softserv.vtb.db.DBConnection;

public class tet {

    public static void main(String[] args) {
    	
    	try {
			Connection connection = DBConnection.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Geofence center and radius
        double geofenceCenterLat = 26.867228;
        double geofenceCenterLng = 75.802009;
        double geofenceRadius = 1000; // in meters

        // Vehicle location
        double vehicleLat = 26.8546521;
        double vehicleLng = 75.822704;

        // Check if the vehicle is inside the geofence
        boolean isInGeofence = false; // = isInsideGeofence(geofenceCenterLat, geofenceCenterLng, geofenceRadius, vehicleLat, vehicleLng);
        
     // Check if the vehicle is outside the geofence
      /*  if (isOutsideGeofence(geofenceCenterLat, geofenceCenterLng, geofenceRadius, vehicleLat, vehicleLng)) {
            System.out.println("Vehicle is outside the geofence!");
        } else {
            System.out.println("Vehicle is inside the geofence.");
        }*/
        
        // Timestamps for in and out times
        LocalDateTime inTime = null;
        LocalDateTime outTime = null;
     // Check if the vehicle is inside the geofence
        if (isInGeofence) {
            if (inTime == null) {
                inTime = LocalDateTime.now();
                System.out.println("Vehicle entered the geofence at: " + inTime);
            }
        } else {
            if (outTime == null && inTime != null) {
                outTime = LocalDateTime.now();
                System.out.println("Vehicle exited the geofence at: " + outTime);
            }
        }

       /* if (isInGeofence)
			System.out.println("================>Source Inside");
		else
			System.out.println("================>Source Outside");*/
        // Print the result
        //System.out.println("Is the vehicle inside the geofence? " + isInGeofence);
    }

    static boolean isInsideGeofence(double geofenceCenterLat, double geofenceCenterLng,
                                     double geofenceRadius, double vehicleLat, double vehicleLng) {
        // Calculate the distance between geofence center and vehicle
        double distance = calculateDistance(geofenceCenterLat, geofenceCenterLng, vehicleLat, vehicleLng);

        // Check if the distance is less than or equal to the geofence radius
        return distance <= geofenceRadius;
    }
    
    static boolean isOutsideGeofence(double geofenceCenterLat, double geofenceCenterLng,
            double geofenceRadius, double vehicleLat, double vehicleLng) {
			// Calculate the distance between geofence center and vehicle
			double distance = calculateDistance(geofenceCenterLat, geofenceCenterLng, vehicleLat, vehicleLng);
			
			// Check if the distance is greater than the geofence radius
			return distance > geofenceRadius;
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
    
    
}