import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Gold here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gold extends Actor{
    public void act(){
        if(getOneIntersectingObject(Enemy.class) == null && getOneIntersectingObject(Person.class) != null){
            ((MyLevelWorld) getWorld()).updateScore();
            ((MyLevelWorld) getWorld()).removeObject(this);
        }
    }
}
