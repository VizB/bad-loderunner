import java.util.List;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Person {

    @Override
    public int getCommand(){
        List<Player> players = getObjectsInRange(9999, Player.class);
        if(players.size() == 0){
            return -1;
        }
        
        Player closestPlayer = players.get(0);

        for(int i = 1; i < players.size(); i++){
            if(closestPlayer.getX() < players.get(i).getX() || closestPlayer.getY() < players.get(i).getY()){
                closestPlayer = players.get(i);
            }
        }


        if(getY() < closestPlayer.getY() && isTouching(Ladder.class) || isOnLadder() || barToGrab() != null){
            return DOWN_COMMAND;
        } else if(getY() > closestPlayer.getY() && isOnLadder()){
            return UP_COMMAND;
        }
        if (getX() < closestPlayer.getX()){
            return RIGHT_COMMAND;
        } else if(getX() > closestPlayer.getX()){
            return LEFT_COMMAND;
        } else if(getX() == closestPlayer.getX()) {
            return RIGHT_COMMAND;
        }
        return -1;
    }

    @Override
    public void setClimbingAnimationImage(){
        GreenfootImage image = new GreenfootImage("enemy_climb_ladder.png");
        if(shouldAlternate){
            image.mirrorHorizontally();
        }
        setImage(image);
        shouldAlternate = !shouldAlternate;
    }

    @Override
    public void setRunningImage(boolean isLeft){
        GreenfootImage image = new GreenfootImage("enemy_run_0" + currImage + ".png");
        if(isLeft){
            image.mirrorHorizontally();
        }
        setImage(image);
        currImage = (currImage + 1) % totalImages;
    }

    @Override
    public void hangBar(boolean isLeft){
        
        setImage("enemy_bar_hang_00.png");
        if(shouldAlternate){
            setImage("enemy_bar_hang_01.png");
        }
        shouldAlternate = !shouldAlternate;
        if(isLeft){
            getImage().mirrorHorizontally();
            setLocation(getX() - dx, getY());
        } else {
            setLocation(getX() + dx, getY());
        }
    }

    @Override
    public String getDefaultImage(){
        return "enemy_stand.png";
    }

    @Override
    public String getDefaultFallImage(){
        return "enemy_fall.png";
    }


}
