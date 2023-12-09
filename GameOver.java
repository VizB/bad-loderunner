import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOver extends World
{

    /**
     * Constructor for objects of class GameOver.
     * 
     */
    public GameOver()
    {    
        // Create a new world with 600x480 cells with a cell size of 1x1 pixels.
        super(600, 480, 1);
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(Color.BLACK);
        bg.fill();
        setBackground(bg);

        showText("Game Over!", getWidth() / 2, getHeight() / 2);

    }
}
