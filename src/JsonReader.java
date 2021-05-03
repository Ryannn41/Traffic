import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
	
	private Road roadStart;
	private JFrame frame;
	
	JsonReader(JFrame frame, Road roadStart) {
		this.frame = frame;
		this.roadStart = roadStart;
	}
	
    public void readFile() {
    	
        //JSON parser object to parse read file
        JSONParser parser = new JSONParser();
   
        try (FileReader reader = new FileReader("map.json")) {
        	
            //Read JSON file
            Object obj = parser.parse(reader);
            
            System.out.println("File read sucessful!");
 
            JSONArray objectList = (JSONArray) obj;
             
            //Iterate over object list
            for (Object object:objectList){
            	if (readRoadObject((JSONObject) object)) {
            		continue;
            	}
            	if (readCarObject((JSONObject)object)) {
            		continue;
            	}
            	
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    // read roads from Json file, if the object has been read is not a road, return false
    private boolean readRoadObject(JSONObject obj) {
        //Get road object within list
        JSONObject roadObject = (JSONObject) obj.get("road");
        
        if (roadObject == null) {
        	return false;
        }
        
        //Get road length
        int roadLength = ((Long) roadObject.get("roadLength")).intValue();    
        System.out.println(roadLength);
         
        //Get orientation of road
        String orientation = (String) roadObject.get("orientation");  
        System.out.println(orientation);
         
        //Get x-position of road
        int xPos = ((Long) roadObject.get("xPos")).intValue();    
        System.out.println(xPos);
        
        //Get y-position of road
        int yPos = ((Long) roadObject.get("yPos")).intValue();    
        System.out.println(yPos);
        
        //Get direction of road
        String direction = (String) roadObject.get("direction");    
        System.out.println(direction);
        
        //Get traffic light of road
        boolean hasLight = (boolean) roadObject.get("hasLight");    
        System.out.println(hasLight);
        
        if (hasLight) {
            Road road = new Road(roadLength, orientation, xPos, yPos, direction, new TrafficLight());
            Map.roads.add(road);
            System.out.println("add tr road");
        }
        else {
            Road road = new Road(roadLength, orientation, xPos, yPos, direction);
            Map.roads.add(road);
            System.out.println("add road");
        }
        
        return true;
    }
    
    // read cars from Json file, if the object has been read is not a car, return false
    private boolean readCarObject(JSONObject obj) {
    	
        //Get car object within list
        JSONObject carObject = (JSONObject) obj.get("car");
        if (carObject == null) {
        	return false;
        }
        
        //Get bus count
        int busCount = ((Long) carObject.get("busCount")).intValue();     
        //System.out.println("bc:"+busCount);
        
        //Get sedan count   
        int sedanCount = ((Long) carObject.get("sedanCount")).intValue(); 
        //System.out.println("sc:"+sedanCount);
        
        // create the number of bus based on the record
        for (int i = 0; i < busCount; i++) {
            Bus bus = new Bus(roadStart);
            Map.cars.add(bus);
            
            for (int x = roadStart.getRoadXPos(); x < bus.getRoadCarIsOn().getRoadLength()*50; x = x + 30) {
                bus.setCarXPosition(x);
                bus.setCarYPosition(bus.getRoadCarIsOn().getRoadYPos()+5);
                if(!bus.collision(x, bus)){
                    frame.repaint();
                    break;
                }
            }
        }
        
        // create the number of sedan based on the record
        for (int i = 0; i < sedanCount; i++) {
            Sedan sedan = new Sedan(roadStart);
            Map.cars.add(sedan);
            sedan.setCarYPosition(sedan.getRoadCarIsOn().getRoadYPos()+5);
            for (int x = roadStart.getRoadXPos(); x < sedan.getRoadCarIsOn().getRoadLength()*50; x = x + 30) {
                sedan.setCarXPosition(x);
                if(!sedan.collision(x, sedan)){
                    frame.repaint();
                    break;
                }

            }
        }
        
        return true;
    }
}
