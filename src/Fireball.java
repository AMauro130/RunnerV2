import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Fireball {
    private double x, y;
    private ImageView img;

    //GETTER
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public ImageView getImg() {
        return img;
    }

    //CONSTRUCTOR
    public Fireball(double xpos, double ypos, String fileName){
        this.x = xpos;
        this.y = ypos;
        Image spriteSheet = new Image(fileName);
        img = new ImageView(spriteSheet);
        img.setViewport(new Rectangle2D(2+83.5*6,164*2,77, 80)); //cas particulier pour la ligne 2 du tir de la fireball
        img.setX(xpos);
        img.setY(ypos);
    }

    //UPDATE
    public void update(Hero hero, ArrayList<Foe> foes, double posXinit){

        if ((hero.getShoot())){ //si on tire, la fireball avance
            x += 15;
            y = hero.getY();
            img.setX(x);
            img.setY(y);
        }

        for (int i=0;i<foes.size();i++) {
            if (x > 810 || foes.get(i).getTouched()) {
                x = posXinit;
                y = -800; //on cache la fireball
                img.setX(x);
                img.setY(y);
                hero.setShoot(false);
                hero.setReload(25000000000L);
            }
        }
    }
}
