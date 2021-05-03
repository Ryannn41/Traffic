import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* This class is for the road model
* @author 
* @version 
*/

public class Road extends JPanel{
    private TrafficLight light;
    private boolean hasLight;
    private int numOfSegments; //length of road
    private static final int ROAD_WIDTH = 50;
    private int roadYPos;
    private int endRoadYPos;
    private int roadXPos;
    private int endRoadXPos;
    private Color lightColor = Color.green;
    private String orientation; //orientation of road, either be vertical or horizontal
    private String trafficDirection;
    
    
    private Point prevPoint;
    private boolean state;
    
    /**
     * This method is for drawing road
     * @param g for drawing components on
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 1200, 1200);

        for(int z = 0; z < Map.roads.size();z++){
            Road r = Map.roads.get(z);
            r.paintRoad(g);
            if(r.getOrientation().equals("vertical")){
                for (int c = 0; c < Map.cars.size(); c++) {
                    Car car = Map.cars.get(c);
                    if(car.getRoadCarIsOn().equals(r)) {
                        car.paintMeVertical(g);
                    }
                }
                if (r.getTrafficLight() != null) {
                    r.paintLight(g);
                }
            }
            else{
                for (int c = 0; c < Map.cars.size(); c++) {
                    Car car = Map.cars.get(c);
                    if(car.getRoadCarIsOn().equals(r)) {
                        car.paintMeHorizontal(g);
                    }
                }
                if (r.getTrafficLight() != null) {
                    r.paintLight(g);
                }
            }
        }
    }
    
    /**
     * This method is for paint traffic light
     * @param g for drawing components on
     */
    public void paintLight(Graphics g){
        g.setColor(lightColor);
        if(getOrientation().equals("horizontal")) {
            if (getTrafficDirection().equals("east")) {
                g.fillRect(roadXPos + numOfSegments * 25 - 10, roadYPos - 20, 10, 20);
                g.setColor(Color.black);
                g.drawRect(roadXPos + numOfSegments * 25 - 10, roadYPos - 20, 10, 20);
            } else {
                g.fillRect(roadXPos, roadYPos - 20, 10, 20);
                g.setColor(Color.black);
                g.drawRect(roadXPos, roadYPos - 20, 10, 20);
            }
        }
        else{
            if (getTrafficDirection().equals("south")) {
                g.fillRect(roadYPos - 20, roadXPos + numOfSegments * 25 - 10, 20, 10);
                g.setColor(Color.black);
                g.drawRect(roadYPos - 20, roadXPos + numOfSegments * 25 - 10, 20, 10);
            }
            else{
                g.fillRect(roadYPos - 20, roadXPos, 20, 10);
                g.setColor(Color.black);
                g.drawRect(roadYPos - 20, roadXPos, 20, 10);
            }
        }
    }
    /**
     * This method is for paint road, either be vertical or horizontal
     * @param g for drawing components on
     */
    public void paintRoad(Graphics g){
        if(orientation.equals("horizontal")) {
            g.setColor(Color.black);
            g.fillRect(roadXPos, roadYPos,numOfSegments * 25, ROAD_WIDTH);
            g.setColor(Color.WHITE);
            for (int j = 0; j < numOfSegments * 25; j = j + 50) { // line being drawn
                g.fillRect(roadXPos + j, roadYPos + ROAD_WIDTH / 2, 30, 2);
            }
        }
        else{
            g.setColor(Color.black);
            g.fillRect(roadYPos, roadXPos, ROAD_WIDTH, numOfSegments * 25);
            g.setColor(Color.WHITE);
            for (int j = 0; j < numOfSegments * 25; j = j + 50) { // line being drawn
                g.fillRect( roadYPos + ROAD_WIDTH / 2, roadXPos + j, 2,30);
            }
        }
    }
    /**
     * 
     * This method is constructors for paint road with traffic light and without traffic light
     * 
     */
    Road(int numOfSegments, String orientation, int xPos, int yPos, String direction){
        super();
        this.numOfSegments = numOfSegments*2;
        this.orientation = orientation;
        this.hasLight = false;
        this.roadXPos = xPos;
        this.roadYPos = yPos;
        this.endRoadXPos = xPos + numOfSegments * 50;
        this.endRoadYPos = yPos + numOfSegments * 50;
        this.trafficDirection = direction;
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseListener(dragListener);
    }
    Road(int numOfSegments, String orientation, int xPos, int yPos, String direction, TrafficLight light){
        super();
        this.numOfSegments = numOfSegments*2;
        this.orientation = orientation;
        this.light = light;
        this.hasLight = true;
        this.roadXPos = xPos;
        this.roadYPos = yPos;
        this.endRoadXPos = xPos + numOfSegments * 50;
        this.endRoadYPos = yPos + numOfSegments * 50;
        this.trafficDirection = direction;
     
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseListener(dragListener);

    }
    public String getOrientation(){ return orientation;}
    public TrafficLight getTrafficLight(){
        return light;
    }
    public int getRoadLength(){
        return numOfSegments;
    }
    public int getRoadYPos(){
        return roadYPos;
    }
    public int getRoadXPos(){
        return roadXPos;
    }
    public int getEndRoadYPos(){
        return endRoadYPos;
    }
    public int getEndRoadXPos(){
        return endRoadXPos;
    }
    public String getRoadDirection() {
    	return trafficDirection;
    }
    public boolean isContainLight() {
    	return hasLight;
    }
    
    // for moving road
    /*
    public void updateRoadYPos(int xPos) {
        this.roadXPos = xPos;
        this.endRoadXPos = xPos + numOfSegments * 50;
    }
    public void updateRoadXPos(int yPos) {
        this.roadYPos = yPos;
        this.endRoadYPos = yPos + numOfSegments * 50;
    }
    */
    
    private class ClickListener extends MouseAdapter {
    	public void mousePressed(MouseEvent e) {
    		//roadXPos = e.getX();
    		//roadYPos = e.getY();
    	    //endRoadXPos = roadXPos + numOfSegments * 50;
    	    //endRoadYPos = roadYPos+ numOfSegments * 50;
    		//prevPoint = e.getPoint();
    	}
    }
    
    private class DragListener extends MouseAdapter {
    	public void mouseDragged(MouseEvent e) {
    		//int currentXPos = e.getX();
    		//int currentYPos = e.getY();
    		//Point currentPoint = new Point(currentXPos, currentYPos);
    		//currentPoint.translate((int)(currentXPos - prevPoint.getX()), (int)(currentYPos - prevPoint.getY()));
    		//prevPoint = currentPoint;			
    		//repaint();
    		
    		Point currentPoint = e.getPoint();
    		currentPoint.translate(
    				(int)(currentPoint.x - roadXPos), 
    				(int)(currentPoint.y - roadYPos)
    		);
    		
    		

    		
    	}
    }
    
    
    public String getTrafficDirection(){ return trafficDirection; }
    public void setLightColor(Color c){
        lightColor = c;
    }
    
    public String toString() {
    	return "Y pos:"+this.roadYPos+"X pos:"+this.roadXPos;
    }

}


