import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonWriter {
    public void saveToFile () {
    	
        // list for storing objects on map
        JSONArray objectList = new JSONArray();
        
        // create a copy of road on the map to save, drop the start road
        ArrayList<Road> roadRecord = Map.roads;
        roadRecord.remove(0);

        if (!Map.roads.isEmpty()) {
        	for (Road road:roadRecord) {
        		JSONObject roadAttributes = new JSONObject();
        	    JSONObject roadObject = new JSONObject(); 
        		roadAttributes.put("roadLength", (road.getRoadLength()/2));
        		roadAttributes.put("orientation", road.getOrientation());
        		roadAttributes.put("xPos", road.getRoadXPos());
        		roadAttributes.put("yPos", road.getRoadYPos());
        		roadAttributes.put("direction", road.getRoadDirection());
        		roadAttributes.put("hasLight", road.isContainLight());
        	    roadObject.put("road", roadAttributes);
        	    objectList.add(roadObject);
        	}
        }
        if (!Map.cars.isEmpty()) {
        	
        	// in order to record the cars on map,
        	// only the number of each individual type is necessary
    		int busCount = 0;
    		int sedanCount = 0;
    		JSONObject carAttributes = new JSONObject();
    	    JSONObject carObject = new JSONObject();
    		
        	for (Car car:Map.cars) {
        		
        		if (car.getCarType() == "Bus") {
        			busCount++;
        		}
        		
        		if (car.getCarType() == "Sedan") {
        			sedanCount++;
        		}
        		
        	}
    	    carAttributes.put("busCount", busCount);
    	    carAttributes.put("sedanCount", sedanCount);
    	    carObject.put("car", carAttributes);
    	    objectList.add(carObject);
        }
        
        // Write JSON file
        try (FileWriter file = new FileWriter("map.json")) {

            file.write(objectList.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
