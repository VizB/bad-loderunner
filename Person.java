import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Color;

import java.awt.*;

/**
 * Write a description of class Person here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Person extends Actor
{

    public int dx = 2;
    public final int GRID_SIZE = 24;

    //States
    public final int STANDING_STATE = 0;
    public final int RUN_LEFT_STATE = 1;
    public final int RUN_RIGHT_STATE = 2;
    public final int FALLING_STATE = 3;
    public final int CLIMBING_UP_STATE = 4;
    public final int CLIMBING_DOWN_STATE = 5;
    public final int BAR_HANGING_LEFT_STATE = 6;
    public final int BAR_HANGING_RIGHT_STATE = 7;
    public final int BAR_HANGING_STATE = 8;

    //Commands
    public final int UP_COMMAND = 0;
    public final int DOWN_COMMAND = 1;
    public final int LEFT_COMMAND = 2;
    public final int RIGHT_COMMAND = 3;

    //Init
    public int currState = 3;
    public int currImage = 0;
    protected int totalImages = 4;
    public int imageOffset = 4;
    protected int fallRate = 3;
    protected boolean shouldAlternate = false;
    
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public void act() {
        MyLevelWorld world = (MyLevelWorld) getWorld();
        if(world.getGameState()) {
            return;
        }
        setState(STANDING_STATE);
        int command = getCommand();

        if(isTouching(Ladder.class) || isOnLadder()) {
            if(isOnLadder()){
                setImage(getDefaultImage());
            }
            if(command == UP_COMMAND && getY() - getImage().getHeight() /2 > 0 && !upIsBlocked()) {
                setState(CLIMBING_UP_STATE);
            } else if(command == DOWN_COMMAND && !downIsBlocked()){
                setState(CLIMBING_DOWN_STATE);
            } else if(command == LEFT_COMMAND && !isObjLeft(Wall.class, 1, 2) && getX() - GRID_SIZE / 2 > 0 && !leftIsBlocked()) {
                setState(RUN_LEFT_STATE);
            } else if(command == RIGHT_COMMAND && !isObjRight(Wall.class, 1, 2) && getX() + GRID_SIZE / 2 < getWorld().getWidth() && !rightIsBlocked()) {
                setState(RUN_RIGHT_STATE);
            }
        } else if (isStandingOnPlatform() || isStandingOnLadder()) {
            if(command == RIGHT_COMMAND && getX() + getImage().getWidth() / 2 < getWorld().getWidth() - getImage().getWidth() / 2 && !rightIsBlocked()) {
                setState(RUN_RIGHT_STATE);
            } else if(command == LEFT_COMMAND && getX() - getImage().getWidth() / 2 > getImage().getWidth() / 2 && !leftIsBlocked()) {
                setState(RUN_LEFT_STATE);
            }
        } else if(barToGrab() != null) {
            setState(BAR_HANGING_STATE);
            if(command == RIGHT_COMMAND && !isObjRight(Wall.class, 1, 2) && getX() + GRID_SIZE / 2 < getWorld().getWidth() && !rightIsBlocked()) {
                setState(BAR_HANGING_RIGHT_STATE);
            } else if(command == LEFT_COMMAND && !isObjLeft(Wall.class, 1, 2) && getX() - GRID_SIZE / 2 > 0 && !leftIsBlocked()) {
                setState(BAR_HANGING_LEFT_STATE);
            } else if(command == DOWN_COMMAND) {
                setState(FALLING_STATE);
            }
        }else if(canFall()) {
            setState(FALLING_STATE);
        }

        runPlayer(currState);
    }

    public String getDefaultImage(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDefaultFallImage(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getCommand() {
        return -1;
    }

    //Can fall, should fall.
    public boolean canFall() {
        return !isStandingOnPlatform() && !isStandingOnLadder() && barToGrab() == null && getOneIntersectingObject(Wall.class) == null && getOneIntersectingObject(Ladder.class) == null;
    }

    public boolean isStandingOnPlatform(){
        boolean isWallB = isObjBelow(Wall.class, 0, 2);
        boolean isWallBL = isObjBelowLeft(Wall.class, 1, 2);
        boolean isWallBR = isObjBelowRight(Wall.class, 1, 2);
        return isWallB || isWallBL || isWallBR;
    }

    public boolean isStandingOnLadder(){
        boolean isLadderB = isObjBelow(Ladder.class, 0, 1);
        boolean isLadderBL = isObjBelowLeft(Ladder.class, -6, 1);
        boolean isLadderBR = isObjBelowRight(Ladder.class, -6, 1);
        return isLadderB || isLadderBL || isLadderBR;
    }
    public <A extends Actor> boolean isObjBelow(Class<A> cls, int sideOffset, int bottomOffset) {
        return getOneObjectAtOffset(sideOffset, GRID_SIZE/2 + bottomOffset, cls) != null;
    }

    public <A extends Actor> boolean isObjBelowLeft(Class<A> cls, int sideOffset, int bottomOffset) {
        return getOneObjectAtOffset(-GRID_SIZE/2 - sideOffset, GRID_SIZE/2 + bottomOffset, cls) != null;
    }

    public <A extends Actor> boolean isObjBelowRight(Class<A> cls, int sideOffset, int bottomOffset) {
        return getOneObjectAtOffset(GRID_SIZE/2 + sideOffset, GRID_SIZE/2 + bottomOffset, cls) != null;
    }

    public <A extends Actor> boolean isObjTop(Class<A> cls, int sideOffset, int topOffset) {
        return getOneObjectAtOffset(sideOffset, -GRID_SIZE/2 - topOffset, cls) != null;
    }

    public <A extends Actor> boolean isObjTopLeft(Class<A> cls, int sideOffset, int topOffset) {
        return getOneObjectAtOffset(-GRID_SIZE/2 - sideOffset, -GRID_SIZE/2 - topOffset, cls) != null;
    }

    public <A extends Actor> boolean isObjTopRight(Class<A> cls, int sideOffset, int topOffset) {
        return getOneObjectAtOffset(GRID_SIZE/2 + sideOffset, -GRID_SIZE/2 - topOffset, cls) != null;
    }

    public <A extends Actor> boolean isObjLeft(Class<A> cls, int sideOffset, int topOffset) {
        return getOneObjectAtOffset(-GRID_SIZE/2 - sideOffset, topOffset, cls) != null;
    }

    public <A extends Actor> boolean isObjRight(Class<A> cls, int sideOffset, int topOffset) {
        return getOneObjectAtOffset(GRID_SIZE/2 + sideOffset, topOffset, cls) != null;
    }

    public boolean isOnLadder(){
        return isObjBelow(Ladder.class, 0, 1); // || isObjBelowLeft(Ladder.class) || isObjBelowRight(Ladder.class);
    }

    public boolean inBetweenLadders() {
        return getOneObjectAtOffset(GRID_SIZE,0, Ladder.class) != null && getOneObjectAtOffset(-GRID_SIZE, 0, Ladder.class) != null;
    }

    public int getGridMiddleX(){
        return ((getX() / GRID_SIZE) * GRID_SIZE) + GRID_SIZE/2;
    }

    public int getGridMiddleY(){
        return ((getY() / GRID_SIZE) * GRID_SIZE) + GRID_SIZE/2;
    }


    
    public void runPlayer(int currState){
        switch(currState){
            case STANDING_STATE:
                setImage(getDefaultImage());
                break;
            case RUN_LEFT_STATE:
                runLeft();
                break;
            case RUN_RIGHT_STATE:
                runRight();
                break;
            case FALLING_STATE:
                this.setLocation(getGridMiddleX(), this.getY());
                fall();
                break;
            case CLIMBING_UP_STATE:
                climb(true);
                break;
            case CLIMBING_DOWN_STATE:
                climb(false);
                break;
            case BAR_HANGING_LEFT_STATE:
                hangBar(true);
                break;
            case BAR_HANGING_RIGHT_STATE:
                hangBar(false);
                break;
            case BAR_HANGING_STATE:
                setImage(getDefaultFallImage());
                break;
            default:
                break;
        }
    }
    
    public Bar barToGrab() {
        int sideOffset = 1;
        int topOffset = 2;
        Bar barToGrab = (Bar) getOneObjectAtOffset(0, -getImage().getHeight()/ 2 + 2, Bar.class);
        if(barToGrab != null) {
            return barToGrab;
        }

        //TOP LEFT BAR
        barToGrab = (Bar) getOneObjectAtOffset(-GRID_SIZE / 2 + sideOffset, -GRID_SIZE / 2 + topOffset, Bar.class);
        if(barToGrab != null) {
            return barToGrab;
        }
        //TOP RIGHT BAR
        barToGrab = (Bar) getOneObjectAtOffset(GRID_SIZE / 2 - sideOffset, -GRID_SIZE / 2 + topOffset, Bar.class);
        return barToGrab;

    }

    public void hangBar(boolean isLeft){
        
        setImage("player_bar_hang_00.png");
        if(shouldAlternate){
            setImage("player_bar_hang_01.png");
        }
        shouldAlternate = !shouldAlternate;
        if(isLeft){
            getImage().mirrorHorizontally();
            setLocation(getX() - dx, getY());
        } else {
            setLocation(getX() + dx, getY());
        }
    }
    
    public void climb(boolean isUp){
        if(!isOnLadder() && !isTouching(Ladder.class)){
            return;
        }

        Actor possibleLadder = getOneIntersectingObject(Ladder.class);
        
        //Top Ladders
        Actor possibleTopLadder = getOneObjectAtOffset(0, (GRID_SIZE / 2), Ladder.class);
        Actor possibleTopLeftLadder = getOneObjectAtOffset(-GRID_SIZE / 2, (GRID_SIZE / 2), Ladder.class);
        Actor possibleTopRightLadder = getOneObjectAtOffset(GRID_SIZE / 2, (GRID_SIZE / 2), Ladder.class);
        
        //Bottom Ladders
        Actor possibleBottomLadder = getOneObjectAtOffset(0, (GRID_SIZE / 2), Ladder.class);
        Actor possibleBottomLeftLadder = getOneObjectAtOffset(-GRID_SIZE / 2, (GRID_SIZE / 2), Ladder.class);
        Actor possibleBottomRightLadder = getOneObjectAtOffset(GRID_SIZE / 2, (GRID_SIZE / 2), Ladder.class);
        
        if(isUp){
            if(possibleLadder != null){
                setLocation(possibleLadder.getX(), getY() - dx);
            }else if(possibleTopLadder != null){
                setLocation(possibleTopLadder.getX(), getY() - dx);
            }else if(possibleTopLeftLadder != null){
                setLocation(possibleTopLeftLadder.getX(), getY() - dx);
            }else if(possibleTopRightLadder != null){
                setLocation(possibleTopRightLadder.getX(), getY() - dx);
            }
        } else {
            if(possibleLadder != null){
                setLocation(possibleLadder.getX(), getY() + dx);
            }else if(possibleBottomLadder != null){
                setLocation(possibleBottomLadder.getX(), getY() + dx);
            }else if(possibleBottomLeftLadder != null){
                setLocation(possibleBottomLeftLadder.getX(), getY() + dx);
            }else if(possibleBottomRightLadder != null){
                setLocation(possibleBottomRightLadder.getX(), getY() + dx);
            }
        }
        setClimbingAnimationImage();
    }
    
    public int getState(){
        return currState;
    }
    
    protected void setState(int newState){    
        currState = newState;
    }
    
    public void fall() {
        setImage(getDefaultFallImage());
        
        setLocation(getX(), getY() + fallRate);
        Actor possibleWall = getOneIntersectingObject(Wall.class);
        if(possibleWall != null){
            setLocation(getX(), getGridMiddleY());
        }

        Actor possibleLadder = getOneIntersectingObject(Ladder.class);
        if(possibleLadder != null) {
            setLocation(getX(), getGridMiddleY());
        }

    }
    
    public void runLeft(){
        setLocation(getX() - dx, getY());
        setRunningImage(true);
    }
    
    public void runRight(){
        setLocation(getX() + dx, getY());
        setRunningImage(false);
    }
    
    protected void setClimbingAnimationImage(){
        GreenfootImage image = new GreenfootImage("player_climb_ladder.png");
        if(shouldAlternate){
            image.mirrorHorizontally();
        }
        setImage(image);
        shouldAlternate = !shouldAlternate;
    }
    
    protected void setRunningImage(boolean isLeft){
        GreenfootImage image = new GreenfootImage("player_run_0" + currImage + ".png");
        if(isLeft){
            image.mirrorHorizontally();
        }
        setImage(image);
        currImage = (currImage + 1) % totalImages;
    }

    protected boolean leftIsBlocked() {
        Actor possibleWall = getOneObjectAtOffset(-getImage().getWidth() / 2, 0, Wall.class);
        boolean topLeftIsBlocked = isObjTopLeft(Wall.class,  1, 2);
        return possibleWall != null && topLeftIsBlocked;
    }

    protected boolean rightIsBlocked() {
        Actor possibleWall = getOneObjectAtOffset(getImage().getWidth() / 2, 0, Wall.class);
        boolean topRightIsBlocked = isObjTopRight(Wall.class,  1, 2);
        return possibleWall != null && topRightIsBlocked;
    }

    protected boolean upIsBlocked() {
        Actor possibleWall = getOneObjectAtOffset(0, -GRID_SIZE / 2, Wall.class);
        return possibleWall != null;
    }
    protected boolean downIsBlocked() {
        Actor possibleWall = getOneObjectAtOffset(0, GRID_SIZE / 2, Wall.class);
        return possibleWall != null;
    }
}


