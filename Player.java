import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * A player that is able to interact with the lode runner world, run, climb, fall and hang on bars
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Person {

    @Override
    public void act() {
        super.act();
        List<Gold> goldList = getObjectsInRange(9999, Gold.class);

        if(goldList.size() == 0) {
            MyLevelWorld world = (MyLevelWorld) getWorld();
            world.nextLevel();
        }

        if(getOneIntersectingObject(Enemy.class) != null){
            MyLevelWorld world = (MyLevelWorld) getWorld();
            if(world.getLives() - 1 == 0){
                Greenfoot.setWorld(new GameOver());
                world.setGameState(false);
                return;
            }
            Greenfoot.setWorld(new MyLevelWorld(world.getLevel(), world.getScore(), world.getLives() - 1));
        }

    }

    @Override
    public int getCommand() {
        if(Greenfoot.isKeyDown("up")){
            return UP_COMMAND;
        }
        
        if(Greenfoot.isKeyDown("down")){
            return DOWN_COMMAND;
        }

        if(Greenfoot.isKeyDown("left")){
            return LEFT_COMMAND;
        }

        if(Greenfoot.isKeyDown("right")){
            return RIGHT_COMMAND;
        }

        return -1;
    }

    @Override
    public String getDefaultImage(){
        return "player_stand.png";
    }

    @Override
    public String getDefaultFallImage(){
        return "player_fall.png";
    }


}
