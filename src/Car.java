import java.awt.*;
import java.util.ArrayList;

public class Car{
    private Road road; // road that the car is on
    protected int yPos; // current position on map
    protected int xPos; // current position on map
    protected int height;
    protected int width;
    public void paintMeHorizontal(Graphics g){
    }
    public void paintMeVertical(Graphics g){
    }
    Car(Road road){
        this.road = road;
        yPos = getRoadCarIsOn().getRoadYPos();
        xPos = getRoadCarIsOn().getRoadXPos();
    }

    public Road getRoadCarIsOn(){
        return road;
    }

    public int getCarXPosition(){ return xPos; }
    public void setCarXPosition(int x){
        xPos = x;
    }
    public int getCarYPosition(){ return yPos; }
    public void setCarYPosition(int y){
        yPos = y;
    }
    public int getCarWidth(){return width;}

    private void setCurrentRoad(Road road){
        this.road = road;
    }
    private boolean checkIfAtEndOfRoad(){
        if(getRoadCarIsOn().getTrafficDirection().equals("east") || getRoadCarIsOn().getTrafficDirection().equals("south")){
            return (xPos+width >= getRoadCarIsOn().getEndRoadXPos());
        }
        else if(getRoadCarIsOn().getTrafficDirection().equals("west") || getRoadCarIsOn().getTrafficDirection().equals("north")){
            return (xPos <= road.getRoadXPos());
        }
        else
            return true;
    }
    public boolean collision(int x, Car car){
        String direction = getRoadCarIsOn().getTrafficDirection();
        for (int i = 0; i < Map.cars.size(); i++) {
            Car c = Map.cars.get(i);
            if (c.getRoadCarIsOn() == getRoadCarIsOn() && car.getCarYPosition() == c.getCarYPosition()) {
                int otherCarXPosition = c.getCarXPosition();
                int otherCarWidth = c.getCarWidth();
                if (!car.equals(c)) { // if not checking current car
                    if (x < otherCarXPosition + otherCarWidth && //left side is left  of cars right side
                            x + otherCarWidth > otherCarXPosition && (direction.equals("east") || direction.equals("south"))) { // right side right of his left side
                        return true;
                    }
                    else if (x < otherCarXPosition + otherCarWidth * 2 - 15 && x + car.getCarWidth() > otherCarXPosition &&
                            (direction.equals("west") || direction.equals("north"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean canMoveForward(){
        String direction = getRoadCarIsOn().getTrafficDirection();
        if(xPos+width >= getRoadCarIsOn().getRoadLength()*25-25+getRoadCarIsOn().getRoadXPos() && (direction.equals("east") || direction.equals("south"))
                || xPos <= getRoadCarIsOn().getRoadXPos()+25 && ( direction.equals("west") || direction.equals("north") )) {
            if (getRoadCarIsOn().getTrafficLight() == null) {
                return true;
            }
            else {
                TrafficLight light = getRoadCarIsOn().getTrafficLight();
                return light.getCurrentColor().equals("green");
            }
        }
        return true;
    }
    private int getIndexOfCurrentRoad(){
        return Map.roads.indexOf(road);
    }
    private Road nextRoad(){
        int otherRoadXPos;
        int otherRoadYPos;
        int otherRoadEndXPos;
        int otherRoadEndYPos;
        int currentRoadXPos;
        int currentRoadYPos;
        int currentRoadEndXPos;
        int currentRoadEndYPos;
        Road currentRoad = Map.roads.get(getIndexOfCurrentRoad());
        Road nextRoad = Map.roads.get(0);
        ArrayList<Integer> xPositions = new ArrayList<Integer>();
        ArrayList<Integer> yPositions = new ArrayList<Integer>();
        if(currentRoad.getOrientation().equals("vertical")){
            currentRoadXPos = currentRoad.getRoadYPos();
            currentRoadYPos = currentRoad.getRoadXPos();
            currentRoadEndXPos = currentRoad.getEndRoadYPos();
            currentRoadEndYPos = currentRoad.getEndRoadXPos();
        }
        else{
            currentRoadXPos = currentRoad.getRoadXPos();
            currentRoadYPos = currentRoad.getRoadYPos();
            currentRoadEndXPos = currentRoad.getEndRoadXPos();
            currentRoadEndYPos = currentRoad.getEndRoadYPos();
        }
        for (int i = 0; i < Map.roads.size(); i++) {
            Road r = Map.roads.get(i);
            if(r != currentRoad) {

                if(r.getOrientation().equals("horizontal")){
                    otherRoadXPos = r.getRoadXPos();
                    otherRoadYPos = r.getRoadYPos();
                    otherRoadEndXPos = r.getEndRoadXPos();
                    otherRoadEndYPos = r.getEndRoadYPos();
                }
                else{
                    otherRoadXPos = r.getRoadYPos();
                    otherRoadYPos = r.getRoadXPos();
                    otherRoadEndXPos = r.getEndRoadYPos();
                    otherRoadEndYPos = r.getEndRoadXPos();
                }
                if(currentRoad.getTrafficDirection().equals("east") && otherRoadXPos > currentRoadEndXPos) {
                    xPositions.add(otherRoadXPos);
                }
                else if(currentRoad.getTrafficDirection().equals("west") && otherRoadEndXPos < currentRoadXPos ){
                    xPositions.add(otherRoadEndXPos);
                }
                else if(currentRoad.getTrafficDirection().equals("north") && otherRoadEndYPos < currentRoadYPos) {
                    yPositions.add(otherRoadEndYPos);
                }
                else if(currentRoad.getTrafficDirection().equals("south") && otherRoadYPos > currentRoadEndYPos){
                    yPositions.add(otherRoadYPos);
                }
            }
        }
        int num;
        int num2;
        num = getCarXPosition(); //trying to find road with x position closest to this x position
        num2 = getCarYPosition(); // trying to find road with y position closest to this y position
        int index = 0;
        int index2 =0;
        int difference_1 = 10000;
        int difference_2 = 10000;
        if(currentRoad.getTrafficDirection().equals("east") || currentRoad.getTrafficDirection().equals("west")) {
            for (int j = 0; j < xPositions.size(); j++) { // loops through every position
                int Difference_x = Math.abs(xPositions.get(j) - num);
                if (Difference_x < difference_1) { // checks if difference is getting smaller
                    index = j;
                    difference_1 = Difference_x;
                }
            }
        }
        else if(currentRoad.getTrafficDirection().equals("south") || currentRoad.getTrafficDirection().equals("north")) {
            for (int j = 0; j < xPositions.size(); j++) { // loops through every position
                int Difference_y = Math.abs(yPositions.get(j) - num2);
                if (Difference_y < difference_2) { // checks if difference is getting smaller
                    index2 = j;
                    difference_2 = Difference_y;
                }
            }
        }
        int closestXPosition = 0;
        int closestYPosition = 0;
        if(currentRoad.getTrafficDirection().equals("east") || currentRoad.getTrafficDirection().equals("west")){
            closestXPosition = xPositions.get(index);
        }
        else {
            closestYPosition = yPositions.get(index2);
        }
        System.out.println(closestXPosition);

        for(int z = 0; z<Map.roads.size();z++){
            Road r = Map.roads.get(z);
            if ((r.getRoadXPos() == closestXPosition || r.getEndRoadXPos() == closestXPosition) && r.getOrientation().equals("horizontal")) {
                nextRoad = r;
            }
            else if ((r.getRoadYPos() == closestXPosition || r.getEndRoadYPos() == closestXPosition) && r.getOrientation().equals("vertical")){
                nextRoad = r;
            }
            if ((r.getRoadYPos() == closestYPosition || r.getEndRoadXPos() == closestYPosition) && r.getOrientation().equals("horizontal")) {
                nextRoad = r;
            }
            else if ((r.getRoadXPos() == closestYPosition || r.getEndRoadXPos() == closestYPosition) && r.getOrientation().equals("vertical")){
                nextRoad = r;
            }
        }
        xPositions.clear();
        yPositions.clear();
        return nextRoad;
    }


    public void move() {
        if(canMoveForward()) {
            if(road.getTrafficDirection().equals("east") || road.getTrafficDirection().equals("south")) {
                xPos += 25;
            }
            else if(road.getTrafficDirection().equals("west") || road.getTrafficDirection().equals("north")){
                xPos -= 25;
            }
            if (checkIfAtEndOfRoad()) {
                try {
                    Road r = nextRoad();
                    setCurrentRoad(r);
                    if(r.getOrientation().equals("horizontal") && r.getTrafficDirection().equals("east") || r.getOrientation().equals("vertical") && r.getTrafficDirection().equals("south")) {
                        for (int x = r.getRoadXPos(); x + getCarWidth() < r.getRoadLength()*25+ r.getEndRoadXPos(); x = x + 30) {
                            setCarXPosition(x);
                            setCarYPosition(getRoadCarIsOn().getRoadYPos()+5);
                            if(!collision(x, this)){
                                return;
                            }
                        }
                    }
                    else if(r.getOrientation().equals("horizontal") && r.getTrafficDirection().equals("west") || r.getOrientation().equals("vertical") && r.getTrafficDirection().equals("north")){
                        for (int x = r.getRoadXPos() + r.getRoadLength()*25 - getCarWidth(); x > r.getRoadXPos(); x = x - 30) {
                            setCarXPosition(x);
                            setCarYPosition(getRoadCarIsOn().getRoadYPos()+5);
                            if(!collision(x, this)){
                                return;
                            }
                        }
                    }
                }
                catch (IndexOutOfBoundsException e){
                    setCurrentRoad(road);
                    xPos = road.getRoadXPos();
                    yPos = road.getRoadYPos() + 5;
                }
            }
        }

    }
    
    // method for override
    public String getCarType() {
		return null;
	}

}
