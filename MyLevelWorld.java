import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * use this class as a starting point for your Lode Runner. Transfer any extra methods or fields you need from your original class.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyLevelWorld extends LevelWorld {
    
    // add instance variables to represent the lives and score
    private int score = 0;
    private int lives = 1;
    protected Text scoreText;
    protected Text livesText;
    protected Text levelText;
    private boolean gameState = false;
    private int GRID_SIZE = 24;

    // static initialization code - runs when the class is loaded, before the main method is called.
    // You can only call static methods and only access static fields.
    // In this case, we need to initialize the margins BEFORE the world is created
    static {
        // set the margins (open space) on the left, right, top and bottom sides of the world
        // The level will be drawn with the given spaces open on each side
        // Set the bottom margin to the height of your HUD
        LevelWorld.setMargins(0, 0, 0, 24);
        

    }
    
    // Default Constructor (loads the first txt in the "levels" folder)
    public MyLevelWorld() {
        this(1, 0, 5); // load level 1
        addObject(scoreText, scoreText.getImage().getWidth()/2, getHeight()-scoreText.getImage().getHeight()/2);
        addObject(livesText, getWidth() / 2 - livesText.getImage().getWidth() / 2,
                getHeight() - livesText.getImage().getHeight() / 2);
        addObject(levelText, getWidth() - levelText.getImage().getWidth(), getHeight() - levelText.getImage().getHeight() / 2);
    }
    
    // loads the level given. For example, if level was 3, it would load the third txt file in the levels folder
    // You can add parameters to this constructor for lives and score. If you do, you need to pass default lives
    // and score values when you call this(1) in the default constructor.
    public MyLevelWorld(int level, int score, int lives) {
        super(level);
        this.score = score;
        this.lives = lives;
    
        scoreText = new Text("SCORE: " + score);
        livesText = new Text("LIVES: " + lives);
        levelText = new Text("LEVEL: " + level);

        addObject(scoreText, scoreText.getImage().getWidth()/2, getHeight()-scoreText.getImage().getHeight()/2);
        addObject(livesText, getWidth() / 2 - livesText.getImage().getWidth() / 2,
                getHeight() - livesText.getImage().getHeight() / 2);
        addObject(levelText, getWidth() - levelText.getImage().getWidth(), getHeight() - levelText.getImage().getHeight() / 2);
        
        GreenfootImage img = new GreenfootImage(600, 480);
        img.setColor(Color.BLACK);
        img.fill();
        setBackground(img);
    }
    
    @Override
    public void defineClassTypes() {
        // define which classes represent walls, ladders, bars, players, and enemies
        // TODO: REPLACE WITH YOUR CLASSES
        getLoader().setWallClass(Wall.class);
        getLoader().setPlayerClass(Player.class);
        getLoader().setLadderClass(Ladder.class); // you can remove this if you have no ladders in your game
        getLoader().setBarClass(Bar.class); // you can remove this if you have no bars in your game
        getLoader().setEnemyClass(Enemy.class); // you can remove this if you have no enemies in your game
        getLoader().setGoldClass(Gold.class); // you can remove this if you have no gold in your game
    }

    public void nextLevel() {
       int currLvl = getLevel();
       int nxtLvl = getLevel() + 1;
       int numLvls = LodeRunnerLevelLoader.numLevels();

       if(nxtLvl <= numLvls){
           World world = new MyLevelWorld(nxtLvl, score, lives);
           Greenfoot.setWorld(world);
       } else {
           Greenfoot.setWorld(new WinScreen());
       }
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
    public int getScore() {
        return score;
    }

    public void updateScore(){
        score += 250;
        scoreText.setText("SCORE: " + score);
    }

    public boolean getGameState(){
        return gameState;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public int getLives(){
        return lives;
    }

    public void setGameState(boolean gameState){
        this.gameState = gameState;
    }
}
