import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MousePlayer extends Player {
    
    
    
    
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public void act()
    {   
        MyLevelWorld world = (MyLevelWorld) getWorld();
        if(!world.getGameState()){        
            int playerWidth = getImage().getWidth();
            boolean leftIsBlocked = getOneObjectAtOffset(-playerWidth/2 - imageOffset, 0, Wall.class) != null;
            boolean rightIsBlocked = getOneObjectAtOffset(playerWidth/2 + imageOffset, 0, Wall.class) != null;
            if(isTouching(Ladder.class) || isOnLadder()){
                if(isOnLadder()){
                    setImage("player_stand.png");
                }
                if(getMouseControlDirection() =="up" && getY() - getImage().getHeight() /2 > 0){
                    super.setState(CLIMBING_UP_STATE);
                } else if(getMouseControlDirection() =="down" && getY() + getImage().getHeight() /2 < getWorld().getHeight() && !isStandingOnPlatform()){
                    super.setState(CLIMBING_DOWN_STATE);
                } else if(getMouseControlDirection() =="left" && !leftIsBlocked && getX() - playerWidth / 2 > 0 && !isTouching(Wall.class)){
                    super.setState(RUN_LEFT_STATE);
                } else if(getMouseControlDirection() =="right" && !rightIsBlocked && getX() + playerWidth < getWorld().getWidth() && !isTouching(Wall.class)){
                    setState(RUN_RIGHT_STATE);
                } 
            } else if(isStandingOnPlatform()){
                setState(STANDING_STATE);
                if(getMouseControlDirection() == "left" && !leftIsBlocked && getX() - playerWidth / 2 > 0){
                    setState(RUN_LEFT_STATE);
                } else if(getMouseControlDirection() =="right" && !rightIsBlocked && getX() + playerWidth < getWorld().getWidth()){
                    setState(RUN_RIGHT_STATE);
                } 
            }else if(barToGrab() != null){
                if(getMouseControlDirection() =="left" && !leftIsBlocked && getX() - playerWidth / 2 > 0){
                    setState(BAR_HANGING_LEFT_STATE);
                } else if(getMouseControlDirection() == "right" && !rightIsBlocked && getX() + playerWidth < getWorld().getWidth()){
                    setState(BAR_HANGING_RIGHT_STATE);
                } 
            } else {
                setState(FALLING_STATE);
            }
            
            runPlayer(currState);
            setState(STANDING_STATE);
        }
    }
    
    public String getMouseControlDirection(){
        int playerHeight = getImage().getHeight();
        int playerWidth = getImage().getWidth();
        MouseInfo mouseInfo = Greenfoot.getMouseInfo();
        if(mouseInfo == null){
            return "no direction";
        }
        if(mouseInfo.getX() > getX() + playerWidth / 2){
            return "right";
        }
        if(mouseInfo.getX() < getX() - playerWidth / 2){
            return "left";
        }
        if(mouseInfo.getY() > getY() + playerHeight / 2 && mouseInfo.getX() < getX() + playerWidth / 2 && mouseInfo.getX() > getX() - playerWidth / 2){
            return "down";
        }
        if(mouseInfo.getY() < getY() - playerHeight / 2 && mouseInfo.getX() < getX() + playerWidth / 2 && mouseInfo.getX() > getX() - playerWidth / 2){
            return "up";
        }
        return "no direction";
    }
}
