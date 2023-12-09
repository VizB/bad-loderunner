import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Text extends Actor
{
    private String text;
    private int fontSize;
    private Color fgColor;
    private Color bgColor;
    private Color outColor;

    public Text(){
        text = "DEFAULT";
        fontSize = 24;
        fgColor = Color.WHITE;
        bgColor = Color.BLACK;
        outColor = Color.WHITE;
        updateImage();
    }

    public Text(String text){
        this.text = text;
        fontSize = 24;
        fgColor = Color.WHITE;
        bgColor = Color.BLACK;
        outColor = Color.WHITE;
        updateImage();
    }

    public void updateImage(){ 
        GreenfootImage img = new GreenfootImage(text, fontSize, fgColor, bgColor);
        if(this.getWorld() != null && this.getImage() != null){
            int newX = this.getX();
            if(img.getWidth() != this.getImage().getWidth()){
                int currentLeftX = newX - this.getImage().getWidth() / 2;
                newX = currentLeftX + img.getWidth() / 2;
                
            }
            this.setLocation(newX, this.getY());
        }
        setImage(img);
    }

    public void act()
    {
        // Add your action code here.
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.updateImage();
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        this.updateImage();
    }

    public Color getFgColor() {
        return fgColor;
    }

    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
        this.updateImage();
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
        this.updateImage();
    }

    public Color getOutColor() {
        return outColor;
    }

    public void setOutColor(Color outColor) {
        this.outColor = outColor;
        this.updateImage();
    }
}
