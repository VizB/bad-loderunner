import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World{
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    protected int score;
    protected Text scoreText;
    private boolean gameOver = false;
    private int lives = 5;
    
    public MyWorld()
    {    
        // Create a new world with 600x480 cells with a cell size of 1x1 pixels.
        super(600, 480, 1);
        score = 0;
        Wall ref = new Wall();
        int gridSize = ref.getImage().getWidth();   
        //Background Color
        GreenfootImage bg = new GreenfootImage(600, 480);
        bg.setColor(Color.BLACK);
        bg.fillRect(0, 0, 600, 480);
        setBackground(bg);
        
        boolean debugMode = false;
        
        if(debugMode){
            bg.setColor(Color.GREEN);
            int xPos = gridSize;
            int yPos = gridSize;
            for(int i = 0; i < getWidth() / gridSize; i++){
                bg.drawLine(xPos, 0, xPos, getHeight());
                xPos += gridSize;
            }
            for(int i = 0; i < getHeight() / gridSize; i++){
                bg.drawLine(0, yPos, getWidth(), yPos);
                yPos += gridSize;
            }
        }
        
        
        //World Setup
        drawRowOfWalls(25, 0, getHeight() - gridSize * 2);
        drawGridOfWalls(3, 3, 0, getHeight() - gridSize * 5);
        drawGridOfWalls(3, 3, getWidth() - gridSize * 3, getHeight() - gridSize * 5);
        drawRowOfWalls(12, 0, getHeight() - gridSize * 6);
        drawRowOfWalls(3, gridSize * 5, getHeight() - gridSize * 11);
        drawRowOfWalls(6, 0, getHeight() - gridSize * 18);
        
        //Ladders
        drawLadders(4, (gridSize * 12), (getHeight() - gridSize * 6));
        drawLadders(5, gridSize * 4, getHeight() - gridSize * 11);
        drawLadders(5, gridSize * 8, getHeight() - gridSize * 11);
        drawLadders(7, gridSize * 6, gridSize * 2);
        drawLadders(3, getWidth() - gridSize * 4, getHeight() - gridSize * 5);
        drawLadders(3, gridSize * 7, gridSize * 4);
        
        //Bars
        drawBars(4, gridSize * 2, gridSize * 3);
        drawBars(5, gridSize * 9, getHeight() - gridSize * 11);
        drawBars(4, gridSize * 13, getHeight() - gridSize * 6);
        drawBars(4, 0, getHeight() - gridSize * 11);
        
        //Player
        Player player = new Player();
        addObject(player, gridSize * 2 + player.getImage().getWidth() / 2, gridSize * 1 + player.getImage().getHeight() / 2);
        //Mouse Player
        MousePlayer mPlayer = new MousePlayer();
        addObject(mPlayer, gridSize * 8, 
                getHeight() - gridSize * 2 - mPlayer.getImage().getHeight() / 2);

        //Enemy
        Enemy enemy = new Enemy();
        addObject(enemy, getWidth() - gridSize * 2 - enemy.getImage().getHeight() / 2, getHeight() - gridSize * 5 - enemy.getImage().getHeight() / 2);

        //Text
        scoreText = new Text("SCORE: " + score);

        addObject(scoreText, scoreText.getImage().getWidth() / 2, getHeight() - gridSize /2);

        Text levelText = new Text("LEVEL 1");
        addObject(levelText, getWidth() - levelText.getImage().getWidth() / 2, getHeight() - gridSize + levelText.getImage().getHeight() / 2);

        //Gold
        Gold gold1 = new Gold();
        addObject(gold1, gridSize * 3 + gridSize / 2, gridSize * 2 - gold1.getImage().getHeight() / 2);
        
        Gold gold4 = new Gold();
        addObject(gold4, 6 * gridSize + gridSize / 2, getHeight() - gridSize * 6 - gold4.getImage().getHeight() / 2);

        Gold gold5 = new Gold();
        addObject(gold5, 10 * gridSize + gridSize / 2, getHeight() - gridSize * 6 - gold5.getImage().getHeight() / 2);
    }
    
    
    public void drawRowOfWalls(int width, int startX, int startY){
        Wall tempWall = new Wall();
        int runningX = startX + tempWall.getImage().getWidth() / 2;
        for(int i = 0; i < width; i++){
            Wall wall = new Wall();
            addObject(wall, runningX, startY + wall.getImage().getWidth() / 2);
            runningX += wall.getImage().getWidth();
        }
    }
    
    public void drawGridOfWalls(int rows, int cols, int startX, int startY){
        Wall tempWall = new Wall();
        int runningX = startX + tempWall.getImage().getWidth() / 2;
        int runningY = startY + tempWall.getImage().getWidth() / 2;
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                Wall wall = new Wall();
                addObject(wall, runningX, runningY);
                runningX += wall.getImage().getWidth();
            }
            runningY += tempWall.getImage().getWidth();
            runningX = startX + tempWall.getImage().getWidth() / 2;
        }
    }
    
    public void drawLadders(int height, int xPos, int yPos){
        int runningYPos = yPos;
        for(int i = 0; i < height; i++){
            Ladder ladder = new Ladder();
            addObject(ladder, xPos + ladder.getImage().getHeight()/2, 
                        runningYPos + ladder.getImage().getHeight()/2);
            runningYPos += ladder.getImage().getHeight();
        }
    }
    
    public void drawBars(int width, int xPos, int yPos){
        Bar tempBar = new Bar();
        int runningXPos = xPos + tempBar.getImage().getWidth() / 2;
        for(int i = 0; i < width; i++){
            Bar bar = new Bar();
            addObject(bar, runningXPos, yPos + bar.getImage().getHeight());
            runningXPos += bar.getImage().getWidth();
        }
    }

    public void updateScore(){
        score += 250;
        scoreText.setText("SCORE: " + score);
    }
    
    public boolean getGameState(){
        return gameOver;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public int getLives(){
        return lives;
    }
}
