import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StaticThing {
    private double x;
    private double y;
    private ImageView img;

    //GETTER
    public ImageView getImg() {
        return img;
    }

    //CONSTRUCTOR
    public StaticThing(double xpos, double ypos, String FileName, int x, int y, int length, int width){
        this.x = xpos;
        this.y = ypos;
        Image spriteSheet = new Image(FileName);
        img = new ImageView(spriteSheet);
        img.setViewport(new Rectangle2D(x,y,length,width));
        img.setX(xpos);
        img.setY(ypos);
    }
}
