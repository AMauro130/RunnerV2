import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boxes {
    private ImageView box;
    private double x, y, length, width;
    private double xInit,yInit;
    private double blinkFreq = 16;

    private int count = 0, sizeRB = 0;

    //GETTER & SETTER
    public ImageView getBox() {return box;}
    public double getY() {return y;}
    public double getX() {return x;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}

    //DISPLAY
    public void displayBox(){
        box.setX(x);
        box.setY(y);
    }

    //RESTART
    public void restartBox(){
        box.setX(xInit);
        box.setY(yInit);
    }

    //CONSTRUCTOR
    public Boxes(double xpos, double ypos, double length, double width, String fileName){
        this.x = xpos;
        this.y = ypos;
        this.xInit = xpos;
        this.yInit = ypos;
        this.length = length;
        this.width = width;
        Image spriteSheet = new Image(fileName);
        box = new ImageView(spriteSheet);
        box.setX(xpos);
        box.setY(ypos);
    }

    //BLINKING
    public void blinking(Group root){ //permet de faire clignotter la box qu'on fait apparaitre
        if (sizeRB == 0 && count < blinkFreq/2){
            root.getChildren().remove(box);
            sizeRB ++;
        }
        else if (sizeRB != 0 && count >= blinkFreq/2){
            root.getChildren().add(box);
            sizeRB --;
        }
        count++;
        if (count == blinkFreq){
            count = 0;
        }
    }
}
